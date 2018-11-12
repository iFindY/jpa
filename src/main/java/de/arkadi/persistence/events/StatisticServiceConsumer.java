package de.arkadi.persistence.events;

import org.apache.logging.log4j.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class StatisticServiceConsumer {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private Logger logger;

    // ======================================
    // =          Business methods          =
    // ======================================

    public void itemsSold(@Observes PurchaseOrder po) {
        logger.info("%%% Purchase Order of ${}", po.getTotal());
    }
}