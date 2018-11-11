package de.arkadi.persistence.listeners;


import javax.persistence.*;

/**
 * default listener, will listen on all entities
 */
public class LifecyleListener {

    // ======================================
    // =          Lifecycle Methods         =
    // ======================================

    @PrePersist
    void prePersist(Object object) {
        System.out.println(".LifecyleListener       prePersist()");
    }

    @PostPersist
    void postPersist(Object object) {
        System.out.println(".LifecyleListener       postPersist()");
    }

    @PreUpdate
    void preUpdate(Object object) {
        System.out.println(".LifecyleListener       preUpdate()");
    }

    @PostUpdate
    void postUpdate(Object object) {
        System.out.println(".LifecyleListener       postUpdate()");
    }

    @PreRemove
    void preRemove(Object object) {
        System.out.println(".LifecyleListener       preRemove()");
    }

    @PostRemove
    void postRemove(Object object) {
        System.out.println(".LifecyleListener       postRemove()");
    }

    @PostLoad
    void postLoad(Object object) {
        System.out.println(".LifecyleListener       postLoad()");
    }
}
