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

	public CotacoesService() {

	}

	public Cotacoes porId(Long id) {
		return manager.find(Cotacoes.class, id);
	}

	public List<Cotacoes> todasCotacoes() {
		return manager.createQuery("from Cotacoes", Cotacoes.class).getResultList();
	}

	@Transacional
	public Cotacoes salvar(Cotacoes cotacoes) {
		return manager.merge(cotacoes);

	}

	@Transacional
	public void Cotacoes(Cotacoes cotacoes) {
		cotacoes = porId(cotacoes.getId());
		manager.remove(cotacoes);
	}

}
