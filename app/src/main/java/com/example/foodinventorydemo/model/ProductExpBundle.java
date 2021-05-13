package com.example.foodinventorydemo.model;

public class ProductExpBundle {
    private ProductUnitData data;
    private String expiration;
    private int qty;

    public ProductExpBundle(ProductUnitData data, String expiration, int qty) {
        this.data = data;
        this.expiration = expiration;
        this.qty = qty;
    }

    public ProductExpBundle(ProductUnitData data, String expiration) {
        this.data = data;
        this.expiration = expiration;
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

    public String getExpiration() {
        return expiration;
    }

    public boolean isSameAs(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductExpBundle that = (ProductExpBundle) o;
        return getData().equals(that.getData()) &&
                getExpiration().equals(that.getExpiration());
    }

}
