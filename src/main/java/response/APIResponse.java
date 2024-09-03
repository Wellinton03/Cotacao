package response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class APIResponse {

    @JsonProperty("date")
    private Date date;

    @JsonProperty("high")
    private Double high;

    @JsonProperty("low")
    private Double low;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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
}


