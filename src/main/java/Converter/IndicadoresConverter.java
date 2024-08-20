package Converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import entity.Indicadores;

@FacesConverter("indicadoresConverter")
public class IndicadoresConverter implements Converter {

    private List<Indicadores> listaIndicadores = new ArrayList<>(); 

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        Long id = Long.valueOf(value);
        for (Indicadores indicadores : listaIndicadores) {
            if (id.equals(indicadores.getId())) {
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
