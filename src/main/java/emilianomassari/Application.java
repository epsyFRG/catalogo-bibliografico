package emilianomassari;

import emilianomassari.dao.CatalogDAO;
import emilianomassari.dao.LoanDAO;
import emilianomassari.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bibliotecaPU");
        EntityManager em = emf.createEntityManager();

        CatalogDAO catalogDAO = new CatalogDAO(em);
        LoanDAO loanDAO = new LoanDAO(em);

        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("scegli un opzione (da 1 a 9, 0 peer uscire)");
            System.out.println("1 aggiungi elemento catalogo");
            System.out.println("2 rimuovi elemento catalogo");
            System.out.println("3 ricerca per ISBN");
            System.out.println("4 ricerca per anno pubblicazione");
            System.out.println("5 ricerca per autore");
            System.out.println("6 ricerca per titolo");
            System.out.println("7 aggiungi prestito");
            System.out.println("8 prestiti di un utente");
            System.out.println("9 prestiti scaduti");
            System.out.println("0 esci");

            System.out.print("scelta: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                // catalogo
                case 1 -> {
                    System.out.print("tipo elemento (book o magazine): ");
                    String tipo = scanner.nextLine().toLowerCase();

                    System.out.print("isbn: ");
                    String isbn = scanner.nextLine();
                    System.out.print("titolo: ");
                    String titolo = scanner.nextLine();
                    System.out.print("anno di pubblicazione: ");
                    int anno = Integer.parseInt(scanner.nextLine());
                    System.out.print("numero di pagine: ");
                    int pagine = Integer.parseInt(scanner.nextLine());

                    if (tipo.equals("book")) {
                        System.out.print("autore: ");
                        String autore = scanner.nextLine();
                        System.out.print("genere: ");
                        String genere = scanner.nextLine();
                        Book book = new Book(isbn, titolo, anno, pagine, autore, genere);
                        catalogDAO.save(book);
                    } else if (tipo.equals("magazine")) {
                        System.out.print("periodcità (settimanale, mensile o semestrale): ");
                        String periodicitaStr = scanner.nextLine().toUpperCase();
                        Periodicity periodicita = Periodicity.valueOf(periodicitaStr);
                        Magazine mag = new Magazine(isbn, titolo, anno, pagine, periodicita);
                        catalogDAO.save(mag);
                    } else {
                        System.out.println("tipo non valido");
                    }
                }

                case 2 -> {
                    System.out.print("elemento isbn da rimuovere: ");
                    String isbn = scanner.nextLine();
                    catalogDAO.findByIsbnAndDelete(isbn);
                }

                case 3 -> {
                    System.out.print("isbn da cercare: ");
                    String isbn = scanner.nextLine();
                    System.out.println(catalogDAO.findByIsbn(isbn));
                }

                case 4 -> {
                    System.out.print("anno di pubblicazione: ");
                    int anno = Integer.parseInt(scanner.nextLine());
                    catalogDAO.findByYear(anno).forEach(System.out::println);
                }

                case 5 -> {
                    System.out.print("autore: ");
                    String autore = scanner.nextLine();
                    catalogDAO.findByAuthor(autore).forEach(System.out::println);
                }

                case 6 -> {
                    System.out.print("parola chiave nel titolo: ");
                    String keyword = scanner.nextLine();
                    catalogDAO.findByTitleContaining(keyword).forEach(System.out::println);
                }

                // prestiti
                case 7 -> {
                    System.out.print("numero tessera utente: ");
                    int numeroTessera = Integer.parseInt(scanner.nextLine());
                    User user = em.find(User.class, numeroTessera);

                    System.out.print("elemento isbn: ");
                    String isbn = scanner.nextLine();
                    CatalogItem item = catalogDAO.findByIsbn(isbn);

                    Loan loan = new Loan(user, item, LocalDate.now());
                    loanDAO.save(loan);
                }

                case 8 -> {
                    System.out.print("numero tessera utente: ");
                    int numeroTessera = Integer.parseInt(scanner.nextLine());
                    User user = em.find(User.class, numeroTessera);
                    loanDAO.findByUser(user).forEach(System.out::println);
                }

                case 9 -> {
                    loanDAO.findExpiredLoans().forEach(System.out::println);
                }

                case 0 -> {
                    running = false;
                    System.out.println("chiusura applicazione...");
                }

                default -> System.out.println("scelta non valida!");
            }
            System.out.println();
        }

        scanner.close();
        em.close();
        emf.close();
    }
}
