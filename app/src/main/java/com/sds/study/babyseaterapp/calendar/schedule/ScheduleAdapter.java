package com.sds.study.babyseaterapp.calendar.schedule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import static com.sds.study.babyseaterapp.calendar.CalendarActivity.db;


public class ScheduleAdapter extends BaseAdapter {
    Context context;
    Intent intent;

    ArrayList<Schedule> scheduleList = new ArrayList<Schedule>();

    public ScheduleAdapter(Context context) {
        this.context = context;
        ScheduleActivity scheduleActivity = (ScheduleActivity) context;
        getList();
        intent = ((Activity) context).getIntent();
    }

    public void getList() {
        String sql = "select * from schedule";
        Cursor rs = db.rawQuery(sql, null);


            //기존 arraylist 모두삭제
            scheduleList.removeAll(scheduleList);

            while (rs.moveToNext()) {
                Schedule dto = new Schedule();

                    int schedule_id = rs.getInt(rs.getColumnIndex("schedule_id"));
                    String year1 = rs.getString(rs.getColumnIndex("sc_year"));
                    String month1 = rs.getString(rs.getColumnIndex("sc_month"));
                    String date1 = rs.getString(rs.getColumnIndex("sc_date"));
                    String hour1 = rs.getString(rs.getColumnIndex("sc_hour"));
                    String minute1 = rs.getString(rs.getColumnIndex("sc_minute"));

                    dto.setSchedule_id(schedule_id);
                    dto.setSc_year(year1);
                    dto.setSc_month(month1);
                    dto.setSc_date(date1);
                    dto.setSc_hour(hour1);
                    dto.setSc_minute(minute1);

                    scheduleList.add(dto);

            }
            this.notifyDataSetChanged();

    }


    //총 아이템 갯수!! arr.length 만큼 확보!!
    public int getCount() {
        return scheduleList.size();
    }

    public Object getItem(int i) {
        return scheduleList.get(i);
    }

    //
    public long getItemId(int i) {
        return scheduleList.get(i).getSchedule_id();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        View view = null;//누가 여기에 들어올지 모른다..
        Schedule schedule = scheduleList.get(i);

        //해당 index에 아이템이 이미 채워져 있다면..
        if (convertView != null) {
            view = convertView;
            ScheduleItem item = (ScheduleItem) view;
            item.setSchedule(schedule);
        } else {
            view = new ScheduleItem(context, schedule);
            //해당 index에 아무것도 없는 상태라면
        }
        ScheduleItem item = new ScheduleItem(context, scheduleList.get(i));

        return item;
    }
}
