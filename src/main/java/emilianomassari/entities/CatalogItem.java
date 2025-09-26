package emilianomassari.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "catalog_item")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class CatalogItem {

    @Id
    @Column(name = "isbn", nullable = false, length = 64)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "year")
    private int year;

    @Column(name = "pages")
    private int pages;

    public CatalogItem() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

}
