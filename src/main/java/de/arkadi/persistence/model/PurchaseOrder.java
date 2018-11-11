package de.arkadi.persistence.model;

public class PurchaseOrder {

    // ======================================
    // =             Attributes             =
    // ======================================

    private Float subtotal;
    private Float total;
    private Float totalAfterDiscount;

    // ======================================
    // =         Getters & setters          =
    // ======================================

    public Float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getTotalAfterDiscount() {
        return totalAfterDiscount;
    }

    public void setTotalAfterDiscount(Float totalAfterDiscount) {
        this.totalAfterDiscount = totalAfterDiscount;
    }

    // ======================================
    // =   Methods hash, equals, toString   =
    // ======================================

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PurchaseOrder{");
        sb.append("subtotal=").append(subtotal);
        sb.append(", total=").append(total);
        sb.append(", totalAfterDiscount=").append(totalAfterDiscount);
        sb.append('}');
        return sb.toString();
    }
}