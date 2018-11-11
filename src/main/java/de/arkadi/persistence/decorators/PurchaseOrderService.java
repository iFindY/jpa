package de.arkadi.persistence.decorators;


import de.arkadi.persistence.model.Item;
import de.arkadi.persistence.model.PurchaseOrder;
import de.arkadi.persistence.qualifier.Vat;
import de.arkadi.persistence.util.NumberHelper;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

public class PurchaseOrderService implements Computable {

    // ======================================
    // =             Attributes             =
    // ======================================

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

    @Override
    public PurchaseOrder compute(List<Item> items) {
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

        logger.info("Purchase Order: " + po);

        return po;
    }

    public PurchaseOrder createPurchaseOrder(List<Item> items) {
        return compute(items);
    }
}