package Controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import DTO.FiltroDTO;
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
    private CotacoesService cotacoesService;
    
    @Inject
    private IndicadoresService indicadorService;
    
    private Cotacoes selectedCotacao;
    
    private Indicadores selectedIndicador;

    private String termoPesquisa;
    
    private String selectedAPI;
    private String selectedFilter;
    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;
    private Long indicadorId;
    private String indicadorDescription;
    
    private String startDate;
    private String finalDate;
    
    private BarChartModel barChartModel;
    
    private String iconSearch = "pi pi-search";
    private String iconSave = "pi pi-save";
    
    private List<Cotacoes> listaCotacoes;
    private List<Indicadores> listaIndicadores;
    private List<IndicadorDTO> indicadoresFiltrados;
    private List<FiltroDTO> cotacoesFiltradas;
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public void initNewCotacao() {
        selectedCotacao = new Cotacoes();
        listaIndicadores = indicadorService.todosIndicadores();
    }
    
    public void initNewFiltro() {
    	selectedFilter = null;
    	dataInicial = null;
    	dataFinal = null;
    	indicadorId = null;
    }
    
    public void initNewBuscaAPI() {
    	selectedAPI = null;
    	listaIndicadores = indicadorService.todosIndicadores();
    	dataInicial = null;
    	dataFinal = null;
    	selectedIndicador = new Indicadores();
    }
    
    public static String formatLocalDateTime(LocalDateTime dateTime) {
    	return dateTime.format(FORMATTER);
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
    
    public void deletaCotacao() {
    	cotacoesService.deletaCotacao();
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
    
    
    public List<IndicadorDTO> getListaIndicadoresFiltrados() {
    	if (indicadoresFiltrados == null) {
    		indicadoresFiltrados = cotacoesService.porIndicador();
    	}
    	
    	
    	return indicadoresFiltrados;
    }
    
    public List<FiltroDTO> getListaCotacoesFiltradas() {
    	if(cotacoesFiltradas == null) {
    	cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(dataInicial, dataFinal, indicadorId);
    }
    	return cotacoesFiltradas;
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
    
    public Integer diferenca(LocalDateTime dataInicial, LocalDateTime dataFinal) {
    	if(dataInicial != null && dataInicial != null) {
    		
    		long dias = ChronoUnit.DAYS.between(dataInicial, dataFinal);
    		return (int) dias;
    	}
    	
    	return null;
    }
    
    
    
    public void buscarAPI(String indicadorDescription, String selectedAPI, LocalDateTime dataInicial, LocalDateTime dataFinal) {
        
    	Integer dias = diferenca(dataInicial, dataFinal);
    	switch (selectedAPI) {
    	case "1":
    		chamarAcoes(indicadorDescription);
    		break;
    	case "2":
    		chamarMoedas(indicadorDescription, dias);
    		break;
    	}
    }
    
    public void chamarAcoes(String indicadorDescription) {
    	cotacoesService.atualizarAcoes(indicadorDescription);
    }
    
    public void chamarMoedas(String indicadorDescription, Integer dias) {
    	cotacoesService.atualizarMoedas(indicadorDescription, dias);
    }
    
    public void aplicarFiltro() {
        switch (selectedFilter) {
            case "1":
                filtro1Dia();
                break;
            case "3":
                filtro3Dias();
                break;
            case "5":
                filtro5Dias();
                break;
            case "10":
                filtro10Dias();
                break;
            case "15":
                filtro15Dias();
                break;
            case "30":
                filtro30Dias();
                break;
            case "custom":
                filtroCustom();
                break;
            default:
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um filtro válido."));
        }
        createBarModel();
    }
    
    
    public void filtro1Dia() {
    	cotacoesFiltradas =  cotacoesService.buscarPorPeriodoEIndicador(getDataAnterior(LocalDateTime.now(), 1), LocalDateTime.now(), indicadorId );
    }
    
    public void filtro3Dias() {
    	cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(getDataAnterior(LocalDateTime.now(), 3), LocalDateTime.now(), indicadorId );
    }
    
    public void filtro5Dias() {
    	cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(getDataAnterior(LocalDateTime.now(), 5), LocalDateTime.now(), indicadorId);
    }
    
    public void filtro10Dias() {
    	cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(getDataAnterior(LocalDateTime.now(), 10), LocalDateTime.now(), indicadorId);
    }
    
    
    public void filtro15Dias() {
    	cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(getDataAnterior(LocalDateTime.now(), 15), LocalDateTime.now(), indicadorId);
    }
    
    public void filtro30Dias() {
    	cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(getDataAnterior(LocalDateTime.now(), 30), LocalDateTime.now(), indicadorId);
    }
    
    public void filtroCustom() {
    	 cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(dataInicial, dataFinal, indicadorId);
    }
    
    public static LocalDateTime getDataAnterior(LocalDateTime dataBase, int dias) {
    	return dataBase.minusDays(dias);
    }
    
    public BarChartModel getBarChartModel() {
    	if (barChartModel == null) {
    		createBarModel();
    	}
    	return barChartModel;
    }
    
    private void createBarModel() {
        if (cotacoesFiltradas == null) {
            cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(dataInicial, dataFinal, indicadorId);
        }
        
        barChartModel = new BarChartModel();
        
        ChartSeries cotacoesSeries = new ChartSeries();
        cotacoesSeries.setLabel("Cotações");

        for (FiltroDTO cotacao : cotacoesFiltradas) {
        	 LocalDateTime dateTime = cotacao.getDataHora();
             
             String formattedDate = dateTime.format(FORMATTER);
             Double value = cotacao.getValor();
            cotacoesSeries.set(formattedDate, value);
        }

        barChartModel.addSeries(cotacoesSeries);
        
        barChartModel.setTitle("Gráfico de Cotações");
        barChartModel.setLegendPosition("ne");
        barChartModel.setAnimate(true);
        barChartModel.setShowDatatip(true);
        barChartModel.setExtender("customizeDatatip");

        Axis xAxis = barChartModel.getAxis(AxisType.X);
        xAxis.setLabel("Data e Valor");
        xAxis.setTickAngle(-45);
        xAxis.setTickInterval("1");

        Axis yAxis = barChartModel.getAxis(AxisType.Y);
        yAxis.setLabel("Valor");
        yAxis.setMin(0);
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
    
    
    public String getSelectedFilter() {
		return selectedFilter;
	}

	public void setSelectedFilter(String selectedFilter) {
		this.selectedFilter = selectedFilter;
	}

	public LocalDateTime getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDateTime dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDateTime getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDateTime dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Long getIndicadorId() {
		return indicadorId;
	}

	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}

	public String getIndicadorDescription() {
		return indicadorDescription;
	}

	public void setIndicadorDescription(String indicadorDescription) {
		this.indicadorDescription = indicadorDescription;
	}

	public String getSelectedAPI() {
		return selectedAPI;
	}

	public void setSelectedAPI(String selectedAPI) {
		this.selectedAPI = selectedAPI;
	}

	public Indicadores getSelectedIndicador() {
		return selectedIndicador;
	}

	public void setSelectedIndicador(Indicadores selectedIndicador) {
		this.selectedIndicador = selectedIndicador;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
	}

	
	public void changeIconLoad() {
        this.iconSearch = "pi pi-spin pi-spinner";
    }
	
	public void changeIconLoadSave() {
        this.iconSave = "pi pi-spin pi-spinner";
    }
	
	public void initIconSearch() {
		this.iconSearch = "pi pi-search";
	}
	
	public void initIconSave() {
		this.iconSave = "pi pi-save";
	}

	public String getIconSearch() {
		return iconSearch;
	}

	public void setIconSearch(String iconSearch) {
		this.iconSearch = iconSearch;
	}

	public String getIconSave() {
		return iconSave;
	}

	public void setIconSave(String iconSave) {
		this.iconSave = iconSave;
	}

}
    

