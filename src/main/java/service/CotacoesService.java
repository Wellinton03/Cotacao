package service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Cotacoes;
import entity.Indicadores;
@Named
@ApplicationScoped
public class CotacoesService implements Serializable {

    private static final long serialVersionUID = 1L;

    private EntityManager manager;
    
    public CotacoesService() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("cotacoesDatabase");
		manager = emf.createEntityManager();
	}

    public Cotacoes porId(Long id) {
        return manager.find(Cotacoes.class, id);
    }
    

    public List<Cotacoes> todasCotacoes() {
        return manager.createQuery("from Cotacoes ", Cotacoes.class).getResultList();
    }

    public void salvar(Cotacoes cotacoes) {
    	System.out.println("chegou no service " + cotacoes);
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            System.out.println("Iniciando transação para salvar: " + cotacoes);
            if (cotacoes.getId() == null) {
                manager.persist(cotacoes);
                System.out.println("Cotação persistida com ID: " + cotacoes.getId());
            } else {
                manager.merge(cotacoes);
                System.out.println("Cotação mesclada com ID: " + cotacoes.getId());
            }
            tx.commit();
            System.out.println("Transação commitada com sucesso");
        } catch (Exception e) {
            System.out.println("Exceção durante transação, fazendo rollback");
            tx.rollback();
            e.printStackTrace();
            throw e;
        }
    }

    public void excluir(Cotacoes cotacoes) {
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            cotacoes = manager.merge(cotacoes);
            manager.remove(cotacoes);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            throw e;
        }
    }
}
