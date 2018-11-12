package de.arkadi.persistence.events;


import de.arkadi.persistence.model.Item;
import de.arkadi.persistence.model.PurchaseOrder;
import de.arkadi.persistence.qualifier.Vat;
import de.arkadi.persistence.util.NumberHelper;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;


public class EventProducer {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private Event<PurchaseOrder> purchaseOrderEvent;

    @Inject
    @Vat
    private Float vatRate;

    @Inject
    private NumberHelper helper;

    @Inject
    private Logger logger;

    // ======================================
    // =          Business methods          =
    // ======================================

    /**
     * firing event here
     * is a synchronous implementation. after firing this bean pauses and passes the event to any register observer
     */
    public PurchaseOrder create(List<Item> items) {
        PurchaseOrder po = new PurchaseOrder();
        Float subtotal = 0f;

        // Sum up the quantities
        for (Item cartItem : items) {
            subtotal += (cartItem.getSubTotal());
        }
        Float vat = subtotal * (vatRate / 100);
        Float total = subtotal + vat;

        po.setSubtotal(helper.round(subtotal));
        po.setTotal(helper.round(total));
        po.setTotalAfterDiscount(helper.round(total));

        logger.info(">>> Event fired");
        purchaseOrderEvent.fire(po);

        return po;
    }
}