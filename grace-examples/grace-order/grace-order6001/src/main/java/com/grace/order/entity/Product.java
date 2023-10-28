package com.grace.order.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("product")
public class Product {

    private String id;

    private String productName;

    private Double price;

    public Product() {
    }

    public Product(String id, String productName, Double price) {
        this.id = id;
        this.productName = productName;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
