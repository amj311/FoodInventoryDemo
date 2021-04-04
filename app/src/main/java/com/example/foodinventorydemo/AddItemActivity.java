package com.example.foodinventorydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.foodinventorydemo.ui.scanner.ScannerCaller;
import com.example.foodinventorydemo.ui.scanner.ScannerFragment;
import com.google.android.material.button.MaterialButton;

import java.util.Arrays;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {
    ConstraintLayout fragWrap;
    FrameLayout addFrag;
    MaterialButton scanBtn;
    ImageView closeBtn;
    List<View> items;
    int numItemsShown = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        scanBtn = findViewById(R.id.scanButton);
        closeBtn = findViewById(R.id.closeBtn);
        fragWrap = findViewById(R.id.addScanFragWrapper);
        addFrag = findViewById(R.id.addScanFrag);
        items = Arrays.asList(findViewById(R.id.fakeItem1), findViewById(R.id.fakeItem2),
                findViewById(R.id.fakeItem3), findViewById(R.id.fakeItem4), findViewById(R.id.fakeItem5),
                findViewById(R.id.fakeItem6), findViewById(R.id.fakeItem7));

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment f = ScannerFragment.newInstance(new AddScanCaller());
                getSupportFragmentManager().beginTransaction().replace(R.id.addScanFrag, f).commit();
                fragWrap.setVisibility(View.VISIBLE);
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager()
                        .findFragmentById(R.id.addScanFrag)).commit();
                fragWrap.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.addItemBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revealItem();
            }
        });
    }

    private void revealItem() {
        if (numItemsShown < items.size()) {
            items.get(numItemsShown).setVisibility(View.VISIBLE);
            numItemsShown++;
        };
    }

    class AddScanCaller extends ScannerCaller {
        @Override
        protected void onNewScanData(String data) {
            revealItem();
        }
    }
}