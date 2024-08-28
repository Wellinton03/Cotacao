package Converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import entity.Indicadores;
import service.IndicadoresService;

@FacesConverter("indicadoresConverter")
public class IndicadoresConverter implements Converter, Serializable {

    private static final long serialVersionUID = 1L;
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
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            System.out.println("Valor recebido: " + value);
            Long id = Long.parseLong(value);
            System.out.println("ID convertido: " + id);
            Indicadores indicadores = getService().porId(id);
            System.out.println(indicadores);
            return indicadores;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Indicadores) {
            Indicadores indicadores = (Indicadores) value;
            return indicadores.getId() != null ? indicadores.getId().toString() : "";
        } else {
            return "";
        }
    }
}
