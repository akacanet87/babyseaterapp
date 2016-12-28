package com.sds.study.babyseaterapp.calendar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sds.study.babyseaterapp.BabySeaterSQLHelper;
import com.sds.study.babyseaterapp.MainActivity;
import com.sds.study.babyseaterapp.R;
import com.sds.study.babyseaterapp.calendar.budget.BudgetActivity;
import com.sds.study.babyseaterapp.calendar.cal.OneDayLayout;
import com.sds.study.babyseaterapp.calendar.diary.DiaryListActivity;
import com.sds.study.babyseaterapp.calendar.schedule.ScheduleActivity;

import java.util.Calendar;

/**
 * Created by CANET on 2016-12-10.
 */

public class CalendarActivity extends AppCompatActivity{

    Intent intent;
    ImageButton btn_diary_to_main;
    Button btn_add_diary, btn_add_schedule, btn_add_budget, btn_setbudget, btn_setspend, txt_thisyear, txt_thismonth;
    TextView txt_thisdate;
    LinearLayout layout_thisdate;
    public static BabySeaterSQLHelper sqlHelper;    //데이터 베이스 구축
    public static SQLiteDatabase db;  //데이터 베이스 쿼리문 제어
    Calendar calendar = Calendar.getInstance();

    public static int TODAY_YEAR;
    public static int TODAY_MONTH;
    public static int TODAY_DATE;

    int today_year;
    int today_month;
    int today_date;
    int item_id;
    String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_calendar);

        TAG = this.getClass().getName()+"/Canet";

        TODAY_YEAR = calendar.get(Calendar.YEAR);
        TODAY_MONTH = calendar.get(Calendar.MONTH) + 1;
        TODAY_DATE = calendar.get(Calendar.DAY_OF_MONTH);

        layout_thisdate = (LinearLayout) findViewById(R.id.layout_thisdate);
        btn_diary_to_main = (ImageButton) findViewById(R.id.btn_calendar_to_main);
        btn_add_diary = (Button) findViewById(R.id.btn_add_diary);
        btn_add_schedule = (Button) findViewById(R.id.btn_add_schedule);
        btn_add_budget = (Button) findViewById(R.id.btn_add_budget);
        btn_setbudget = (Button) findViewById(R.id.btn_setbudget);
        btn_setspend = (Button) findViewById(R.id.btn_setspend);
        txt_thisyear = (Button) findViewById(R.id.txt_thisyear);
        txt_thismonth = (Button) findViewById(R.id.txt_thismonth);
        txt_thisdate = (TextView) findViewById(R.id.txt_thisdate);

        initDB();

        MonthlyFragment monthlyFragment = (MonthlyFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_monthly);
        monthlyFragment.setOnMonthChangeListener(new OnMonthChangeListener(){

            @Override
            public void onChange(int year, int month){

                today_year = year;
                today_month = month+1;
                txt_thisyear.setText(Integer.toString(today_year));
                txt_thismonth.setText(Integer.toString(today_month));
            }

            @Override
            public void onDayClick(OneDayLayout dayView){

                today_date = dayView.get(Calendar.DAY_OF_MONTH);
                layout_thisdate.setVisibility(View.VISIBLE);
                txt_thisdate.setText(Integer.toString(today_date));

                item_id = today_year*10000+today_month*100+today_date;
                /*Toast.makeText( CalendarActivity.this, "Click  " + dayView.get(Calendar.MONTH) + "/" + dayView.get(Calendar.DAY_OF_MONTH), Toast.LENGTH_SHORT)
                        .show();*/
            }

        });

    }

    //데이터베이스 초기화
    public void initDB(){
        sqlHelper=new BabySeaterSQLHelper(this,"iot.sqlite",null,1);
        db=sqlHelper.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        int id = item.getItemId();
        if(id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goBack(){

        intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        finish();

    }

    public void addDiaryItem(){

        Toast.makeText(this, "+다이어리", Toast.LENGTH_SHORT).show();
        Log.d( TAG, "오늘의 item_id는 "+item_id );

        intent = new Intent(this, DiaryListActivity.class);

        //intent.putExtra("today_text", today_text);

        startActivity(intent);

    }

    public void addScheduleItem(){

        Toast.makeText(this, "+일정", Toast.LENGTH_SHORT).show();
        Log.d( TAG, "오늘의 item_id는 "+item_id );

        intent = new Intent(this, ScheduleActivity.class);

        //intent.putExtra("today_text", today_text);

        startActivity(intent);

    }

    public void addBudgetItem(){

        Toast.makeText(this, "+가계부", Toast.LENGTH_SHORT).show();
        Log.d( TAG, "오늘의 item_id는 "+item_id );

        intent = new Intent(this, BudgetActivity.class);

        String today_text = today_month+"/"+today_date;

        intent.putExtra("today_text", today_text);

        startActivity(intent);

    }

    public void setMonthlyBudget(){

        Toast.makeText(this, "예산설정", Toast.LENGTH_SHORT).show();

    }

    public void showSpendList(){

        Toast.makeText(this, "지출설정", Toast.LENGTH_SHORT).show();

    }

    public void btnCalendarClick(View view){

        switch(view.getId()){

            case R.id.btn_calendar_to_main : goBack(); break;
            case R.id.btn_add_diary : addDiaryItem(); break;
            case R.id.btn_add_schedule : addScheduleItem(); break;
            case R.id.btn_add_budget : addBudgetItem(); break;
            case R.id.btn_setbudget : setMonthlyBudget(); break;
            case R.id.btn_setspend : showSpendList(); break;

        }

    }

}
