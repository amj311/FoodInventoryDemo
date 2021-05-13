package com.example.foodinventorydemo.model;

import com.example.foodinventorydemo.utils.DateUtils;

import java.util.Calendar;

public class ProductBundleTransaction {
    public final long timestamp;
    private String mode;
    private ProductExpBundle bundleData;
    private int amount;

    public ProductBundleTransaction(ProductExpBundle bundle, int amount) {
        this.amount = amount;
        this.bundleData = bundle;
        timestamp = DateUtils.getTimeInMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int modifyAmountBy(int delta) {
        return this.amount += delta;
    }

    public ProductExpBundle getBundle() {
        return bundleData;
    }

    public void setBundleData(ProductExpBundle bundle) {
        this.bundleData = bundle;
    }
}
