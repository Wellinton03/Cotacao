package controller;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import entity.Indicadores;


public class IndicadoresController implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
		private EntityManager manager;
		
		
		public IndicadoresController() {
			
		}
		
		public IndicadoresController(EntityManager manager) {
			this.manager = manager;
		}
		
		public Indicadores porId(Long Id) {
			return manager.find(Indicadores.class, Id);
		}
		
		public List<Indicadores> pesquisar(String description) {
			TypedQuery<Indicadores> query = manager
					.createQuery("from Indicadores where description like :description ", Indicadores.class);
			 query.setParameter("description", description + "%");
			
			return query.getResultList();
		}
		
		public Indicadores salvar( Indicadores indicadores) {
			return manager.merge(indicadores);
		}
		
		public void excluir(Indicadores indicadores) {
			indicadores = porId(indicadores.getId());
			manager.remove(indicadores);
		}
	}

