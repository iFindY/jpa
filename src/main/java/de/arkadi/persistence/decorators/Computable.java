package de.arkadi.persistence.decorators;

import de.arkadi.persistence.model.Item;
import de.arkadi.persistence.model.PurchaseOrder;

import java.util.List;

public interface Computable {

    // ======================================
    // =          Business methods          =
    // ======================================

    PurchaseOrder compute(List<Item> items);

    PurchaseOrder createPurchaseOrder(List<Item> items);
}