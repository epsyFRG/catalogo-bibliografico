package emilianomassari.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "magazine")
public class Magazine extends CatalogItem {

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicity", nullable = false)
    private Periodicity periodicity;

    public Magazine() {
        super();
    }

    public Magazine(String isbn, String title, int year, int pages, Periodicity periodicity) {
        super.setIsbn(isbn);
        super.setTitle(title);
        super.setYear(year);
        super.setPages(pages);
        this.periodicity = periodicity;
    }


    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

}
