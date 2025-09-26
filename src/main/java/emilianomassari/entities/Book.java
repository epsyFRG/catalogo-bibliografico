package emilianomassari.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
public class Book extends CatalogItem {

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "genre")
    private String genre;

    public Book() {
        super();
    }

    public Book(String isbn, String title, int year, int pages, String author, String genre) {
        super.setIsbn(isbn);
        super.setTitle(title);
        super.setYear(year);
        super.setPages(pages);
        this.author = author;
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
