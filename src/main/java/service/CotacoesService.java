package service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Cotacoes;
import entity.Indicadores;
@Named
@ApplicationScoped
public class CotacoesService implements Serializable {

    private static final long serialVersionUID = 1L;

    private EntityManager manager;
    
    public CotacoesService() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("cotacoesDatabase");
		manager = emf.createEntityManager();
	}

    public Cotacoes porId(Long id) {
        return manager.find(Cotacoes.class, id);
    }
    

    public List<Cotacoes> todasCotacoes() {
        return manager.createQuery("from Cotacoes ", Cotacoes.class).getResultList();
    }

    public void salvar(Cotacoes cotacoes) {
    	System.out.println("chegou");
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
}
