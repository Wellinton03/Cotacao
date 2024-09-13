package Controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import entity.Indicadores;
import service.IndicadoresService;

@Named
@ViewScoped
public class IndicadoresControllerBean implements Serializable {

	private List<Indicadores> listaIndicadores;
	private static final long serialVersionUID = 1L;
	
	private Indicadores selectedIndicador;
	
	private String termoPesquisa;
	
	@Inject
	private IndicadoresService indicadorService;
	
 	public List<Indicadores> getListaIndicadores() {
		if (listaIndicadores == null) {
			listaIndicadores = indicadorService.todosIndicadores();
		}
		return listaIndicadores;
	}
 	
 	public void initNewIndicador() {
 	    selectedIndicador = new Indicadores();
 	    
 	}
 	
 	public void pesquisa() {
 		listaIndicadores = indicadorService.buscar(termoPesquisa);
 	}

 	public void salvar() {
 	    if (selectedIndicador != null) {
 	    		
 	    	 if (selectedIndicador.getDescription() != null) {
 	            selectedIndicador.setDescription(selectedIndicador.getDescription().toUpperCase());
 	        }
 	        indicadorService.salvar(selectedIndicador);
 	        listaIndicadores = indicadorService.todosIndicadores();
 	        
 	        selectedIndicador = null;
 	        
 	        FacesContext.getCurrentInstance().addMessage(null,
 	        		new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Indicador salvo com sucesso!"));
 	    } else {
 	    	FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_INFO, "Falha", "Indicador não foi salvo"));
 	    }
 	}

	public void excluir() {
		if (selectedIndicador != null) {
			indicadorService.excluir(selectedIndicador);
			selectedIndicador = null;
			listaIndicadores = indicadorService.todosIndicadores();
			
			selectedIndicador = null;
			
			 FacesContext.getCurrentInstance().addMessage(null,
		                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Indicador excluído com sucesso!"));
		}else {
			 FacesContext.getCurrentInstance().addMessage(null,
		                new FacesMessage(FacesMessage.SEVERITY_INFO, "Falha", "Indicador não foi excluído"));
		}
	}
	
	public String getTermoPesquisa() {
		return termoPesquisa;
	}
    
    public void setTermoPesquisa(String pesquisa) {
		this.termoPesquisa = pesquisa;
	}

	public void setSelectedIndicador(Indicadores selectedIndicador) {
		this.selectedIndicador = selectedIndicador;
	}

	public Indicadores getSelectedIndicador() {
		return selectedIndicador;
	}
	
	public boolean isIndicadorSeleciona() {
		return selectedIndicador != null && selectedIndicador.getId() != null;
	}

	
	
	

}