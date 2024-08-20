package Components;

/*import java.io.Serializable;
import java.util.ArrayList;
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
public class CrudView implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Cotacoes> cotacoes;

    private Cotacoes selectedCotacao;

    private List<Cotacoes> selectedCotacoes;

    @Inject
    private CotacoesService cotacoesService;

    @PostConstruct
    public void init() {
        this.cotacoes = this.cotacoesService.todasCotacoes();
        this.selectedCotacoes = new ArrayList<>();
    }

    public List<Cotacoes> getCotacoes() {
        return cotacoes;
    }

    public Cotacoes getSelectedCotacao() {
        return selectedCotacao;
    }

    public void setSelectedCotacao(Cotacoes selectedCotacao) {
        this.selectedCotacao = selectedCotacao;
    }

    public List<Cotacoes> getSelectedCotacoes() {
        return selectedCotacoes;
    }

    public void setSelectedCotacoes(List<Cotacoes> selectedCotacoes) {
        this.selectedCotacoes = selectedCotacoes;
    }

    public void openNew() {
        this.selectedCotacao = new Cotacoes();
    }

    public void saveCotacao() {
        if (this.selectedCotacao.getId() == null) {
            this.cotacoes.add(selectedCotacao);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cotação Adicionada"));
        } else {
            this.cotacoesService.salvar(this.selectedCotacao);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cotação Atualizada"));
        }

        PrimeFaces.current().executeScript("PF('manageCotacaoDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cotacoes");
    }

    public void deleteCotacao() {
        this.cotacoesService.excluir(this.selectedCotacao);
        this.cotacoes.remove(this.selectedCotacao);
        this.selectedCotacoes.remove(this.selectedCotacao);
        this.selectedCotacao = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cotação Removida"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cotacoes");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedCotacoes()) {
            int size = this.selectedCotacoes.size();
            return size > 1 ? size + " cotações selecionadas" : "1 cotação selecionada";
        }

        return "Excluir";
    }

    public boolean hasSelectedCotacoes() {
        return this.selectedCotacoes != null && !this.selectedCotacoes.isEmpty();
    }

    public void deleteSelectedCotacoes() {
        for (Cotacoes cotacao : this.selectedCotacoes) {
            this.cotacoesService.excluir(cotacao);
        }
        this.cotacoes.removeAll(this.selectedCotacoes);
        this.selectedCotacoes = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cotações Removidas"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-cotacoes");
        PrimeFaces.current().executeScript("PF('dtCotacoes').clearFilters()");
    }
}
*/
