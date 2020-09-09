package com.icommerce.developer.product.service.dto;

public class SearchCriteria {
    String title;
    String brand;
    Integer priceGreaterThan;
    Integer priceLesserThan;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getPriceGreaterThan() {
        return priceGreaterThan;
    }

    public void setPriceGreaterThan(Integer priceGreaterThan) {
        this.priceGreaterThan = priceGreaterThan;
    }

    public Integer getPriceLesserThan() {
        return priceLesserThan;
    }

    public void setPriceLesserThan(Integer priceLesserThan) {
        this.priceLesserThan = priceLesserThan;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "title='" + title + '\'' +
                ", brand='" + brand + '\'' +
                ", priceGreaterThan=" + priceGreaterThan +
                ", priceLesserThan=" + priceLesserThan +
                '}';
    }
}
