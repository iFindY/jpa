package de.arkadi.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = Book.FIND_ALL, query = "select b from Book b"),
        @NamedQuery(name = "ExpensiveBooks", query = "SELECT b FROM Book b WHERE b.unitCost > 29 AND b.nbOfPage < 700"),
        @NamedQuery(name = "ParamPosBooks", query = "SELECT b FROM Book b WHERE b.unitCost > ?1 AND b.nbOfPage < ?2"),
        @NamedQuery(name = "ParamNameBooks", query = "SELECT b FROM Book b WHERE b.unitCost > :cost AND b.nbOfPage < :pages"),
        @NamedQuery(name = "PublishedBooks", query = "SELECT b FROM Book b WHERE b.publicationDate < :pubDate"),
})
public class Book extends Item implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Column(length = 15)
    @XmlAttribute
    @NotNull
    @Size(max = 15)
    private String isbn;

    @Column(name = "nb_of_pages")
    @XmlElement(name = "nb-of-pages")
    @Min(1)
    private Integer nbOfPage;

    @Column(name = "publication_date")
    @XmlElement(name = "publication-date")
    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @Enumerated(EnumType.STRING)
    private Language language;

    @OneToMany
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_fk"), inverseJoinColumns = @JoinColumn(name = "author_fk"))
    private Set<Author> authors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "publisher_pk")
    private Publisher publisher;

    @Transient
    private Float price;

    @Transient
    private String number;

    // ======================================
    // =              Constant              =
    // ======================================

    public static final String FIND_ALL = "Book.findAll";

    // ======================================
    // =            Constructors            =
    // ======================================


    public Book() {
    }

    public Book(String title, Float price) {
        this.title = title;
        this.price = price;
    }

    public Book(String title, String number) {
        this.title = title;
        this.number = number;
    }

    public Book(String title, Float price, String number) {
        this.title = title;
        this.price = price;
        this.number = number;
    }

    public Book(String title, String description, Float unitCost, String isbn, Integer nbOfPage) {
        this.title = title;
        this.description = description;
        this.unitCost = unitCost;
        this.isbn = isbn;
        this.nbOfPage = nbOfPage;
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getNbOfPage() {
        return nbOfPage;
    }

    public void setNbOfPage(Integer nbOfPage) {
        this.nbOfPage = nbOfPage;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getPrice() {
        return price;
    }

    // ======================================
    // =    hashcode, equals & toString     =
    // ======================================


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Book book = (Book) o;

        if (!isbn.equals(book.isbn)) return false;
        if (language != book.language) return false;
        if (nbOfPage != null ? !nbOfPage.equals(book.nbOfPage) : book.nbOfPage != null) return false;
        if (publicationDate != null ? !publicationDate.equals(book.publicationDate) : book.publicationDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + isbn.hashCode();
        result = 31 * result + (nbOfPage != null ? nbOfPage.hashCode() : 0);
        result = 31 * result + (publicationDate != null ? publicationDate.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(title);
        return sb.toString();
    }
}