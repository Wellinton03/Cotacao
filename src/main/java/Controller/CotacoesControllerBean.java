package Controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import DTO.IndicadorDTO;
import entity.Cotacoes;
import entity.Indicadores;
import service.CotacoesService;
import service.IndicadoresService;

@Named
@ViewScoped
public class CotacoesControllerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private IndicadoresService indicadorService;

    private Cotacoes selectedCotacao;

    private String termoPesquisa;
    
    private List<Cotacoes> listaCotacoes;
    private List<Indicadores> listaIndicadores;
    private List<IndicadorDTO> listaFiltradaDeIndicadores; 

    @Inject
    private CotacoesService cotacoesService;

    public void initNewCotacao() {
        selectedCotacao = new Cotacoes();
        selectedCotacao.setIndicadores(new Indicadores());
        listaIndicadores = indicadorService.todosIndicadores();
    }

    public void todosIndicadores() {
        listaIndicadores = indicadorService.todosIndicadores();
    }

    public void todasCotacoes() {
        listaCotacoes = cotacoesService.todasCotacoes();
    }
     
    public void pesquisa() {
        listaCotacoes = cotacoesService.buscar(termoPesquisa);
    }
    
    

    public void salvar() {
        if (selectedCotacao.getIndicadores() != null) {
            cotacoesService.salvar(selectedCotacao);
            listaCotacoes = cotacoesService.todasCotacoes();
            
            selectedCotacao = null;
            
            FacesContext.getCurrentInstance().addMessage(null,
 	        		new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cotação salva com sucesso!"));
        } else {
        	FacesContext.getCurrentInstance().addMessage(null,
 	        		new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha", "Cotação não foi salva"));
        }
    }

    public void excluir() {
        if (selectedCotacao != null) {
            cotacoesService.excluir(selectedCotacao);
            listaCotacoes.remove(selectedCotacao);
            
            selectedCotacao = null;
            
            FacesContext.getCurrentInstance().addMessage(null,
 	        		new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cotação excluída com sucesso!"));
        }else {
        	FacesContext.getCurrentInstance().addMessage(null,
 	        		new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha", "Cotação não foi excluída"));
        }
    }
    
    public List<IndicadorDTO> getListaFiltradaDeIndicadores() {
    	return listaFiltradaDeIndicadores;
    }

    public List<Indicadores> getListaIndicadores() {
        if (listaIndicadores == null) {
            listaIndicadores = indicadorService.todosIndicadores();
        }
        return listaIndicadores;
    }

    public List<Cotacoes> getListaCotacoes() {
        if (listaCotacoes == null) {
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
        return selectedCotacao != null && selectedCotacao.getId() != null;
    }

    public String getTermoPesquisa() {
        return termoPesquisa;
    }

    public void setTermoPesquisa(String termoPesquisa) {
        this.termoPesquisa = termoPesquisa;
    }
    
    
}
