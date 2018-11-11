package de.arkadi.persistence.service;


import de.arkadi.persistence.model.Book;
import de.arkadi.persistence.util.IsbnGenerator;
import de.arkadi.persistence.qualifier.LifeCheck;
import de.arkadi.persistence.qualifier.Loggable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@LifeCheck
@Loggable
@ApplicationScoped
public class BookService {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private IsbnGenerator generator;

    // ======================================
    // =          Business methods          =
    // ======================================

    public Book createBook(String title, Float price) {
        return new Book(title, price, generator.generateNumber());
    }

    public Book raisePrice(Book book) throws InterruptedException {
        Thread.sleep(100);
        book.setPrice(book.getPrice() * 2.5F);
        return book;
    }
}
