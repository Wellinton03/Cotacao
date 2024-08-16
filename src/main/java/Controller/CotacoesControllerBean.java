package Controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entity.Cotacoes;
import service.CotacoesService;

@Named
@ViewScoped
public class CotacoesControllerBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CotacoesService cotacoesService;
	private List<Cotacoes> listaCotacoes;
	
	public void todasCotacoes() {
		listaCotacoes = cotacoesService.todasCotacoes();
	}
	
	public List<Cotacoes> listCotacoes() {
		return listaCotacoes;
	}
	
	
}
