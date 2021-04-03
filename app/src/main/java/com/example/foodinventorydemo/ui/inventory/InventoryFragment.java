package com.example.foodinventorydemo.ui.inventory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.foodinventorydemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryFragment extends Fragment {

//    private InventoryViewModel inventoryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        inventoryViewModel =
//                ViewModelProviders.of(this).get(InventoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inventory, container, false);
//        final TextView textView = root.findViewById(R.id.text_inventory);

//        inventoryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        ArrayList<FoodItemDisplayable> grainList = new ArrayList<>();
        grainList.add(new FoodItemDisplayable("Rice", "05/07/2021"));
        grainList.add(new FoodItemDisplayable("Noodles", "05/07/2021"));

        ArrayList<FoodItemDisplayable> fruitList = new ArrayList<>();
        fruitList.add(new FoodItemDisplayable("Dried Apples", "08/20/2021"));
        fruitList.add(new FoodItemDisplayable("Dried Pears", "08/20/2021"));

        ArrayList<FoodItemDisplayable> otherList = new ArrayList<>();
        otherList.add(new FoodItemDisplayable("Peanut Butter", "04/21/2021"));
        otherList.add(new FoodItemDisplayable("Bottled Water", "08/20/2021"));

        HashMap<String, ArrayList<FoodItemDisplayable>> listData = new HashMap<>();

        listData.put("Grains", grainList);
        listData.put("Fruit", fruitList);
        listData.put("Other", otherList);
        //TODO add groups and data
        List<String> keyList = new ArrayList<>(listData.keySet());

        ExpandableListView expandableListView = root.findViewById(R.id.expandable_list_view);
        Adapter adapter = new Adapter(keyList, listData);
        expandableListView.setAdapter(adapter);
        return root;
    }

    class FoodItemDisplayable {
        public String foodName;
        public String expirationDate;
        public FoodItemDisplayable(String foodName, String expirationDate) {
            this.foodName = foodName;
            this.expirationDate = expirationDate;
        }

        Bitmap displayIcon(Context context) {
            return null;
        }

        String displayText() {
            return foodName + " - Exp: " + expirationDate;
        }

        void handleClick(Context context) {

        }
    }

    class Adapter extends BaseExpandableListAdapter {
        class ViewHolder {
            public ImageView icon;
            public TextView textView;
            public NumberPicker numberPicker;
            public FoodItemDisplayable displayable;

            public ViewHolder(View view) {
                icon = view.findViewById(R.id.list_item_icon);
                textView = view.findViewById(R.id.expanded_list_item);
                numberPicker = view.findViewById(R.id.list_number_picker);
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
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            View rowView;
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
            viewHolder.icon.setImageBitmap(displayable.displayIcon(parent.getContext()));
            viewHolder.textView.setText(displayable.displayText());
            viewHolder.numberPicker.setMaxValue(100);
            viewHolder.numberPicker.setMinValue(0);
            viewHolder.displayable = displayable;
            return rowView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}