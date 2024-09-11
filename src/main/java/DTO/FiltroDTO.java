package DTO;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class FiltroDTO {
    private LocalDateTime dataHora;
    private double valor;

    public FiltroDTO(LocalDateTime dataHora, double valor) {
        this.dataHora = dataHora;
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return "FiltroDTO{" +
               "dataHora=" + (dataHora != null ? dateFormat.format(dataHora) : "null") +
               ", valor=" + String.format("%.2f", valor) +
               '}';
    }
}
