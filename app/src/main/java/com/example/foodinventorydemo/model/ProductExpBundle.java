package com.example.foodinventorydemo.model;

import java.util.List;

public class ProductUnitBundle {
    private ProductUnitData data;
    private int qty;

    public ProductUnitBundle(ProductUnitData data, int qty) {
        this.data = data;
        this.qty = qty;
    }

    public ProductUnitBundle(ProductUnitData data) {
        this.data = data;
        this.qty = 0;
    }

    public ProductUnitData getData() {
        return data;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) throws Exception {
        if (qty < 0) throw new Exception("Bundle size cannot be less than 0!");
        this.qty = qty;
    }

    public void modifyQtyBy(int delta) throws Exception {
        this.setQty(Math.max(0, qty+delta));
    }
}
