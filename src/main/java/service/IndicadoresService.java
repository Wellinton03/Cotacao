package service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Indicadores;
import util.Transacional;
@Named
@ApplicationScoped
public class IndicadoresService implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Indicadores> getListaIndicadores;
	
	private EntityManager manager;
	
	public IndicadoresService() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("cotacoesDatabase");
        this.manager = factory.createEntityManager();
    }
	@PostConstruct
    public void init() {
        getListaIndicadores = todosIndicadores();
        System.out.println("Lista de indicadores populada: " + getListaIndicadores);
    }
	
	public List<Indicadores> getListaIndicadores(){
		return getListaIndicadores();
	}
	public Indicadores findByDescription(String description) {
		TypedQuery<Indicadores> query = manager.createQuery(" description from indicadores",
				Indicadores.class);
		try {
            return (Indicadores) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
	}

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
	
	public List<Indicadores> allDescriptions() {
		return manager.createQuery(" from Indicadores description", Indicadores.class).getResultList();
	}

	@Transacional
	public Indicadores salvar(Indicadores indicadores) {
		return manager.merge(indicadores);
	}

	@Transacional
	public void excluir(Indicadores indicadores) {
		indicadores = porId(indicadores.getId());
		manager.remove(indicadores);
	}
}
