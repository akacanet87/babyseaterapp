package com.sds.study.babyseaterapp.calendar.budget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sds.study.babyseaterapp.R;

import java.util.ArrayList;

public class BudgetListActivity extends AppCompatActivity implements View.OnClickListener{
    BudgetDAO budgetDAO;
    ItemListLayout itemListLayout;
    DailyBudgetListLayout dailyBudgetListLayout;
    String TAG;
    LinearLayout cost_list , item_list;
    ArrayList<BudgetDTO> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_spend_list);
        Log.d(TAG,"costList호출");
        TAG=this.getClass().getName()+"/test";

        budgetDAO =new BudgetDAO();
        cost_list=(LinearLayout)findViewById(R.id.cost_list);
        init();
    }

    public void init(){
        Log.d(TAG,"init호출");
        list=(ArrayList) budgetDAO.costlist();
        Log.d(TAG,"list크기는"+list.size());
        for(int i=0;i<list.size();i++){
            dailyBudgetListLayout =new DailyBudgetListLayout(this);
            item_list=(LinearLayout) dailyBudgetListLayout.findViewById(R.id.item_list);
            TextView txt_day=(TextView) dailyBudgetListLayout.findViewById(R.id.txt_day);
            txt_day.setText(list.get(i).getRegdate());
            cost_list.addView(dailyBudgetListLayout);
            if(txt_day.getText().equals(list.get(i).getRegdate())){
                itemListLayout =new ItemListLayout(this);
                item_list.addView(itemListLayout);
                item_list.setOnClickListener(this);
                TextView txt_itemcost=(TextView) itemListLayout.findViewById(R.id.txt_itemcost);
                txt_itemcost.setText(list.get(i).getCost());
                TextView txt_itemcontent=(TextView) itemListLayout.findViewById(R.id.txt_itemcontent);
                txt_itemcontent.setText(list.get(i).getContent());
                TextView txt_costid=(TextView) itemListLayout.findViewById(R.id.txt_costid);
                txt_costid.setText(list.get(i).getCost_id());
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,BudgetActivity.class);
        TextView txt_costid=(TextView)view.findViewById(R.id.txt_costid);
        int cost_id=Integer.parseInt(txt_costid.getText().toString());
        BudgetDTO budgetDTO =list.get(cost_id);
        intent.putExtra("budgetDTO", budgetDTO);
        startActivity(intent);
    }
}

