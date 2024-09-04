package Controller;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import DTO.FiltroDTO;
import DTO.IndicadorDTO;
import entity.Cotacoes;
import entity.Indicadores;
import service.CotacoesService;
import service.IndicadoresService;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;


@Named
@ViewScoped
public class CotacoesControllerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CotacoesService cotacoesService;
    
    @Inject
    private IndicadoresService indicadorService;
    
    private Cotacoes selectedCotacao;

    private String termoPesquisa;
    
    private String selectedFilter;
    private Date dataInicial;
    private Date dataFinal;
    private Long idIndicadores;
    
    private BarChartModel barChartModel;
    
    private List<Cotacoes> listaCotacoes;
    private List<Indicadores> listaIndicadores;
    private List<IndicadorDTO> indicadoresFiltrados;
    private List<FiltroDTO> cotacoesFiltradas;

    public void initNewCotacao() {
        selectedCotacao = new Cotacoes();
        selectedCotacao.setIndicadores(new Indicadores());
        listaIndicadores = indicadorService.todosIndicadores();
    }
    
    public void initNewFiltro() {
    	selectedFilter = null;
    	dataInicial = null;
    	dataFinal = null;
    	idIndicadores = null;
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
    
    
    public List<IndicadorDTO> getListaIndicadoresFiltrados() {
    	if (indicadoresFiltrados == null) {
    		indicadoresFiltrados = cotacoesService.porIndicador();
    	}
    	
    	
    	return indicadoresFiltrados;
    }
    
    public List<FiltroDTO> getListaCotacoesFiltradas() {
    	if(cotacoesFiltradas == null) {
    	cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(dataInicial, dataFinal, idIndicadores);
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
    	cotacoesFiltradas =  cotacoesService.buscarPorPeriodoEIndicador(getDataAnterior(new Date(), 1), new Date(), idIndicadores );
    }
    
    public void filtro3Dias() {
    	cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(getDataAnterior(new Date(), 3), new Date(), idIndicadores );
    }
    
    public void filtro5Dias() {
    	cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(getDataAnterior(new Date(), 5), new Date(), idIndicadores);
    }
    
    public void filtro10Dias() {
    	cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(getDataAnterior(new Date(), 10), new Date(), idIndicadores);
    }
    
    
    public void filtro15Dias() {
    	cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(getDataAnterior(new Date(), 15), new Date(), idIndicadores);
    }
    
    public void filtro30Dias() {
    	cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(getDataAnterior(new Date(), 30), new Date(), idIndicadores);
    }
    
    public void filtroCustom() {
    	 cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(dataInicial, dataFinal, idIndicadores);
    }
    
    public static Date getDataAnterior(Date dataBase, int dias) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(dataBase);
    	System.out.println(dataBase);
    	calendar.add(Calendar.DAY_OF_MONTH, -dias);
    	System.out.println(calendar.getTime());
    	return calendar.getTime();
    }
    
    public BarChartModel getBarChartModel() {
    	if (barChartModel == null) {
    		createBarModel();
    	}
    	return barChartModel;
    }
    
    private void createBarModel() {
        if (cotacoesFiltradas == null) {
            cotacoesFiltradas = cotacoesService.buscarPorPeriodoEIndicador(dataInicial, dataFinal, idIndicadores);
        }
        barChartModel = new BarChartModel();

        ChartSeries cotacoesSeries = new ChartSeries();
        cotacoesSeries.setLabel("Cotações");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (FiltroDTO cotacao : cotacoesFiltradas) {
        	String formattedDate = dateFormat.format(cotacao.getDataHora());
            Double value = cotacao.getValor();
            String label = formattedDate + " - R$ " + String.format("%.2f", value);
            cotacoesSeries.set(label, value);
        }

        barChartModel.addSeries(cotacoesSeries);

        barChartModel.setTitle("Gráfico de Cotações");
        barChartModel.setLegendPosition("ne");
        barChartModel.setAnimate(true);
        barChartModel.setShowDatatip(false);

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

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	

	public Long getIdIndicadores() {
		return idIndicadores;
	}

	public void setIdIndicadores(Long idIndicadores) {
		this.idIndicadores = idIndicadores;
	}

}
    

