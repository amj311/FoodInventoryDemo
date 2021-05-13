package com.example.foodinventorydemo.ui.main.inventory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.foodinventorydemo.R;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inventory, container, false);

        ArrayList<FoodItemDisplayable> grainList = new ArrayList<>();
        grainList.add(new FoodItemDisplayable("https://previews.123rf.com/images/jamakosy/jamakosy1711/jamakosy171100225/90332158-white-rice-in-burlap-sack-bag-isolated-on-white-background.jpg","Rice", "05/07/2025", "4"));
        grainList.add(new FoodItemDisplayable("https://www.dutchmansstore.com/wp-content/uploads/2020/09/DSC_4812-Edit-scaled-e1601073911963.jpg","Noodles", "05/07/2025", "3"));

        ArrayList<FoodItemDisplayable> fruitList = new ArrayList<>();
        fruitList.add(new FoodItemDisplayable("https://nuts.com/images/rackcdn/ed910ae2d60f0d25bcb8-80550f96b5feb12604f4f720bfefb46d.ssl.cf1.rackcdn.com/e8d95ad6e4090391-sXRwtvud-large.jpg","Dried Apples", "04/30/2021", "5"));
        fruitList.add(new FoodItemDisplayable("https://www.bellaviva.com/assets/images/dried-fruit/pear.jpg","Dried Pears", "08/20/2021", "6"));

        ArrayList<FoodItemDisplayable> otherList = new ArrayList<>();
        otherList.add(new FoodItemDisplayable("https://www.peanutbutter.com/wp-content/uploads/2019/03/SKIPPY_Product_PB_Spread_Creamy_Peanut_Butter_28oz.png","Peanut Butter", "04/21/2021", "7"));
        otherList.add(new FoodItemDisplayable("https://images.heb.com/is/image/HEBGrocery/000567987","Bottled Water", "08/20/2023", "2"));
        otherList.add(new FoodItemDisplayable("https://images.homedepot-static.com/productImages/741fbb97-dedc-4571-acf1-9856fe6ec0ea/svn/camco-toilet-paper-40274-64_1000.jpg","Toilet Paper", "08/14/2054", "145"));

        HashMap<String, ArrayList<FoodItemDisplayable>> listData = new HashMap<>();

        listData.put("Grains", grainList);
        listData.put("Fruit", fruitList);
        listData.put("Other", otherList);

        List<String> keyList = new ArrayList<>(listData.keySet());

        ExpandableListView expandableListView = root.findViewById(R.id.expandable_list_view);
        Adapter adapter = new Adapter(keyList, listData);
        expandableListView.setAdapter(adapter);

        for (int i = 0; i < listData.size(); i++) {
            expandableListView.expandGroup(i);
        }
        return root;
    }

    class FoodItemDisplayable {
        public String url;
        public String foodName;
        public String expirationDate;
        public String quantity;
        public FoodItemDisplayable(String url, String foodName, String expirationDate, String quantity) {
            this.url = url;
            this.foodName = foodName;
            this.expirationDate = expirationDate;
            this.quantity = quantity;
        }

        Bitmap displayIcon(Context context) {
            return null;
        }

        String displayFoodName() {
            return foodName;
        }

        String displayExpirationDate() {
            return "Expires: " + expirationDate;
        }

        String displayQuantity() {
            return "Qty: " + quantity;
        }

        void handleClick(Context context) {

        }
    }

    class Adapter extends BaseExpandableListAdapter {
        class ViewHolder {
            public ImageView icon;
            public TextView foodName;
            public TextView expirationDate;
            public TextView itemQuantity;
            public ImageView trashCan;
            public ImageView expAlertIcon;
//            public NumberPicker numberPicker;
            public FoodItemDisplayable displayable;

            public ViewHolder(View view) {
                icon = view.findViewById(R.id.list_item_icon);
                foodName = view.findViewById(R.id.food_name);
                expirationDate = view.findViewById(R.id.expiration_date);
                itemQuantity = view.findViewById(R.id.item_quantity);
                trashCan = view.findViewById(R.id.list_item_trash);
                expAlertIcon = view.findViewById(R.id.exp_alert_icon);
//                numberPicker = view.findViewById(R.id.list_number_picker);
                displayable = null;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (displayable != null) {
                            displayable.handleClick(getContext());
                        }
                    }
                });
            }

        }
        List<String> titleList;
        HashMap<String, ArrayList<FoodItemDisplayable>> dataList;

        public Adapter(List<String> titleList, HashMap<String, ArrayList<FoodItemDisplayable>> dataList) {
            this.titleList = titleList;
            this.dataList = dataList;
        }

        @Override
        public int getGroupCount() {
            return this.titleList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this.dataList.get(this.titleList.get(groupPosition)).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this.titleList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return this.dataList.get(this.titleList.get(groupPosition)).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String listTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_group, parent, false);
            }
            TextView listTitleTextView = convertView.findViewById(R.id.list_title);
            listTitleTextView.setTypeface(null, Typeface.BOLD);
            listTitleTextView.setText(listTitle);
            return convertView;
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            final View rowView;
            FoodItemDisplayable displayable = (FoodItemDisplayable) getChild(groupPosition, childPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = layoutInflater.inflate(R.layout.list_item, parent, false);
                viewHolder = new ViewHolder(rowView);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                rowView = convertView;
            }
            String url = displayable.url;
            Picasso.get().load(url).into(viewHolder.icon);
            viewHolder.foodName.setText(displayable.displayFoodName());
            viewHolder.itemQuantity.setText(displayable.displayQuantity());
            viewHolder.trashCan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String categoryName = titleList.get(groupPosition);
                    dataList.get(categoryName).remove(childPosition);
                    notifyDataSetChanged();
                }
            });
//            viewHolder.numberPicker.setMaxValue(100);
//            viewHolder.numberPicker.setMinValue(0);
            viewHolder.displayable = displayable;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uuuu");
            LocalDate expDate = LocalDate.parse(displayable.expirationDate, formatter);
            LocalDate today = LocalDate.now();
            long numDays = ChronoUnit.DAYS.between(today, expDate);
            System.out.println("Number of days between two dates = "+numDays);
            viewHolder.expirationDate.setText(displayable.displayExpirationDate());

            if (numDays <= 31) {
                viewHolder.expAlertIcon.setVisibility(View.VISIBLE);
                viewHolder.expirationDate.setTextColor(getResources().getColor(R.color.out_red));
            }
            else {
                viewHolder.expAlertIcon.setVisibility(View.GONE);
                viewHolder.expirationDate.setTextColor(Color.parseColor("#606060"));
            }

            return rowView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}