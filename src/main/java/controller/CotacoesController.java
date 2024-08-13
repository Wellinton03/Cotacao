package controller;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;


public class CotacoesController implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
		private EntityManager manager;
		
		
		public CotacoesController() {
			
		}
		
		public CotacoesController(EntityManager manager) {
			this.manager = manager;
		}
		
		public CotacoesController porId(Long Id) {
			return null;
		}
		
		public List<CotacoesController> pesquisar(String desciption) {
			return null;
		}
		
		public CotacoesController adicionar( CotacoesController cotacoesController) {
			return null;
		}
		
		public void excluir(CotacoesController cotacoesController) {
			
		}
	}

