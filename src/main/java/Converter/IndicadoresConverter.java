package Converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import entity.Indicadores;
import service.IndicadoresService;

@FacesConverter("indicadoresConverter")
public class IndicadoresConverter implements Converter {

    private List<Indicadores> listaIndicadores = new ArrayList<>(); 

    private IndicadoresService indicadoresService;
    
    @Inject
    private IndicadoresService getService() {
    	if (indicadoresService == null) {
    		indicadoresService = new IndicadoresService();
    		
    	} 
    	return indicadoresService;
    }
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        Long id = Long.valueOf(value);
        for (Indicadores indicadores : listaIndicadores) {
            if (id.equals(getService().porId(id))) {
                return indicadores;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        Indicadores indicadores = (Indicadores) value;
        return indicadores.getId().toString();
    }
}