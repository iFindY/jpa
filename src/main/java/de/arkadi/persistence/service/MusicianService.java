package de.arkadi.persistence.service;

import de.arkadi.persistence.model.Musician;

import javax.persistence.EntityManager;

public class MusicianService {

    // ======================================
    // =             Attributes             =
    // ======================================

    private EntityManager em;

    // ======================================
    // =            Constructors            =
    // ======================================

    public MusicianService(EntityManager em) {
        this.em = em;
    }

    // ======================================
    // =           Public Methods           =
    // ======================================

    public Long createMusician(Musician musician) {
        em.persist(musician);
        return musician.getId();
    }

    public void removeMusician(Musician musician) {
        em.remove(em.merge(musician));
    }

    public Musician findMusician(Long id) {
        return em.find(Musician.class, id);
    }

}
