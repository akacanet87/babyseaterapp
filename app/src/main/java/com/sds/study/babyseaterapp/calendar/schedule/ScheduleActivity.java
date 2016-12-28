package com.sds.study.babyseaterapp.calendar.schedule;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.sds.study.babyseaterapp.BabySeaterSQLHelper;
import com.sds.study.babyseaterapp.R;

public class ScheduleActivity extends AppCompatActivity{
    Button button;
    static ListView listView;
    static ScheduleAdapter scheduleAdapter;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        listView=(ListView)findViewById(R.id.listView);

        ScheduleAdapter scheduleAdapter =new ScheduleAdapter(this);
        scheduleAdapter.getList();
        scheduleAdapter.notifyDataSetChanged();

        listView.setAdapter(scheduleAdapter);

    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.addSch_bt:
                Toast.makeText(this, "add버튼누름", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, AddScheduleActivity.class);
                this.startActivity(intent);
                break;
        }
    }

}
