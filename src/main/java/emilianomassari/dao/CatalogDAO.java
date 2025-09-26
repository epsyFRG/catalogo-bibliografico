package emilianomassari.dao;

import emilianomassari.entities.Book;
import emilianomassari.entities.CatalogItem;
import emilianomassari.entities.Magazine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CatalogDAO {
    private final EntityManager entityManager;

    public CatalogDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(CatalogItem item) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(item);
        transaction.commit();
        System.out.println("Elemento salvato: " + item.getTitle());
    }

    public CatalogItem findByIsbn(String isbn) {
        CatalogItem found = entityManager.find(CatalogItem.class, isbn);
        if (found == null) throw new RuntimeException("Elemento " + isbn + " non trovato");
        return found;
    }

    public List<CatalogItem> findAll() {
        TypedQuery<CatalogItem> query = entityManager.createQuery("SELECT c FROM CatalogItem c", CatalogItem.class);
        return query.getResultList();
    }

    public List<Book> findAllBooks() {
        TypedQuery<Book> query = entityManager.createQuery("SELECT b FROM Book b", Book.class);
        return query.getResultList();
    }

    public List<Magazine> findAllMagazines() {
        TypedQuery<Magazine> query = entityManager.createQuery("SELECT m FROM Magazine m", Magazine.class);
        return query.getResultList();
    }

    public void findByIsbnAndDelete(String isbn) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Query query = entityManager.createQuery("DELETE FROM CatalogItem c WHERE c.isbn = :isbn");
        query.setParameter("isbn", isbn);
        int numDeleted = query.executeUpdate();
        transaction.commit();
        System.out.println(numDeleted + " elementi cancellati");
    }
}
