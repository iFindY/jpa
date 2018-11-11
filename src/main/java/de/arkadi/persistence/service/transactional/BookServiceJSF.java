package de.arkadi.persistence.service.transactional;

import de.arkadi.persistence.model.Book;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * @Named turn this POJO into JSF Backing Bean
 */
@Transactional
@Named
public class BookServiceJSF {
    @Inject
    private EntityManager em;

    public Book createBook(Book book) {
        em.persist(book);
        return book;
    }
}
