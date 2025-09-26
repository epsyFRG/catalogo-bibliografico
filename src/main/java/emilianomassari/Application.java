package emilianomassari;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class Application {

    public static void main(String[] args) {

        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliotecaPU");
            EntityManager em = emf.createEntityManager();

            System.out.println("Connessione riuscita");

            em.close();
            emf.close();
        } catch (Exception e) {
            System.out.println("Connessione non riuscita");
            e.printStackTrace();
        }
    }
}
