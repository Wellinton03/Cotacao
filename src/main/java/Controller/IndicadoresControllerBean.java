package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import entity.Indicadores;
import service.IndicadoresService;

@Named
@RequestScoped
public class IndicadoresControllerBean implements Serializable {
	
	private List<Indicadores> listaIndicadores;
    private static final long serialVersionUID = 1L;
    @Inject
    private Indicadores selectedIndicador;
    
    @Inject
    private IndicadoresService indicadorService;
    
   
    
    public List<Indicadores> getListaIndicadores() {
    	if(listaIndicadores == null) {
    		listaIndicadores = indicadorService.todosIndicadores();
    	}
        return listaIndicadores;
    }
    
    public void salvar() {
    	System.out.println(selectedIndicador);
        indicadorService.salvar(selectedIndicador);
        listaIndicadores = indicadorService.todosIndicadores(); 
    }
    public void excluir() {
    	System.out.println(selectedIndicador.getId());
    	if (selectedIndicador != null) {
    		indicadorService.excluir(selectedIndicador);
    		listaIndicadores.remove(selectedIndicador);
    	}
    }

    public void setSelectedIndicador(Indicadores selectedIndicador) {
        this.selectedIndicador = selectedIndicador;
    }
    
    public Indicadores getSelectedIndicador() {
        return selectedIndicador;
    }
    


}