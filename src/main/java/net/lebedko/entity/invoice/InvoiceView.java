package net.lebedko.entity.invoice;

import net.lebedko.entity.Validatable;
import net.lebedko.entity.general.Price;

import java.time.LocalDateTime;

/**
 * alexandr.lebedko : 01.05.2017.
 */

public class InvoiceView extends Invoice{
    private int id;
    private boolean paid;
    private int orderId;
    private Price price;
    private LocalDateTime creationDateTime;

    public InvoiceView(int id, int orderId, Price price, boolean paid, LocalDateTime creationDate) {
        this.id = id;
        this.orderId = orderId;
        this.price = price;
        this.paid = paid;
        this.creationDateTime = creationDate;
    }


    public int getId() {
        return id;
    }

    public boolean isPaid() {
        return paid;
    }

    public int getOrderId() {
        return orderId;
    }


    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean isValid() {
        return id != 0 &&
                orderId != 0 &&
                price.isValid() &&
                creationDateTime.isBefore(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "InvoiceView{" +
                "id=" + id +
                ", paid=" + paid +
                ", orderId=" + orderId +
                ", creationDateTime=" + creationDateTime +
                '}';
    }

}
