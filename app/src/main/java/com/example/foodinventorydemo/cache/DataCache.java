package com.example.foodinventorydemo.cache;

import com.example.foodinventorydemo.model.ProductUnitData;

import java.util.ArrayList;
import java.util.List;

public class DataCache {
    private static DataCache single_instance = null;

    public List<ProductUnitData> foodItemList = new ArrayList<>();
    // private constructor restricted to this class itself
    private DataCache() {}

    // static method to create instance of Singleton class
    public static DataCache getInstance()
    {
        if (single_instance == null)
            single_instance = new DataCache();
        return single_instance;
    }
}
