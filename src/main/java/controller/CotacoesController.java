package controller;

import entity.CotacoesEntity;

public class CotacoesController {

	private CotacoesEntity cotacoesEntity;
	
	public CotacoesController() {
		this.cotacoesEntity = new CotacoesEntity();
	}
	
	
	public void saveCotacao(Long id, String description) {
		this.cotacoesEntity = new CotacoesEntity(id, description);
		
		
		
	}
}
