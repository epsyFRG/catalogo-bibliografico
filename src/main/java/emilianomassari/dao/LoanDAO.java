package emilianomassari.dao;

import emilianomassari.entities.Loan;
import emilianomassari.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;


public class LoanDAO {
    private final EntityManager entityManager;

    public LoanDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Loan loan) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(loan);
        transaction.commit();
        System.out.println("Prestito salvato per utente: " + loan.getUser().getNome());
    }

    public List<Loan> findByUser(User user) {
        TypedQuery<Loan> query = entityManager.createQuery(
                "SELECT l FROM Loan l WHERE l.user = :user", Loan.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

    public List<Loan> findExpiredLoans() {
        TypedQuery<Loan> query = entityManager.createQuery(
                "SELECT l FROM Loan l WHERE l.expectedReturnDate < :today AND l.actualReturnDate IS NULL", Loan.class);
        query.setParameter("today", LocalDate.now());
        return query.getResultList();
    }
}
