package DTO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FiltroDTO {
    private Date dataHora;
    private double valor;

    public FiltroDTO(Date dataHora, double valor) {
        this.dataHora = dataHora;
        this.valor = valor;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
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
