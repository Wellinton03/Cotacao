package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import controller.IndicadoresController;
import entity.Indicadores;

public class SchemaGeneration {

	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("cotacoesDatabase");
		
		EntityManager manager = entityManagerFactory.createEntityManager();
		
		List<Indicadores> lista = manager.createQuery("from Indicadores" , Indicadores.class )
				.getResultList();
		
		System.out.println(lista);
		
		manager.close();
		entityManagerFactory.close();
	}

}
