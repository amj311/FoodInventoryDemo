package com.example.foodinventorydemo.singleton;

public class FoodItem {
    private String name;
    private String category;
    private String expirationDate;

    public FoodItem(String name, String category, String expirationDate) {
        this.name = name;
        this.category = category;
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
