package emilianomassari.dao;

import emilianomassari.entities.Book;
import emilianomassari.entities.CatalogItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CatalogDAO {

    private final EntityManager entityManager;

    public CatalogDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // salva
    public void save(CatalogItem item) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(item);
        transaction.commit();
        System.out.println("Elemento salvato: " + item);
    }

    // trova per isbn
    public CatalogItem findByIsbn(String isbn) {
        CatalogItem found = entityManager.find(CatalogItem.class, isbn);
        if (found == null) {
            System.out.println("Elemento con ISBN " + isbn + " non trovato!");
        }
        return found;
    }

    // rimuovi per isbn
    public void findByIsbnAndDelete(String isbn) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        CatalogItem item = entityManager.find(CatalogItem.class, isbn);
        if (item != null) {
            entityManager.remove(item);
            System.out.println("Elemento rimosso: " + item);
        } else {
            System.out.println("Elemento con ISBN " + isbn + " non trovato!");
        }
        transaction.commit();
    }

    // trova per anno
    public List<CatalogItem> findByYear(int year) {
        TypedQuery<CatalogItem> query = entityManager.createQuery(
                "SELECT c FROM CatalogItem c WHERE c.year = :year", CatalogItem.class
        );
        query.setParameter("year", year);
        return query.getResultList();
    }

    // trova per autore
    public List<Book> findByAuthor(String author) {
        TypedQuery<Book> query = entityManager.createQuery(
                "SELECT b FROM Book b WHERE LOWER(b.author) = LOWER(:author)", Book.class
        );
        query.setParameter("author", author);
        return query.getResultList();
    }

    // trova per titolo
    public List<CatalogItem> findByTitleContaining(String keyword) {
        TypedQuery<CatalogItem> query = entityManager.createQuery(
                "SELECT c FROM CatalogItem c WHERE LOWER(c.title) LIKE LOWER(:keyword)", CatalogItem.class
        );
        query.setParameter("keyword", "%" + keyword + "%");
        return query.getResultList();
    }

    // trova tutti gli elementi
    public List<CatalogItem> findAll() {
        TypedQuery<CatalogItem> query = entityManager.createQuery(
                "SELECT c FROM CatalogItem c", CatalogItem.class
        );
        return query.getResultList();
    }
}
