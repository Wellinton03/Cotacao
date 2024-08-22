package Controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import entity.Cotacoes;
import entity.Indicadores;
import service.CotacoesService;
import service.IndicadoresService;

@Named
@RequestScoped
public class CotacoesControllerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CotacoesService cotacoesService;
    
    
    @Inject
    private IndicadoresService indicadorService;

    private Indicadores indicadores;
    
@Inject
private Indicadores indicador() {
if (indicadores == null) {
	indicadores = new Indicadores();
}
 return indicadores;
}
    @Inject
    private Cotacoes selectedCotacao;

    private List<Cotacoes> listaCotacoes;
    private List<Indicadores> listaIndicadores;

    public void todosIndicadores() {
        listaIndicadores = indicadorService.todosIndicadores();
    }

    public List<Indicadores> getListaIndicadores() {
    	if(listaIndicadores == null) {
    		listaIndicadores = indicadorService.todosIndicadores();
    	}
        return listaIndicadores;
    }
    	

    public List<Cotacoes> getListaCotacoes() {
    	if(listaCotacoes == null) {
    		 listaCotacoes = cotacoesService.todasCotacoes();
    	}
        return listaCotacoes;
    }

    public Cotacoes getSelectedCotacao() {
        return selectedCotacao;
    }

    public void setSelectedCotacao(Cotacoes selectedCotacao) {
        this.selectedCotacao = selectedCotacao;
    }

    public void todasCotacoes() {
        listaCotacoes = cotacoesService.todasCotacoes();
    }

    public void salvar() {
    	System.out.println("chegou");
        if (selectedCotacao.getIndicadores() == null) {
        	System.out.println("Cotacao a ser salva: " + selectedCotacao);
        	System.out.println("Indicador selecionado: " + selectedCotacao.getIndicadores());
            return;
        }else {
        
        
        cotacoesService.salvar(selectedCotacao);
        }
    }

    public void excluir() {
        if (selectedCotacao != null) {
            cotacoesService.excluir(selectedCotacao);
            listaCotacoes.remove(selectedCotacao);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cotação Removida"));
            PrimeFaces.current().ajax().update("mainForm:messages", "mainForm:idCotacoes");
        }
    }
}
