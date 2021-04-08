package com.example.foodinventorydemo.singleton;

public class DataCache {
    private static DataCache single_instance = null;

    // variable of type String
    public String s;

    // private constructor restricted to this class itself
    private DataCache()
    {

    }

    // static method to create instance of Singleton class
    public static DataCache getInstance()
    {
        if (single_instance == null)
            single_instance = new DataCache();

        return single_instance;
    }
}
