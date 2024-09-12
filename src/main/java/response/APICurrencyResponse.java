package response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class APICurrencyResponse {
	
	@JsonProperty("high")
	private Double high;
	
	@JsonProperty("low")
	private Double low;
	
	@JsonProperty("bid")
	private Double bid;
	
	@JsonProperty("timestamp")
	private LocalDateTime dataHora;
	
	public Double getHigh() {
		return high;
	}
	public void setHigh(Double high) {
		this.high = high;
	}
	public Double getLow() {
		return low;
	}
	public void setLow(Double low) {
		this.low = low;
	}
	public Double getBid() {
		return bid;
	}
	public void setBid(Double bid) {
		this.bid = bid;
	}
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
	
	@Override
	public String toString() {
		return "APICurrencyResponse [high=" + high + ", low=" + low + ", bid=" + bid + ", dataHora=" + dataHora + "]";
	}
	
	
	
}
