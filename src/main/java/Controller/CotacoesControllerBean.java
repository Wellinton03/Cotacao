package Controller;

import java.io.Serializable;
import java.util.List;

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
@ViewScoped
public class CotacoesControllerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CotacoesService cotacoesService;
    
    @Inject
    private IndicadoresService indicadorService;
    
    private String termoPesquisa;
    
    private Cotacoes selectedCotacao;

    private List<Cotacoes> listaCotacoes;
    private List<Indicadores> listaIndicadores;
    
    public void initNewCotacao() {
 	    selectedCotacao = new Cotacoes();
 	}
    
    public void todosIndicadores() {
        listaIndicadores = indicadorService.todosIndicadores();
    }
    
    public void todasCotacoes() {
        listaCotacoes = cotacoesService.todasCotacoes();
    }

    public void salvar() {
 	    System.out.println("selectedIndicador: " + selectedCotacao);
 	    if (selectedCotacao != null) {
 	        System.out.println("selectedIndicador não é nulo");
 	       cotacoesService.salvar(selectedCotacao);
 	        listaCotacoes = cotacoesService.todasCotacoes();
 	    } else {
 	        System.out.println("cotacoesService é nulo");
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
    
    public String getTermoPesquisa() {
		return termoPesquisa;
	}
    
    public void setTermoPesquisa(String pesquisa) {
		this.termoPesquisa = pesquisa;
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
    
    public boolean isCotacaoSeleciona() {
		System.out.println(selectedCotacao);
		return selectedCotacao != null && selectedCotacao.getId() != null;
	}
}
