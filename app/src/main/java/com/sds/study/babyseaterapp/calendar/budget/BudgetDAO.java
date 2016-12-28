package com.sds.study.babyseaterapp.calendar.budget;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.sds.study.babyseaterapp.calendar.CalendarActivity.db;
import static com.sds.study.babyseaterapp.calendar.budget.BudgetActivity.budgetActivity;


public class BudgetDAO{
    String TAG;

    public BudgetDAO() {
        TAG=this.getClass().getName()+"/test";
    }

    public void msgRegist(BudgetDTO budgetDTO){
        Log.d(TAG,"msgRegist반응");

        String sql="insert into costtable(cardcompany,regdate,category,cost,content) values(?,?,?,?,?)";
        String cardcompany= budgetDTO.getCardCompany();
        String regdate= budgetDTO.getRegdate();
        String category= budgetDTO.getCategory();
        String cost= budgetDTO.cost;
        String content= budgetDTO.content;
        Log.d(TAG,"content는"+content);

        db.execSQL(sql,new String[]{cardcompany,regdate,category,cost,content});
        Log.d(TAG,"msgRegist끝");
    }

    public List costlist(){
        Log.d(TAG,"costlist반응");
        String sql="select * from costtable";
        Cursor rs=db.rawQuery(sql,null);
        ArrayList<BudgetDTO> budgetDTOArrayList =new ArrayList<BudgetDTO>();

        while(rs.moveToNext()){
            BudgetDTO budgetDTO =new BudgetDTO();
            budgetDTO.setCardCompany(rs.getString(rs.getColumnIndex("cardcompany")));
            Log.d(TAG, budgetDTO.getCardCompany());
            budgetDTO.setContent(rs.getString(rs.getColumnIndex("content")));
            Log.d(TAG, budgetDTO.getContent());
            budgetDTO.setCost(rs.getString(rs.getColumnIndex("cost")));
            Log.d(TAG, budgetDTO.getCost());
            budgetDTO.setRegdate(rs.getString(rs.getColumnIndex("regdate")));
            Log.d(TAG, budgetDTO.getRegdate());
            Log.d(TAG,"cost_id는"+rs.getInt(rs.getColumnIndex("cost_id")));
            budgetDTOArrayList.add(budgetDTO);
        }
        Log.d(TAG,"costlist끝");

        return budgetDTOArrayList;
    }
}
