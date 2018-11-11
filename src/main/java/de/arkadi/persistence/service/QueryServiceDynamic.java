package de.arkadi.persistence.service;


import de.arkadi.persistence.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class QueryServiceDynamic {

    // ======================================
    // =             Attributes             =
    // ======================================

    private EntityManager em;

    // ======================================
    // =            Constructors            =
    // ======================================

    public QueryServiceDynamic(EntityManager em) {
        this.em = em;
    }

    // ======================================
    // =           Public Methods           =
    // ======================================

    /**
     * dynamic return type has to be casted
     */
    public void queryBooks() {

        Query query = em.createQuery("SELECT b FROM Book b WHERE b.unitCost > 29 AND b.nbOfPage < 700");
        List books = query.getResultList();

        for (int i = 0; i < books.size(); i++) {
            Book book = (Book) books.get(i);
            System.out.println(book);
        }
    }

    /**
     * the return type is set by the typed query parameter `Book.class`
     */
    public void queryBooksTyped() {

        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.unitCost > 29 AND b.nbOfPage < 700", Book.class);
        List<Book> books = query.getResultList();

        for (Book book : books) {
            System.out.println(book);
        }
    }

    /**
     * query with parameter positions
     *
     * @param unitCost
     * @param nbOfPage
     */
    public void queryBooksPosition(Float unitCost, Integer nbOfPage) {

        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.unitCost > ?1 AND b.nbOfPage < ?2", Book.class);
        query.setParameter(1, unitCost);
        query.setParameter(2, nbOfPage);
        List<Book> books = query.getResultList();

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println(book);
        }
    }

    /**
     * query with  named parameters
     *
     * @param unitCost
     * @param nbOfPage
     */
    public void queryBooksName(Float unitCost, Integer nbOfPage) {

        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.unitCost > :cost AND b.nbOfPage < :pages", Book.class);
        query.setParameter("cost", unitCost);
        query.setParameter("pages", nbOfPage);
        List<Book> books = query.getResultList();

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println(book);
        }
    }

    /**
     * date as input parameter
     *
     * @param unitCost
     * @param nbOfPage
     * @param publicationDate
     */
    public void queryFull(Float unitCost, Integer nbOfPage, Date publicationDate) {

        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.unitCost > :cost AND b.nbOfPage < :pages OR b.publicationDate < :pubDate", Book.class);
        query.setParameter("cost", unitCost);
        query.setParameter("pages", nbOfPage);
        query.setParameter("pubDate", publicationDate, TemporalType.TIMESTAMP);
        List<Book> books = query.getResultList();

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println(book);
        }
    }

    /**
     * dynamic query depending on the runtime. will be compiled every time method is called
     *
     * @param unitCost
     * @param nbOfPage
     * @param publicationDate
     */
    public void queryDynamic(Float unitCost, Integer nbOfPage, Date publicationDate) {
        boolean hasPages = true;
        boolean hasDate = true;

        String statement = "SELECT b FROM Book b WHERE b.unitCost > :cost ";
        if (hasPages)
            statement += "AND b.nbOfPage < :pages ";
        if (hasDate)
            statement += "OR b.publicationDate < :pubDate";

        TypedQuery<Book> query = em.createQuery(statement, Book.class);
        query.setParameter("cost", unitCost);
        query.setParameter("pages", nbOfPage);
        query.setParameter("pubDate", publicationDate, TemporalType.TIMESTAMP);
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

        TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.unitCost > :cost AND b.nbOfPage < :pages", Book.class);
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