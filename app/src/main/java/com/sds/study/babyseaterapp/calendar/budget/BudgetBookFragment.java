package com.sds.study.babyseaterapp.calendar.budget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sds.study.babyseaterapp.R;


public class BudgetBookFragment extends Fragment{

    ItemInputLayout itemInputLayout;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LinearLayout costBook=(LinearLayout) inflater.inflate(R.layout.layout_one_spend,null);
        itemInputLayout =(ItemInputLayout)costBook.findViewById(R.id.inputView);

        return costBook;
    }

}
