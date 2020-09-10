package com.icommerce.developer.product.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Order.
 */
@Document(collection = "order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("payment_type")
    private String paymentType;

    @NotNull
    @Field("created_by")
    private String createdBy;

    @NotNull
    @Field("cart")
    private Cart cart;

    @NotNull
    @Field("delivery_date")
    private LocalDate deliveryDate;

    @NotNull
    @Field("created_date")
    private LocalDate createdDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public Order paymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Order createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id='" + id + '\'' +
            ", paymentType='" + paymentType + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", cart=" + cart +
            ", deliveryDate=" + deliveryDate +
            ", createdDate=" + createdDate +
            '}';
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
