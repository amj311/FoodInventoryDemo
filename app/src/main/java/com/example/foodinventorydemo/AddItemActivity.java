package com.example.foodinventorydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodinventorydemo.model.ProductUnitData;
import com.example.foodinventorydemo.singleton.DataCache;
import com.example.foodinventorydemo.ui.scanner.ScannerCaller;
import com.example.foodinventorydemo.ui.scanner.ScannerFragment;
import com.example.foodinventorydemo.utils.ResourceResponseHandler;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {
    private final String MODE_IN = "IN";
    private final String MODE_OUT = "OUT";
    private String mode;

    private final int INPUT_SCAN = 0;
    private final int INPUT_FORM = 1;
    private int input;

    TextView inLabel;
    TextView outLabel;
    LinearLayoutCompat inForm;
    LinearLayoutCompat outForm;
    SwitchMaterial modeToggle;

    ConstraintLayout fragWrap;
    FrameLayout addFrag;
    MaterialButton scanBtn;
    ImageView closeBtn;
    EditText nameField;
    EditText categoryField;
    EditText expireField;
    EditText qtyField;
    ProgressBar searchingSpinner;
    RecyclerView itemsRV;
    ItemListAdapter adapter;
    List<ProductUnitData> items = new ArrayList<>();
    MaterialDatePicker datePicker;
    int numItemsShown = 0;
    ScannerFragment scanner;
    boolean searching = false;

    ResourceResponseHandler<ProductUnitData> dataResHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dataResHandler = new ProductResultHandler();

        modeToggle = findViewById(R.id.inOutToggle);
        inLabel = findViewById(R.id.inLabel);
        outLabel = findViewById(R.id.outLabel);
        inForm = findViewById(R.id.addForm);
        outForm = findViewById(R.id.removeForm);

        scanBtn = findViewById(R.id.scanButton);
        closeBtn = findViewById(R.id.closeBtn);
        fragWrap = findViewById(R.id.addScanFragWrapper);
        addFrag = findViewById(R.id.addScanFrag);
        searchingSpinner = findViewById(R.id.searchingSpinner);
        nameField = findViewById(R.id.nameField);
        categoryField = findViewById(R.id.categoryField);
        expireField = findViewById(R.id.expField);
        qtyField = findViewById(R.id.qtyField);

        datePicker = MaterialDatePicker.Builder.datePicker().build();
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                expireField.setText(datePicker.getHeaderText());
            }
        });


        switchToIn();
        input = INPUT_SCAN;
        if (mode.equals(MODE_IN)) outLabel.setTextColor(Color.WHITE);
        else inLabel.setTextColor(Color.WHITE);
        scanner = ScannerFragment.newInstance(new AddScanCaller());
        getSupportFragmentManager().beginTransaction().replace(R.id.addScanFrag, scanner).commit();
        fragWrap.setVisibility(View.VISIBLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = INPUT_SCAN;
                if (mode.equals(MODE_IN)) outLabel.setTextColor(Color.WHITE);
                else inLabel.setTextColor(Color.WHITE);
                scanner = ScannerFragment.newInstance(new AddScanCaller());
                getSupportFragmentManager().beginTransaction().replace(R.id.addScanFrag, scanner).commit();
                fragWrap.setVisibility(View.VISIBLE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = INPUT_FORM;
                if (mode.equals(MODE_IN)) outLabel.setTextColor(Color.BLACK);
                else inLabel.setTextColor(Color.BLACK);
                getSupportFragmentManager().beginTransaction().remove(scanner).commit();
                fragWrap.setVisibility(View.GONE);
                scanner = null;
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });

        modeToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) switchToOut();
                else switchToIn();
            }
        });

        findViewById(R.id.addItemBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemFromForm();
            }
        });
        expireField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    datePicker.show(getSupportFragmentManager(),"ExpirationDatePicker");
                    expireField.clearFocus();
                }
            }

        });

        adapter = new ItemListAdapter(items);
        itemsRV = findViewById(R.id.itemsList);
        itemsRV.setLayoutManager(new LinearLayoutManager(this));
        itemsRV.setAdapter(adapter);
    }


    class AddScanCaller extends ScannerCaller {
        Creator<AddScanCaller> CREATOR = new Creator<AddScanCaller>() {
            @Override
            public AddScanCaller createFromParcel(Parcel parcel) {
                return new AddScanCaller();
            }

            @Override
            public AddScanCaller[] newArray(int i) {
                return new AddScanCaller[0];
            }
        };

        @Override
        protected void onNewScanData(String data) {
            if (searching) return;
            induceSearchingState();
            LookupCodeService.fetchProductData(data, dataResHandler);
        }
    }

    class ProductResultHandler extends ResourceResponseHandler<ProductUnitData> {
        @Override
        public void handleError(Exception e) {
            Toast.makeText(AddItemActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            endSearchingState();
        }

        public void handleError(LookupCodeService.NotFoundException e) {
            Toast.makeText(AddItemActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            endSearchingState();
        }

        @Override
        public void handleRes(ProductUnitData res) {
            endSearchingState();
            addItem(res);
        }
    }




    private void addItemFromForm() {
        String name = nameField.getText().toString();
        String expiration = expireField.getText().toString();
        String category = categoryField.getText().toString();
        int qty = 1;
        try{
            qty = Integer.parseInt(qtyField.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        addItem(new ProductUnitData(null, name,null, null, expiration, category,null,null, qty));
    }

    private void addItem(ProductUnitData item) {
        items.add(item);
        DataCache.getInstance().foodItemList.add(item);
        adapter.notifyItemChanged(items.size()-1,item);
        itemsRV.smoothScrollToPosition(items.size()-1);
    }




    private void endSearchingState() {
        searching = false;
        scanner.allowScan();
        searchingSpinner.setVisibility(View.GONE);
    }

    private void induceSearchingState() {
        searching = true;
        scanner.disallowScan();
        searchingSpinner.setVisibility(View.VISIBLE);
    }



    private void switchToIn() {
        mode = MODE_IN;
        inLabel.setTextColor(getResources().getColor(R.color.in_green));
        outLabel.setTextColor(input == INPUT_FORM? Color.BLACK : Color.WHITE);
        inForm.setVisibility(View.VISIBLE);
        outForm.setVisibility(View.GONE);
    }
    private void switchToOut() {
        mode = MODE_OUT;
        outLabel.setTextColor(getResources().getColor(R.color.out_red));
        inLabel.setTextColor(input == INPUT_FORM? Color.BLACK : Color.WHITE);
        outForm.setVisibility(View.VISIBLE);
        inForm.setVisibility(View.GONE);
    }



    class ItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<ProductUnitData> list;

        public ItemListAdapter(List<ProductUnitData> items) {
            list = items;
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            private final TextView nameText;
            private final TextView expireText;
            private final TextView qtyText;
            private final TextView modeText;
            private final ImageView imageView;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                modeText = itemView.findViewById(R.id.modeText);
                nameText = itemView.findViewById(R.id.productName);
                expireText = itemView.findViewById(R.id.productExpire);
                qtyText = itemView.findViewById(R.id.productQty);
                imageView = itemView.findViewById(R.id.productImage);

            }

            public void setItem(ProductUnitData item) {
                modeText.setText(mode);
                modeText.setTextColor(mode.equals(MODE_IN) ? getResources().getColor(R.color.in_green) : getResources().getColor(R.color.out_red));
                nameText.setText(item.getName());
                expireText.setText(item.getExpiration()!=null ? item.getExpiration() : "Expires: 5/12/2024");
                qtyText.setText(String.format("Qty: %d", item.getQty()!=0 ? item.getQty() : 1));
                attemptSetImage(imageView, item,null);
            }

            private void attemptSetImage(final ImageView imageView, final ProductUnitData item, final String lastUrl) {
                final String url = item.getNextUrl(lastUrl);
                if (url == null) {
                    imageView.setImageResource(R.drawable.common_google_signin_btn_icon_disabled);
                    return;
                }
                Picasso.get().load(url).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("PICASSO_ERROR","Failed to load image: "+lastUrl);
                        attemptSetImage(imageView, item, url);
                    }
                });
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item_view, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;
            itemHolder.setItem(list.get(position));
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

    }
}