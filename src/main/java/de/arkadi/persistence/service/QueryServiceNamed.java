package de.arkadi.persistence.service;


import de.arkadi.persistence.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;


public class QueryServiceNamed {

    // ======================================
    // =             Attributes             =
    // ======================================

    private EntityManager em;

    // ======================================
    // =            Constructors            =
    // ======================================

    public QueryServiceNamed(EntityManager em) {
        this.em = em;
    }

    // ======================================
    // =           Public Methods           =
    // ======================================

    public void queryBooks() {

        Query query = em.createNamedQuery("ExpensiveBooks");
        List books = query.getResultList();

        for (int i = 0; i < books.size(); i++) {
            Book book = (Book) books.get(i);
            System.out.println(book);
        }
    }

    public void queryBooksTyped() {

        TypedQuery<Book> query = em.createNamedQuery(Book.FIND_ALL, Book.class);
        List<Book> books = query.getResultList();

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println(book);
        }
    }

    public void queryBooksPosition(Float unitCost, Integer nbOfPage) {

        TypedQuery<Book> query = em.createNamedQuery("ParamPosBooks", Book.class);
        query.setParameter(1, unitCost);
        query.setParameter(2, nbOfPage);
        List<Book> books = query.getResultList();

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println(book);
        }
    }

    public void queryBooksName(Float unitCost, Integer nbOfPage) {

        TypedQuery<Book> query = em.createNamedQuery("ParamNameBooks", Book.class);
        query.setParameter("cost", unitCost);
        query.setParameter("pages", nbOfPage);
        List<Book> books = query.getResultList();

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println(book);
        }
    }

    public void queryBooksDate(Date publicationDate) {

        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.publicationDate < :pubDate", Book.class);
        query.setParameter("pubDate", publicationDate, TemporalType.TIMESTAMP);
        List<Book> books = query.getResultList();

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println(book);
        }
    }

    public void paginateBooks(Float unitCost, Integer nbOfPage) {

        TypedQuery<Book> query = em.createNamedQuery("ParamNameBooks", Book.class);
        query.setParameter("cost", unitCost);
        query.setParameter("pages", nbOfPage);
        query.setMaxResults(2);
        List<Book> books = query.getResultList();

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println(book);
        }
    }
}