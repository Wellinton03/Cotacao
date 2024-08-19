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
import service.CotacoesService;

@Named
@ViewScoped
public class CotacoesControllerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CotacoesService cotacoesService;

    private Cotacoes selectedCotacao;

    private List<Cotacoes> listaCotacoes;

    @PostConstruct
    public void init() {
        todasCotacoes();
    }

    public List<Cotacoes> getListaCotacoes() {
        return listaCotacoes;
    }
    
    public void selecionarCotacao(Cotacoes cotacao) {
    	this.selectedCotacao = cotacao;
    }

    public Cotacoes getSelectedCotacao() {
        return selectedCotacao;
    }

    public void todasCotacoes() {
        listaCotacoes = cotacoesService.todasCotacoes();
    }

    public void salvar() {
    	try {
            if (selectedCotacao.getId() == null) {
                cotacoesService.salvar(selectedCotacao);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cotação Adicionada"));
            } else {
                cotacoesService.salvar(selectedCotacao);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cotação Atualizada"));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar cotação", e.getMessage()));
            e.printStackTrace();
        }

        PrimeFaces.current().executeScript("PF('cotacaoDialogWidgetVar').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cotacoes");

        selectedCotacao = null;
    }

    public void excluir() {
        if (selectedCotacao != null) {
            cotacoesService.excluir(selectedCotacao);
            listaCotacoes.remove(selectedCotacao);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cotação Removida"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-cotacoes");
        }
    }

    public void excluirSelecionados(List<Cotacoes> cotacoesSelecionadas) {
        for (Cotacoes cotacao : cotacoesSelecionadas) {
            cotacoesService.excluir(cotacao);
        }
        listaCotacoes.removeAll(cotacoesSelecionadas);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cotações Removidas"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cotacoes");
    }
}
