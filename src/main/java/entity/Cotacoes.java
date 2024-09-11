package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Cotacoes")
public class Cotacoes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_Hora")
    private LocalDateTime dataHora;

    @Column(name = "valor")
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "id_indicador")
    private Indicadores indicadores;

    public Indicadores getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(Indicadores indicadores) {
        this.indicadores = indicadores;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataAtual) {
        this.dataHora = dataAtual;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Cotacoes {\n" + "  id=" + id + "\n" + "  dataHora='" + dataHora + "/n" + "\n" + "  valor='" + valor
                + '\'' + "\n" + "  idIndicador " + indicadores.getId() + "  " + indicadores.getDescription();

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cotacoes other = (Cotacoes) obj;
        return Objects.equals(this.id, other.id);
    }

}
