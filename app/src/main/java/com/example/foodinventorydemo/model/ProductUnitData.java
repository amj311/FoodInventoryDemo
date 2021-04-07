package com.example.foodinventorydemo.model;

import java.util.List;

public class ProductUnitData {
    private String code;
    private String name;
    private String brand;
    private String description;
    private String expiration;
    private String category;
    private String weight;
    private List<String> imageUrls;
    private int qty;

    public ProductUnitData(String code, String name, String brand, String description, String expiration, String category, String weight, List<String> imageUrls, int qty) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.expiration = expiration;
        this.category = category;
        this.weight = weight;
        this.imageUrls = imageUrls;
        this.qty = qty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public String getNextUrl(String lastUrl) {
        if (imageUrls==null || imageUrls.size()==0) return null;
        if (lastUrl == null) return imageUrls.get(0);
        if (!imageUrls.contains(lastUrl)) return null;
        int lastIdx = imageUrls.indexOf(lastUrl);
        if (lastIdx == imageUrls.size()-1) return null;
        return imageUrls.get(lastIdx+1);
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}
