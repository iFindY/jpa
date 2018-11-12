package de.arkadi.persistence.events;


import de.arkadi.persistence.model.PurchaseOrder;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.logging.Logger;

public class ShippingServiceConsumer {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private Logger logger;

    // ======================================
    // =          Business methods          =
    // ======================================

    /**
     * called automatic
     */
    public void shipItems(@Observes PurchaseOrder po) {
        logger.info("### Purchase Order of ${}"+ po.getTotal());
    }
}