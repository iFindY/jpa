package de.arkadi.persistence.producers;

import de.arkadi.persistence.qualifier.Discount;
import de.arkadi.persistence.qualifier.EightDigits;
import de.arkadi.persistence.qualifier.ThirteenDigits;
import de.arkadi.persistence.qualifier.Vat;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.inject.Named;


public class NumberProducer {

    // ======================================
    // =              Producers             =
    // ======================================

    @Produces
    @ThirteenDigits
    private String prefix1 = "13-";

    @Produces
    @ThirteenDigits
    private int postfix1 = 9;

    @Produces
    @EightDigits
    private String prefix2 = "8-";

    @Produces
    @EightDigits
    private int postfix2 = 4;

    @Produces
    @ThirteenDigits
    @EightDigits
    @Alternative
    private String prefix3 = "Mock-";

    @Produces
    @ThirteenDigits
    @EightDigits
    @Alternative
    private int postfix3 = 0;

    @Produces
    @Vat
    @Named("vatRate")
    private Float vatRate = 5.5F;

    @Produces
    @Vat
    @Alternative
    @Named("vatRate")
    private Float altVatRate = 7.8F;

    @Produces
    @Discount
    @Named("discountRate")
    private Float discountRate = 2.25f;

    @Produces
    @Discount
    @Alternative
    @Named("discountRate")
    private Float discountRateAlt = 12.5f;
}