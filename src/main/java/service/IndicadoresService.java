package service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import entity.Indicadores;
@Named
@ApplicationScoped
public class IndicadoresService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Indicadores porId(Long Id) {
		return manager.find(Indicadores.class, Id);
	}

	public List<Indicadores> buscar(String description) {
		TypedQuery<Indicadores> query = manager.createQuery("from Indicadores where description like :description ",
				Indicadores.class);
		query.setParameter("description", description + "%");

		return query.getResultList();
	}

	public List<Indicadores> todosIndicadores() {
		return manager.createQuery("from Indicadores", Indicadores.class).getResultList();
	}
	
	public void salvar(Indicadores indicadores) {
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            if (indicadores.getId() == null) {
                manager.persist(indicadores);
            } else {
                manager.merge(indicadores);  
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw e;
        }
    }

    public void excluir(Indicadores indicadores) {
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            manager.remove(indicadores.getId());
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw e;
        }
    }
}
