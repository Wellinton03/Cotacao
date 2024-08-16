package Controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entity.Indicadores;
import service.IndicadoresService;

@Named
@ViewScoped
public class IndicadoresControllerBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private IndicadoresService indicadoresService;
	private List<Indicadores> listaIndicadores;
	 
	public void todosIndicadores() {
		listaIndicadores = indicadoresService.todosIndicadores();
	}
	public List<Indicadores> getListaIndicadores() {
		return listaIndicadores;
	}
	}
	
	
