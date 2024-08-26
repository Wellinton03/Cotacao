package Controller;

import java.io.Serializable;
import java.util.Date;
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

	private String valor;
	private Indicadores indicadores;
	private Date dataHora;

	private List<Cotacoes> listaCotacoes;
	private List<Indicadores> listaIndicadores;

	@Inject
	private CotacoesService cotacoesService;

	public void initNewCotacao() {
		selectedCotacao = new Cotacoes();
		selectedCotacao.setIndicadores(new Indicadores());
	}

	public void todosIndicadores() {
		listaIndicadores = indicadorService.todosIndicadores();
	}

	public void todasCotacoes() {
		listaCotacoes = cotacoesService.todasCotacoes();
	}

	public void salvar() {
		System.out.println("chegou");
		selectedCotacao = new Cotacoes();
		System.out.println("selectedCotacao " + selectedCotacao);
		if (selectedCotacao != null) {
			Indicadores indicador = selectedCotacao.getIndicadores();
			System.out.println("Indicador selecionado: " + indicador);
			cotacoesService.salvar(selectedCotacao);
			listaCotacoes = cotacoesService.todasCotacoes();
		} else {
			System.out.println("selectedCotacao é nulo");
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

}
