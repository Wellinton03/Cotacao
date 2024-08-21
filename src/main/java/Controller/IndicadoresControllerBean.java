package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
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
    
    private Indicadores selectedIndicador;
    private List<Indicadores> listaIndicadores = new ArrayList<>();

    @PostConstruct
    public void init() {
        todosIndicadores();
    }

    public void todosIndicadores() {
        listaIndicadores = indicadoresService.todosIndicadores();
    }

    public List<Indicadores> getListaIndicadores() {
        return listaIndicadores;
    }
    
    public Indicadores getSelectedIndicador() {
        return selectedIndicador;
    }
    

    public void setSelectedIndicador(Indicadores selectedIndicador) {
        this.selectedIndicador = selectedIndicador;
    }


}