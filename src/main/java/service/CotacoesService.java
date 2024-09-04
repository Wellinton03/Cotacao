package service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import DTO.FiltroDTO;
import DTO.IndicadorDTO;
import entity.Cotacoes;
import entity.Indicadores;
import response.APIResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;


@Named
@ApplicationScoped
public class CotacoesService implements Serializable {

    private static final long serialVersionUID = 1L;

    private EntityManager manager;
    APIService apiService = new APIService();
    
    public CotacoesService() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cotacoesDatabase");
        manager = emf.createEntityManager();
    }

    public Cotacoes porId(Long id) {
        return manager.find(Cotacoes.class, id);
    }

    public List<Cotacoes> buscar(String description) {
        TypedQuery<Cotacoes> query = manager.createQuery(
            "SELECT c FROM Cotacoes c WHERE c.indicadores.description LIKE :description", Cotacoes.class);
        query.setParameter("description", "%" + description + "%");
        
        return query.getResultList();
    }

    public List<Cotacoes> todasCotacoes() {
        return manager.createQuery("from Cotacoes", Cotacoes.class).getResultList();
    }

    public void salvar(Cotacoes cotacoes) {
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            if (cotacoes.getId() == null) {
                manager.persist(cotacoes);
            } else {
                manager.merge(cotacoes);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw e;
        }
    }

    public void excluir(Cotacoes cotacoes) {
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            cotacoes = manager.merge(cotacoes);
            manager.remove(cotacoes);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw e;
        }
    }

    public List<IndicadorDTO> porIndicador() {
        String jpql = "SELECT new DTO.IndicadorDTO(i.id, i.description) " +
                      "FROM Indicadores i " +
                      "JOIN Cotacoes c ON c.indicadores.id = i.id " +
                      "GROUP BY i.id, i.description";
        TypedQuery<IndicadorDTO> query = manager.createQuery(jpql, IndicadorDTO.class);
        return query.getResultList();
    }

    public List<FiltroDTO> buscarPorPeriodoEIndicador(Date dataInicial, Date dataFinal, Long idIndicadores) {
        String jpql = "SELECT new DTO.FiltroDTO(c.dataHora, c.valor) " +
                      "FROM Cotacoes c WHERE c.dataHora BETWEEN :dataInicial AND :dataFinal " +
                      "AND c.indicadores.id = :idIndicadores";
        return manager.createQuery(jpql, FiltroDTO.class)
                      .setParameter("dataInicial", dataInicial)
                      .setParameter("dataFinal", dataFinal)
                      .setParameter("idIndicadores", idIndicadores)
                      .getResultList();
    }
    
    public Indicadores verificarOuAdicionarIndicador(String nomeMoeda) {
        TypedQuery<Indicadores> query = manager.createQuery(
            "SELECT i FROM Indicadores i WHERE i.description = :description", Indicadores.class);
        query.setParameter("description", nomeMoeda);

        List<Indicadores> resultado = query.getResultList();

        if (resultado.isEmpty()) {
            Indicadores indicadores = new Indicadores();
            indicadores.setDescription(nomeMoeda);
            manager.persist(indicadores); 
            return indicadores;
        } else {
            return resultado.get(0);
        }
    }

    public String formatDateForAPI(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    public void atualizarCotacoesDoBanco() {
        try {
            List<String> moedas = apiService.obterMoedasDisponiveis();
            
            
            for (String moeda : moedas) {
                String moedaConsulta = moeda + "-BRL";
                List<APIResponse> apiResponses = apiService.getExchangeRates(moedaConsulta, 2);
                
                EntityTransaction tx = null;
                try {
                    tx = manager.getTransaction();
                    tx.begin();
                    
                    for (APIResponse response : apiResponses) {
                        Date dataHora = response.getDataEHora();
                        TypedQuery<Cotacoes> query = manager.createQuery(
                            "SELECT c FROM Cotacoes c WHERE c.dataHora = :dataHora AND c.indicadores.description = :description", Cotacoes.class);
                        query.setParameter("dataHora", dataHora);
                        query.setParameter("description", response.getNomeMoeda());

                        List<Cotacoes> cotacoesExistentes = query.getResultList();
                        
                        if (cotacoesExistentes.isEmpty()) {
                            Cotacoes cotacao = new Cotacoes();
                            cotacao.setDataHora(dataHora); 
                            cotacao.setValor(response.getAlta());  
                            
                            Indicadores indicador = verificarOuAdicionarIndicador(response.getNomeMoeda());
                            cotacao.setIndicadores(indicador);
                            
                            manager.persist(cotacao);
                        }
                    }
                    tx.commit();
                } catch (Exception e) {
                    if (tx != null && tx.isActive()) {
                        tx.rollback();
                    }
                    e.printStackTrace();
                } finally {
                    if (manager != null) {
                        manager.clear();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
