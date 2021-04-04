package com.example.foodinventorydemo.ui.scanner;

import java.io.Serializable;

public abstract class ScannerCaller implements Serializable {
    protected void onScanError(Exception e) {

    };
    protected void onNewScanData(String data) {

    };
}
