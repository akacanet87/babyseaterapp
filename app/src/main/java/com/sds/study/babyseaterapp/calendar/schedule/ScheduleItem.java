package com.sds.study.babyseaterapp.calendar.schedule;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sds.study.babyseaterapp.R;

public class ScheduleItem extends LinearLayout {
    private Schedule schedule;
    TextView  item_txt_year,item_txt_month,item_txt_date,item_txt_hour,item_txt_minute;

    boolean isAlarmOn=false;
    ImageView alarm_img;

    public ScheduleItem(Context context, Schedule schedule) {
        super(context);
        this.schedule=schedule;
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_schedule,this);

         item_txt_year=(TextView) this.findViewById(R.id.item_txt_year);
         item_txt_month=(TextView) this.findViewById(R.id.item_txt_month);
         item_txt_date=(TextView) this.findViewById(R.id.item_txt_date);
         item_txt_hour=(TextView) this.findViewById(R.id.item_txt_hour);
         item_txt_minute=(TextView) this.findViewById(R.id.item_txt_minute);

        item_txt_year.setText(String.valueOf(schedule.getSc_year()));
        item_txt_month.setText(String.valueOf(schedule.getSc_month()));
        item_txt_date.setText(String.valueOf(schedule.getSc_date()));
        item_txt_hour.setText(String.valueOf(schedule.getSc_hour()));
        item_txt_minute.setText(String.valueOf(schedule.getSc_minute()));

    }

    public ScheduleItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;

        item_txt_year.setText(String.valueOf(schedule.getSc_year()));
        item_txt_month.setText(String.valueOf(schedule.getSc_month()));
        item_txt_date.setText(String.valueOf(schedule.getSc_date()));
        item_txt_hour.setText(String.valueOf(schedule.getSc_hour()));
        item_txt_minute.setText(String.valueOf(schedule.getSc_minute()));

    }
}
