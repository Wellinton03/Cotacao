package service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entity.Indicadores;
import util.Transacional;

public class IndicadoresService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public IndicadoresService() {
	}

	public IndicadoresService(EntityManager manager) {
		this.manager = manager;
	}

	public Indicadores porId(Long Id) {
		return manager.find(Indicadores.class, Id);
	}

	public List<Indicadores> pesquisar(String description) {
		TypedQuery<Indicadores> query = manager.createQuery("from Indicadores where description like :description ",
				Indicadores.class);
		query.setParameter("description", description + "%");

		return query.getResultList();
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
