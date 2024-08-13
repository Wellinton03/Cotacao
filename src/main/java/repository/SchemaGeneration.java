package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import controller.CotacoesController;

public class SchemaGeneration {

	public static void main(String[] args) {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Cotacoes_database");
		
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		List<CotacoesController> lista = entityManager.createQuery("from Empresa" , CotacoesController.class )
				.getResultList();
		
		System.out.println(lista);
		
		entityManager.close();
		entityManagerFactory.close();
	}

}
