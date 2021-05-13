package com.example.foodinventorydemo.model;

public class ResourceResponseHandler<T> {
    public void handleError(Exception e) {};
    public void handleRes(T res) {};
}
