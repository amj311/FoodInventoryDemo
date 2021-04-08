package com.example.foodinventorydemo.ui.scanner;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

public abstract class ScannerCaller implements Serializable, Parcelable {
    protected void onScanError(Exception e) {

    };
    protected void onNewScanData(String data) {
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
