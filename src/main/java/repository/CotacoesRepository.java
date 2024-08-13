package repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

public class CotacoesRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private EntityManager manager;
	
	
	public void CotacoesRespository() {
		
	}
	
	public CotacoesRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public List<CotacoesRepository> pesquisar(String description){
		return null;
	}
}
