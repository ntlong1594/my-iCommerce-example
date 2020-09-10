package com.icommerce.developer.product.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;


public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;

    private String userId;

    private Map<String, CartDetail> selectedProducts;

    private Integer totalAmount;

    private LocalDate createdDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, CartDetail> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(Map<String, CartDetail> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Cart{" +
            "id=" + id +
            ", userId='" + userId + '\'' +
            ", selectedProducts=" + selectedProducts +
            ", totalAmount=" + totalAmount +
            ", createdDate=" + createdDate +
            '}';
    }
}
