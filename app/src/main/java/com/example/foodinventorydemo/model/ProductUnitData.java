package com.example.foodinventorydemo.model;

import java.util.List;
import java.util.Objects;

public class ProductUnitData {
    private String code;
    private String name;
    private String brand;
    private String description;
    private String category;
    private String weight;
    private List<String> imageUrls;

    public ProductUnitData(String code, String name, String brand, String description, String category, String weight, List<String> imageUrls) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.category = category;
        this.weight = weight;
        this.imageUrls = imageUrls;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductUnitData unitData = (ProductUnitData) o;
        if (getCode()!=null && !getCode().equals(unitData.getCode())) return false;
        if (getName()!=null && !getName().equals(unitData.getName())) return false;
        if (getBrand()!=null && !getBrand().equals(unitData.getBrand())) return false;
        if (getDescription()!=null && !getDescription().equals(unitData.getDescription())) return false;
        if (getCategory()!=null && !getCategory().equals(unitData.getCategory())) return false;
        if (getWeight()!=null && !getWeight().equals(unitData.getWeight())) return false;

        return true;
    }

}
