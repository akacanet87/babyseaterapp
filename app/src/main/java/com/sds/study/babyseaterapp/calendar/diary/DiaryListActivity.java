package com.sds.study.babyseaterapp.calendar.diary;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.sds.study.babyseaterapp.BabySeaterSQLHelper;
import com.sds.study.babyseaterapp.R;

/**
 * Created by pch on 2016. 12. 19..
 */

public class DiaryListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    SQLiteDatabase db;
    static ListView listView;
    static DiaryListAdapter diaryListAdapter;
    BabySeaterSQLHelper sqlHelper;
    Button bt_itemplus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_diary_list);

        init();

        listView=(ListView)findViewById(R.id.diary_listView);
        diaryListAdapter=new DiaryListAdapter(this);

        listView.setAdapter(diaryListAdapter);
        listView.setOnItemClickListener(this);

        bt_itemplus=(Button)findViewById(R.id.bt_itemplus);

    }
    public void init(){
        sqlHelper=new BabySeaterSQLHelper(this, "iot.sqlite", null,2);
        db=sqlHelper.getWritableDatabase();

    }
    public void btnClick(View view){
        if(view.getId()==R.id.bt_itemplus){
            Intent intent=new Intent(this, AddDiaryActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(this, DetailDiaryActivity.class);
        DiaryItem item=(DiaryItem)view;
        Diary diary=item.getDiary();
        intent.putExtra("diary", diary);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode==RESULT_OK){

                diaryListAdapter.getList();
                diaryListAdapter.notifyDataSetChanged();
            }
        }

    }

}
