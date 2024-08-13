package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import controller.IndicadoresController;
import entity.Indicadores;

public class CamadaPersistencia {

	public static void main(String[] args) {
		
		EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("cotacoesDatabase");
		
		EntityManager manager = managerFactory.createEntityManager();
		
		manager.getTransaction().begin();
		
		
		IndicadoresController indicadoresController = new IndicadoresController(manager);
		Indicadores indicadores = new Indicadores();
		
		
		//Criar
		indicadores.setDescription("BitCoin");
		
		System.out.println("criando");
		
		
		//Salvar 
		indicadoresController.salvar(indicadores);
		
		//Buscar
		List<Indicadores> listaIndicadores = indicadoresController.pesquisar("");
		System.out.println(listaIndicadores);

		System.out.println("listando");
		
		manager.getTransaction().commit();
		
		manager.close();
		managerFactory.close();
		
		
		
		
		
		
		
		
	}
}
