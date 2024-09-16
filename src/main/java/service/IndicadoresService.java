package service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.Cotacoes;
import entity.Indicadores;

@Named
@ApplicationScoped
public class IndicadoresService implements Serializable {

	private static final long serialVersionUID = 1L;

	private EntityManager manager;

	public IndicadoresService() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("cotacoesDatabase");
		manager = emf.createEntityManager();
	}

	public Indicadores porId(Long Id) {
		return manager.find(Indicadores.class, Id);
	}

	public List<Indicadores> buscar(String description) {
		TypedQuery<Indicadores> query = manager.createQuery("from Indicadores where description like :description ", Indicadores.class);
		query.setParameter("description", "%" + description + "%");

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
			indicadores = manager.merge(indicadores);
			manager.remove(indicadores);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
			throw e;
		}
	}
}
