package de.arkadi.persistence.model;

import de.arkadi.persistence.qualifier.Legacy;

import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;


public class Order {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Pattern.List({
            @Pattern(regexp = "[C,D,M][A-Z][0-9]*"),
            @Pattern(regexp = "[S,Y][1-9]*", groups = Legacy.class)
    })
    private String orderId;
    private Date creationDate;
    private Double totalAmount;
    private Date paymentDate;
    private Date deliveryDate;
    private List<OrderLine> orderLines;

    // ======================================
    // =            Constructors            =
    // ======================================

    public Order() {
    }

    public Order(String orderId, Double totalAmount) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }

    // ======================================
    // =          Getters & Setters         =
    // ======================================

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}