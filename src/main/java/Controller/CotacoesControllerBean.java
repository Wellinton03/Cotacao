package Controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import entity.Cotacoes;
import entity.Indicadores;
import service.CotacoesService;

@Named
@ViewScoped
public class CotacoesControllerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CotacoesService cotacoesService;

    @Inject
    private Cotacoes selectedCotacao;

    private List<Cotacoes> listaCotacoes;
    private List<Indicadores> listaIndicadores;

     public CotacoesControllerBean() {
     }
    	

    public List<Cotacoes> getListaCotacoes() {
        return listaCotacoes;
    }

    public Cotacoes getSelectedCotacao() {
        return selectedCotacao;
    }

    public void setSelectedCotacao(Cotacoes selectedCotacao) {
        this.selectedCotacao = selectedCotacao;
    }

    public List<Indicadores> getListaIndicadores() {
        return listaIndicadores;
    }

    public void todasCotacoes() {
        listaCotacoes = cotacoesService.todasCot();
    }

    public void salvar() {
        try {
            System.out.println("valor " + selectedCotacao.getValor());
            System.out.println("Data: " + selectedCotacao.getDataHora());
            System.out.println("Indicador ID: " + selectedCotacao.getIndicadores());
            if (selectedCotacao.getId() == null) {
                cotacoesService.salvar(selectedCotacao);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cotação Adicionada"));
            } else {
                cotacoesService.salvar(selectedCotacao);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cotação Atualizada"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar a cotação"));
        }
        PrimeFaces.current().executeScript("PF('cotacaoDialogWidgetVar').hide()");
        PrimeFaces.current().ajax().update("mainForm:messages", "mainForm:idCotacoes");
    }

    public void excluir() {
        if (selectedCotacao != null) {
            cotacoesService.excluir(selectedCotacao);
            listaCotacoes.remove(selectedCotacao);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cotação Removida"));
            PrimeFaces.current().ajax().update("mainForm:messages", "mainForm:idCotacoes");
        }
    }

    public void excluirSelecionados(List<Cotacoes> cotacoesSelecionadas) {
        for (Cotacoes cotacao : cotacoesSelecionadas) {
            cotacoesService.excluir(cotacao);
        }
        listaCotacoes.removeAll(cotacoesSelecionadas);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cotações Removidas"));
        PrimeFaces.current().ajax().update("mainForm:messages", "mainForm:idCotacoes");
    }
}
