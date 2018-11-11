package de.arkadi.persistence.service.transactional;

import de.arkadi.persistence.model.Book;
import sun.net.httpserver.HttpServerImpl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.transaction.Transactional;

/**
 * WebServlet turn this POJO into Servlet
 */
@Transactional
@WebServlet(urlPatterns = {"/TxServlet"})
public class BookServiceServlet extends HttpServlet {
    @Inject
    private EntityManager em;

    public Book createBook(Book book) {
        em.persist(book);
        return book;
    }
}
