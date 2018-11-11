package de.arkadi.persistence.util;

import de.arkadi.persistence.qualifier.LifeCheck;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Random;

@LifeCheck
@ApplicationScoped
public class IsbnGenerator {

    // ======================================
    // =             Attributes             =
    // ======================================

    private int postfix;

    // ======================================
    // =          Lifecycle methods         =
    // ======================================

    @PostConstruct
    private void init() {
        postfix = Math.abs(new Random().nextInt());
    }

    // ======================================
    // =          Business methods          =
    // ======================================

    public String generateNumber() {
        return "13-84356-" + postfix++;
    }
}
