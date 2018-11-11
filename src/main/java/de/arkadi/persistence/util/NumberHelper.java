package de.arkadi.persistence.util;

public class NumberHelper {

    // ======================================
    // =          Business methods          =
    // ======================================

    public Float round(Float numb) {
        return (float) (Math.round(numb * 100) / 100.0);
    }
}