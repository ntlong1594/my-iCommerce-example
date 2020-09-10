package com.icommerce.developer.product.domain;

import java.io.Serializable;


public class CartDetail implements Serializable {
    private Integer pricePerUnit;
    private Integer quantity;

    public Integer getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Integer pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartDetail{" +
            "pricePerUnit=" + pricePerUnit +
            ", quantity=" + quantity +
            '}';
    }
}
