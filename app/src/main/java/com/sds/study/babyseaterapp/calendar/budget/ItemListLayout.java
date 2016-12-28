package com.sds.study.babyseaterapp.calendar.budget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.sds.study.babyseaterapp.R;

public class ItemListLayout extends LinearLayout{
    public ItemListLayout(Context context) {
        super(context);
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_budget_list,this);
    }
}
