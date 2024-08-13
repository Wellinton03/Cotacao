package entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Indicadores")
public class Indicadores implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column(name = "description", nullable = false)
	private String description;
	
	@Column(name = "Data_e_Hora")
	private LocalDateTime dataEHora;
	
	
	@Column(name = "valor")
	private Integer valor;
	
	
	public String toString() {
		return "Indicadores {\n" +
	            "  id=" + id + "\n" +
	            "  description='" + description + '\'' + "\n" +
	            "  valor=" + valor + "\n" +
	            "  dataEHora=" + dataEHora + "\n" +
	            '}' + "\n";
	}
	public LocalDateTime getDataEHora() {
		return dataEHora;
	}


	public void setDataEHora(LocalDateTime dataEHora) {
		this.dataEHora = dataEHora;
	}


	public Integer getValor() {
		return valor;
	}


	public void setValor(Integer valor) {
		this.valor = valor;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Indicadores() {
		
	}
	
	public Indicadores( Long id, String description) {
		this.id = id;
		this.description = description;
	}
	
	
}
