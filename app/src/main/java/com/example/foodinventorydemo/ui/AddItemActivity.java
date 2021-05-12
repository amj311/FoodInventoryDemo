package com.example.foodinventorydemo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodinventorydemo.service.DummyLookupCodeService;
import com.example.foodinventorydemo.service.LookupCodeService;
import com.example.foodinventorydemo.R;
import com.example.foodinventorydemo.model.ProductUnitData;
import com.example.foodinventorydemo.cache.DataCache;
import com.example.foodinventorydemo.ui.scanner.ScannerCaller;
import com.example.foodinventorydemo.ui.scanner.ScannerFragment;
import com.example.foodinventorydemo.utils.ResourceResponseHandler;
import com.example.foodinventorydemo.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
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

    SwitchMaterial demoSwitch;

    TextView modeMsg;
    View scanModeBar;
    ImageButton closeBtn;
    ObjectAnimator modeMsgAnimator;
    LinearLayoutCompat inForm;
    LinearLayoutCompat outForm;

    ConstraintLayout fragWrap;
    FrameLayout addFrag;
    MaterialButton scanBtn;
    ImageView startManualBtn;
    EditText nameField;
    EditText categoryField;
    EditText expireField;
    EditText qtyField;
    ProgressBar scannerSpinner;
    ProgressBar newTransactionSpinner;

    BottomSheetBehavior bottomSheetBehavior;
    LinearLayout bottomSheet;
    ObjectAnimator sheetAnimator;
    TextView addTallyText;
    TextView removeTallyText;
    int addTally = 0;
    int removeTally = 0;
    RecyclerView itemsRV;
    ItemListAdapter adapter;
    List<ProductUnitData> items = new ArrayList<>();

    MaterialDatePicker datePicker;
    int numItemsShown = 0;
    ScannerFragment scanner;
    boolean searching = false;

    ResourceResponseHandler<ProductUnitData> dataResHandler;

    boolean dev = true;
    LookupCodeService lookupCodeService = dev? new DummyLookupCodeService() : new LookupCodeService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove_item);

        getSupportActionBar().hide();
        dataResHandler = new ProductResultHandler();

        updateDevStatus(dev);

        demoSwitch = findViewById(R.id.demoToggle);
        demoSwitch.setChecked(dev);
        demoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                updateDevStatus(b);
            }
        });

        modeMsg = findViewById(R.id.scanModeText);
        scanModeBar = findViewById(R.id.scan_app_bar);
        closeBtn = findViewById(R.id.closeBtn);

        inForm = findViewById(R.id.addForm);
        outForm = findViewById(R.id.removeForm);

        scanBtn = findViewById(R.id.scanButton);
        startManualBtn = findViewById(R.id.startManualBtn);
        fragWrap = findViewById(R.id.addScanFragWrapper);
        addFrag = findViewById(R.id.addScanFrag);
        scannerSpinner = findViewById(R.id.searchingSpinner);
        newTransactionSpinner = findViewById(R.id.newTransactionSpinner);
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

        addTallyText = findViewById(R.id.addTallyText);
        removeTallyText = findViewById(R.id.removeTallyText);
        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        switchToIn();
        input = INPUT_SCAN;
        scanner = ScannerFragment.newInstance(new AddScanCaller());
        getSupportFragmentManager().beginTransaction().replace(R.id.addScanFrag, scanner).commit();
        fragWrap.setVisibility(View.VISIBLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = INPUT_SCAN;
                scanner = ScannerFragment.newInstance(new AddScanCaller());
                getSupportFragmentManager().beginTransaction().replace(R.id.addScanFrag, scanner).commit();
                fragWrap.setVisibility(View.VISIBLE);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });
        startManualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = INPUT_FORM;
                getSupportFragmentManager().beginTransaction().remove(scanner).commit();
                fragWrap.setVisibility(View.GONE);
                scanner = null;
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });

        scanModeBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleMode();
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        itemsRV.setLayoutManager(linearLayoutManager);
        itemsRV.setAdapter(adapter);

        int animDuration = 750;
        modeMsgAnimator = ObjectAnimator.ofFloat(modeMsg, View.ALPHA, 1f, .5f);
        modeMsgAnimator.setDuration(animDuration);
        modeMsgAnimator.setRepeatMode(ValueAnimator.REVERSE);
        modeMsgAnimator.setRepeatCount(ValueAnimator.INFINITE);
        modeMsgAnimator.start();
    }


    void updateDevStatus(boolean status) {
        dev = status;
        lookupCodeService = dev? new DummyLookupCodeService() : new LookupCodeService();
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
            lookupCodeService.fetchProductData(data, dataResHandler);
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

        int animDuration = 750;
        sheetAnimator = ObjectAnimator.ofInt(bottomSheetBehavior, "peekHeight", Utils.dpToPx(75f,this), Utils.dpToPx(155f, this));
        sheetAnimator.setDuration(animDuration);
        sheetAnimator.setRepeatMode(ValueAnimator.REVERSE);
        sheetAnimator.setRepeatCount(1);
        sheetAnimator.start();

        if (mode == MODE_IN) {
            addTally += item.getQty();
            addTallyText.setText(String.valueOf(addTally)+" added");
        }
        if (mode == MODE_OUT) {
            removeTally += item.getQty();
            removeTallyText.setText(String.valueOf(removeTally)+" removed");
        }
    }




    private void endSearchingState() {
        searching = false;
        scanner.allowScan();
        scannerSpinner.setVisibility(View.GONE);
        newTransactionSpinner.setVisibility(View.GONE);
    }

    private void induceSearchingState() {
        searching = true;
        scanner.disallowScan();
        scannerSpinner.setVisibility(View.VISIBLE);
        newTransactionSpinner.setVisibility(View.VISIBLE);
    }


    private void toggleMode() {
        if (mode == MODE_IN) switchToOut();
        else switchToIn();
    }

    private void switchToIn() {
        mode = MODE_IN;
        modeMsg.setText("SCANNING IN");
        scanModeBar.setBackgroundColor(getResources().getColor(R.color.in_green));
        inForm.setVisibility(View.VISIBLE);
        outForm.setVisibility(View.GONE);
    }
    private void switchToOut() {
        mode = MODE_OUT;
        modeMsg.setText("SCANNING OUT");
        scanModeBar.setBackgroundColor(getResources().getColor(R.color.out_red));
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
                qtyText.setText(String.format("Qty: %d", item.getQty()));
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