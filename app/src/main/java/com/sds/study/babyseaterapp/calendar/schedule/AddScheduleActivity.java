package com.sds.study.babyseaterapp.calendar.schedule;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.sds.study.babyseaterapp.BabySeaterSQLHelper;
import com.sds.study.babyseaterapp.MainActivity;
import com.sds.study.babyseaterapp.R;

import java.util.Calendar;

import static com.sds.study.babyseaterapp.calendar.CalendarActivity.db;

/**
 * Created by student on 2016-12-19.
 */

public class AddScheduleActivity extends AppCompatActivity implements View.OnClickListener{
    Calendar c;
    int myHour, myMinute;
    static String Hour1,minute1;
    static String year1,month1,date1;
    static boolean isAlarm=true;
    ImageView add_alarm;
    TextView timeDisplay, dayNight;
    ListView layout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_add_schedule);

        layout = (ListView) findViewById(R.id.listView);
        timeDisplay = (TextView) findViewById(R.id.timeDisplay);
        dayNight = (TextView) findViewById(R.id.dayNight);
        add_alarm=(ImageView)findViewById(R.id.add_alarm);

        timeDisplay.setOnClickListener(this);
        add_alarm.setOnClickListener(this);

        //현재 액티비티에 넘겨진 인텐트가 있을 경우 받는다..
        Intent intent = this.getIntent();

    }

    public void btnClick(View view){
        switch (view.getId()){
            case R.id.back_bt:
                Toast.makeText(this, "뒤로가기버튼누름", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ScheduleActivity.class);
                this.startActivity(intent);
                break;

            case R.id.add_bt:
                regist();
                if(Hour1==null || minute1==null){
                    Toast.makeText(this, "시간과 분을 선택해 주세요", Toast.LENGTH_SHORT).show();
                    break;
                }
                Toast.makeText(this, Hour1, Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, ScheduleActivity.class);
                intent2.putExtra("hour1",Hour1);
                intent2.putExtra("minute1",minute1);
                intent2.putExtra("year1",year1);
                intent2.putExtra("month1",month1);
                intent2.putExtra("date1",date1);

                this.startActivity(intent2);
                //여기에서 item 생성해서 layout에 동적으로 뿌리기!!
                //intent로 정보 넘겨서 메인에서 생성하나??
                //어댑터로 어떻게 넘기지??
        }
    }



    public void onClick(View view) {

        c = Calendar.getInstance();

        switch (view.getId()) {
            //timeDisplay를 눌렀을때 TimePicker가 뜨도록 설정
            case R.id.timeDisplay:
                myHour = c.get(Calendar.HOUR_OF_DAY);
                myMinute = c.get(Calendar.MINUTE);
                //----------------------------년 월 일 가져오는 부분------------------------
                year1=String.valueOf(c.get(Calendar.YEAR));
                month1=String.valueOf(c.get(Calendar.MONTH));
                date1=String.valueOf(c.get(Calendar.DATE));

                Toast.makeText(AddScheduleActivity.this,
                        "- onCreateDialog(ID_TIMEPICKER) -", Toast.LENGTH_LONG)
                        .show();
                Dialog dlgTime = new TimePickerDialog(this, myTimeSetListener,
                        myHour, myMinute, false);
                dlgTime.show();
                break;

            default:
                break;

            case R.id.add_alarm:
                if(isAlarm==true) {
                    isAlarm=false;
                    add_alarm.setImageResource(R.drawable.alarm_off);
                }else{
                    isAlarm=true;
                    add_alarm.setImageResource(R.drawable.alarm);
                }
        }
    }

    private TimePickerDialog.OnTimeSetListener myTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = "Hour: " + String.valueOf(hourOfDay) + "\n"
                    + "Minute: " + String.valueOf(minute);
            Hour1=String.valueOf(hourOfDay);
            minute1=String.valueOf(minute);
            Toast.makeText(AddScheduleActivity.this, time, Toast.LENGTH_LONG).show();
            if (hourOfDay <12) {
                timeDisplay.setText(hourOfDay + ":" + minute);
                dayNight.setText("am");
            }else if(hourOfDay==12){
                timeDisplay.setText(hourOfDay+ ":" + minute);
                dayNight.setText("pm");
            }else if(hourOfDay>12){
                timeDisplay.setText(hourOfDay-12 + ":" + minute);
                dayNight.setText("pm");
            }
        }
    };

    public void regist(){
        //이 앱이 보유한 sqlite 데이터베이스에 insert..
        String sql="insert into schedule(sc_year,sc_month,sc_date,sc_hour,sc_minute)";
        sql+=" values(?,?,?,?,?)";

        String year=year1;
        String month=month1;
        String date=date1;
        String hour=Hour1;
        String minute=minute1;

        db.execSQL(sql,new String[]{year,month,date,hour,minute});

        //Listview갱신
        Toast.makeText(this, "DB등록성공!!", Toast.LENGTH_SHORT).show();



    }
}
