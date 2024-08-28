package Controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
        System.out.println("*** Iniciando Salvar!");
        if (selectedCotacao.getIndicadores() != null) {
            System.out.println("verificou " + selectedCotacao);
            cotacoesService.salvar(selectedCotacao);
            listaCotacoes = cotacoesService.todasCotacoes();
            System.out.println("chegou");
        } else {
            System.out.println("Indicador está nulo!");
        }
    }

    public void excluir() {
        if (selectedCotacao != null) {
            cotacoesService.excluir(selectedCotacao);
            listaCotacoes.remove(selectedCotacao);
        }
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
