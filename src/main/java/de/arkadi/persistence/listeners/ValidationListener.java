package de.arkadi.persistence.listeners;


import de.arkadi.persistence.model.Artist;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


public class ValidationListener {

    // ======================================
    // =          Lifecycle Methods         =
    // ======================================

    @PrePersist
    @PreUpdate
    private void validate(Artist artist) {
        System.out.println(".DataValidationListener validate()");
        if (artist.getFirstName() == null || "".equals(artist.getFirstName()))
            throw new IllegalArgumentException("Invalid first name");
        if (artist.getLastName() == null || "".equals(artist.getLastName()))
            throw new IllegalArgumentException("Invalid last name");
    }
}