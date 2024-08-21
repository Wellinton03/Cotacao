package service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import entity.Cotacoes;
import util.Transacional;

@ApplicationScoped
public class CotacoesService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Cotacoes porId(Long id) {
        return manager.find(Cotacoes.class, id);
    }

    public List<Cotacoes> todasCot() {
        return manager.createQuery("from Cotacoes ", Cotacoes.class).getResultList();
    }

    @Transacional
    public void salvar(Cotacoes cotacao) {
        if (cotacao.getId() == null) {
            manager.persist(cotacao);
        } else {
            manager.merge(cotacao);  
        }
    }

    @Transacional
    public void excluir(Cotacoes cotacoes) {
        cotacoes = porId(cotacoes.getId());
        if (cotacoes != null) {
            manager.remove(cotacoes);
        }
    }
}
