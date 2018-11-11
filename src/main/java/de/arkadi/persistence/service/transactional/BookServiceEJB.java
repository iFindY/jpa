package de.arkadi.persistence.service.transactional;

import de.arkadi.persistence.model.Book;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Stateless turn this POJO into EJB and automatic bind Transactional EJB
 */
@Stateless
public class BookServiceEJB {
    @Inject
    private EntityManager em;

    public Book createBook(Book book) {
        em.persist(book);
        return book;
    }
}
