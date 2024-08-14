package Controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class CadastroIndicadoresBean {
	
	private static Integer NUMERO = 0;
	
	public CadastroIndicadoresBean() {
		NUMERO++;
	}
	
	public Integer getNumero() {
		return NUMERO;
	}
}
