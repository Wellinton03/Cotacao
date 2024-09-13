package service;

import java.io.Serializable;
import java.time.LocalDateTime;
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
import response.APICurrencyResponse;
import response.APIResponse;

@Named
@ApplicationScoped
public class CotacoesService implements Serializable {

    private static final long serialVersionUID = 1L;

    private EntityManager manager;
    APIAlphaVantage apiAlphaVantage = new APIAlphaVantage();
    APIAwesome apiAwesome = new APIAwesome();
    
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
        return manager.createQuery(" from Cotacoes", Cotacoes.class).getResultList();
    }
    
    public void deletaCotacao() {
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            	manager.createQuery("DELETE FROM Cotacoes").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
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

    public List<FiltroDTO> buscarPorPeriodoEIndicador(LocalDateTime dataInicial, LocalDateTime dataFinal, Long indicadorId) {
        String jpql = "SELECT new DTO.FiltroDTO(c.dataHora, c.valor) " +
                      "FROM Cotacoes c WHERE c.dataHora BETWEEN :dataInicial AND :dataFinal " +
                      "AND c.indicadores.id = :indicadorId";
        return manager.createQuery(jpql, FiltroDTO.class)
                      .setParameter("dataInicial", dataInicial)
                      .setParameter("dataFinal", dataFinal)
                      .setParameter("indicadorId", indicadorId)
                      .getResultList();
    }
    
    public String obterDescricaoIndicador(Long indicadorId) {
        Indicadores indicador = manager.find(Indicadores.class, indicadorId);
        return indicador != null ? indicador.getDescription() : null;
    }
  

    private Indicadores buscarOuCriarIndicador(String symbol) {
        TypedQuery<Indicadores> query = manager.createQuery(
            "SELECT i FROM Indicadores i WHERE i.description = :description", Indicadores.class);
        query.setParameter("description", symbol);
        
        List<Indicadores> indicadoresExistentes = query.getResultList();

        if (indicadoresExistentes.isEmpty()) {
            Indicadores novoIndicador = new Indicadores();
            novoIndicador.setDescription(symbol);
            manager.persist(novoIndicador);
            return novoIndicador;
        } else {
            return indicadoresExistentes.get(0);
        }
    }
    
    public void atualizarMoedas(String moeda, Integer dias ) {
    	EntityTransaction transaction = null;
    	try {
    		List<APICurrencyResponse> apiResponses = apiAwesome.getCurrencyHistoryData(moeda, dias);
    		
    		transaction = manager.getTransaction();
    			if(!transaction.isActive()) {
    				transaction.begin();
    			}
    			for (APICurrencyResponse response : apiResponses) {
    				LocalDateTime dataAtual = response.getDataHora();
    				Indicadores indicador = buscarOuCriarIndicador(moeda);
    				
    				TypedQuery<Cotacoes> query = manager.createQuery(
    				        "SELECT c FROM Cotacoes c WHERE c.dataHora = :dataHora AND c.indicadores.description = :description", Cotacoes.class);
    				    query.setParameter("dataHora", dataAtual);
    				    query.setParameter("description", moeda);
    				    
    				 List<Cotacoes> cotacoesExistentes = query.getResultList();
    				 
    				 if(cotacoesExistentes.isEmpty()) {
    					 Cotacoes novaCotacao = new Cotacoes();
    					 novaCotacao.setDataHora(dataAtual);
    					 novaCotacao.setValor(response.getBid());
    					 novaCotacao.setIndicadores(indicador);
    					 
    					 manager.persist(novaCotacao);
    				 } else {
    					 Cotacoes cotacaoExistente = cotacoesExistentes.get(0);
    					 
    					 cotacaoExistente.setValor(response.getBid());

    				     manager.merge(cotacaoExistente);
    				 }
    				}
    			transaction.commit();
    	} catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
            	transaction.rollback(); 
            }
            e.printStackTrace();
        } finally {
            if (manager != null) {
                manager.clear(); 
            }
        }
    }
    
    public void atualizarAcoes(String symbol) {
        EntityTransaction tx = null;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -1); 

            List<APIResponse> apiResponses = apiAlphaVantage.getStockHistoryData(symbol);

            Calendar hoje = Calendar.getInstance();
            hoje.set(Calendar.HOUR_OF_DAY, 0);
            hoje.set(Calendar.MINUTE, 0);
            hoje.set(Calendar.SECOND, 0);
            hoje.set(Calendar.MILLISECOND, 0);

            tx = manager.getTransaction();
            if (!tx.isActive()) {
                tx.begin();
            }

            for (APIResponse response : apiResponses) {
                LocalDateTime dataAtual = response.getDataEHora();

                Indicadores indicador = buscarOuCriarIndicador(symbol);

                TypedQuery<Cotacoes> query = manager.createQuery(
                    "SELECT c FROM Cotacoes c WHERE c.dataHora = :dataHora AND c.indicadores.description = :description", Cotacoes.class);
                query.setParameter("dataHora", dataAtual);
                query.setParameter("description", symbol);

                List<Cotacoes> cotacoesExistentes = query.getResultList();

                if (cotacoesExistentes.isEmpty()) {
                    Cotacoes novaCotacao = new Cotacoes();
                    novaCotacao.setDataHora(dataAtual);
                    novaCotacao.setValor(response.getFechamento());
                    novaCotacao.setIndicadores(indicador);

                    manager.persist(novaCotacao);
                } else {
                    Cotacoes cotacaoExistente = cotacoesExistentes.get(0);
                    cotacaoExistente.setValor(response.getFechamento());

                    manager.merge(cotacaoExistente);
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

}
