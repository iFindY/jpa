package de.arkadi.persistence.service;


import de.arkadi.persistence.model.Author;

import javax.persistence.EntityManager;

public class AuthorService {

    // ======================================
    // =             Attributes             =
    // ======================================

    private EntityManager em;

    // ======================================
    // =            Constructors            =
    // ======================================

    public AuthorService(EntityManager em) {
        this.em = em;
    }

    // ======================================
    // =           Public Methods           =
    // ======================================

    public Long createAuthor(Author author) {
        em.persist(author);
        return author.getId();
    }

    public void removeAuthor(Author author) {
        em.remove(em.merge(author));
    }

    public Author findAuthor(Long id) {
        return em.find(Author.class, id);
    }
}