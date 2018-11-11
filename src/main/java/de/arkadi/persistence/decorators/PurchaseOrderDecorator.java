package de.arkadi.persistence.decorators;


import de.arkadi.persistence.model.Item;
import de.arkadi.persistence.model.PurchaseOrder;
import de.arkadi.persistence.qualifier.Discount;
import de.arkadi.persistence.util.NumberHelper;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@Decorator
public abstract class PurchaseOrderDecorator implements Computable {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    @Discount
    private Float discountRate;

    @Inject
    @Delegate
    private Computable purchaseOrderService;

    @Inject
    private NumberHelper helper;

    @Inject
    private Logger logger;

    // ======================================
    // =          Business methods          =
    // ======================================

    @Override
    public PurchaseOrder compute(List<Item> items) {

        PurchaseOrder po = purchaseOrderService.compute(items);

        po.setTotalAfterDiscount(helper.round(po.getTotal() - po.getTotal() * (discountRate / 100)));

        logger.info("Decorated Purchase Order: " + po);

        return po;
    }
}