/*
* Copyright (C) 2015 Hansoo Lab.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.sds.study.babyseaterapp.calendar.cal;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sds.study.babyseaterapp.R;

import java.util.Calendar;

import static com.sds.study.babyseaterapp.calendar.CalendarActivity.TODAY_DATE;
import static com.sds.study.babyseaterapp.calendar.CalendarActivity.TODAY_MONTH;
import static com.sds.study.babyseaterapp.calendar.CalendarActivity.TODAY_YEAR;

/**
 * View to display a day
 * @author Brownsoo
 *
 */
public class OneDayLayout extends LinearLayout{
    
    /** number text field */
    TextView txt_day, txt_diary, txt_schedule, txt_budget;
    ImageView img_icon_diary, img_icon_schedule, img_icon_budget;
    
    /** Value object for a day info */
    OneDayData oneDayData;

    public OneDayLayout(Context context){

        super(context);
        init(context);

 
    }

    public OneDayLayout(Context context, AttributeSet attrs){

        super(context, attrs);
        init(context);

    }

    public void init(Context context){

        View view = View.inflate(context, R.layout.item_day, this);
        
        txt_day = (TextView) view.findViewById(R.id.txt_day);
        txt_diary = (TextView) view.findViewById(R.id.txt_diary);
        txt_schedule = (TextView) view.findViewById(R.id.txt_schedule);
        txt_budget = (TextView) view.findViewById(R.id.txt_budget);
        img_icon_diary = (ImageView) view.findViewById(R.id.img_icon_diary);
        img_icon_schedule = (ImageView) view.findViewById(R.id.img_icon_schedule);
        img_icon_budget = (ImageView) view.findViewById(R.id.img_icon_budget);

        if( txt_diary.getText().toString().equals("") ){

            img_icon_diary.setImageResource(0);

        }else{

            img_icon_diary.setImageResource(R.drawable.icon_diary);

        }

        if( txt_schedule.getText().toString().equals("") ){

            img_icon_schedule.setImageResource(0);

        }else{

            img_icon_schedule.setImageResource(R.drawable.icon_schedule);

        }

        if( txt_budget.getText().toString().equals("") ){

            img_icon_budget.setImageResource(0);

        }else{

            img_icon_budget.setImageResource(R.drawable.icon_schedule);

        }

        oneDayData = new OneDayData();
        
    }

    public void setDay(int year, int month, int day) {

        this.oneDayData.calendar.set(year, month, day);

    }

    public void setDay(Calendar cal) {

        this.oneDayData.setDay((Calendar) cal.clone());

    }

    public void setDay(OneDayData one) {

        this.oneDayData = one;

    }

    public OneDayData getDay() {

        return oneDayData;

    }

    public int get(int field) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {

        return oneDayData.get(field);

    }

    public void refresh() {

        txt_day.setText(String.valueOf(oneDayData.get(Calendar.DAY_OF_MONTH)));

        if(oneDayData.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {

            txt_day.setTextColor(Color.RED);

        }else if(oneDayData.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {

            txt_day.setTextColor(Color.BLUE);

        }else {

            txt_day.setTextColor(Color.BLACK);

        }

        if( oneDayData.calendar.get(Calendar.DAY_OF_MONTH)==TODAY_DATE && (oneDayData.calendar.get(Calendar.MONTH)+1)==TODAY_MONTH && oneDayData.calendar.get(Calendar.YEAR)==TODAY_YEAR ){

            this.setBackgroundColor(Color.YELLOW);

        } else {

            this.setBackgroundColor(Color.WHITE);
            
        }

    }
    
}