package response;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APIResponse {

    @JsonProperty("code")
    private String codMoeda;

    @JsonProperty("codein")
    private String codMoedaConversion;

    @JsonProperty("name")
    private String nomeMoeda;

    @JsonProperty("high")
    private Double alta;

    @JsonProperty("low")
    private Double baixa;

    @JsonProperty("create_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataEHora;

    public String getCodMoeda() {
        return codMoeda;
    }

    public void setCodMoeda(String codMoeda) {
        this.codMoeda = codMoeda;
    }

    public String getCodMoedaConversion() {
        return codMoedaConversion;
    }

    public void setCodMoedaConversion(String codMoedaConversion) {
        this.codMoedaConversion = codMoedaConversion;
    }

    public String getNomeMoeda() {
        return nomeMoeda;
    }

    public void setNomeMoeda(String nomeMoeda) {
        this.nomeMoeda = nomeMoeda;
    }

    public Double getAlta() {
        return alta;
    }

    public void setAlta(Double alta) {
        this.alta = alta;
    }

    public Double getBaixa() {
        return baixa;
    }

    public void setBaixa(Double baixa) {
        this.baixa = baixa;
    }

    public Date getDataEHora() {
        return dataEHora;
    }

    public void setDataEHora(Date dataEHora) {
        this.dataEHora = dataEHora;
    }
}
