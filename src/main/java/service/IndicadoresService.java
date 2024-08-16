package service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entity.Indicadores;
import util.Transacional;

@ApplicationScoped
public class IndicadoresService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public IndicadoresService() {
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
