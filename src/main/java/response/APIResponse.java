package response;

import java.time.LocalDateTime;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APIResponse {

    @JsonProperty("4. close")
    private Double fechamento;

    @JsonProperty("2. high")
    private Double alta;

    @JsonProperty("3. low")
    private Double baixa;

    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime dataEHora;

	public Double getFechamento() {
		return fechamento;
	}

	public void setFechamento(Double fechamento) {
		this.fechamento = fechamento;
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

	public LocalDateTime getDataEHora() {
		return dataEHora;
	}

	public void setDataEHora(LocalDateTime dataEHora) {
		this.dataEHora = dataEHora;
	}

   
}
