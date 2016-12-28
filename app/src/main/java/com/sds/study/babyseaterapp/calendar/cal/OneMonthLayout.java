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
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * View to display a month
 */
public class OneMonthLayout extends LinearLayout implements View.OnClickListener {

    public interface OnClickDayListener {
        void onClick(OneDayLayout oneDayLayout);
    }

    int this_year;
    int this_month;

    ArrayList<LinearLayout> weeks = null;
    ArrayList<OneDayLayout> oneDayLayouts = null;
    OnClickDayListener onClickDayListener;
    private final OnClickDayListener dummyClickDayListener = new OnClickDayListener() {
        @Override
        public void onClick(OneDayLayout oneDayLayout) {

        }
    };

    public void setOnClickDayListener(OnClickDayListener onClickDayListener) {
        if (onClickDayListener != null) {
            this.onClickDayListener = onClickDayListener;
        }
        else {
            this.onClickDayListener = dummyClickDayListener;
        }
    }


    public OneMonthLayout(Context context) {
        this(context, null);
    }

    public OneMonthLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OneMonthLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setOrientation(LinearLayout.VERTICAL);
        onClickDayListener = dummyClickDayListener;

        //Prepare many day-views enough to prevent recreation.
        if(weeks == null) {

            weeks = new ArrayList<>(6); //Max 6 weeks in a month
            oneDayLayouts = new ArrayList<>(42); // 7 days * 6 weeks = 42 days

            LinearLayout layout_oneday = null;

            for(int i=0; i<42; i++) {

                if(i % 7 == 0) {
                    //Create new week layout
                    layout_oneday = new LinearLayout(context);
                    LayoutParams params
                            = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
                    params.weight = 1;
                    layout_oneday.setOrientation(LinearLayout.HORIZONTAL);
                    layout_oneday.setLayoutParams(params);
                    layout_oneday.setWeightSum(7);

                    weeks.add(layout_oneday);
                }

                LayoutParams params
                        = new LayoutParams(0, LayoutParams.MATCH_PARENT);
                params.weight = 1;

                OneDayLayout oneDayLayout = new OneDayLayout(context);
                oneDayLayout.setLayoutParams(params);
                oneDayLayout.setOnClickListener(this);

                layout_oneday.addView(oneDayLayout);
                oneDayLayouts.add(oneDayLayout);
            }
        }
        
        //for Preview of Graphic editor
        if(isInEditMode()) {
            Calendar calendar = Calendar.getInstance();
            make(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH));
        }

    }

    /**
     * Get current year
     * @return 4 digits number of year
     */
    public int getYear() {
        return this_year;
    }

    /**
     * Get current month
     * @return 0~11 (Calendar.JANUARY ~ Calendar.DECEMBER)
     */
    public int getMonth() {
        return this_month;
    }


    /**
     * Any layout manager that doesn't scroll will want this.
     */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }


    /**
     * Make a Month view
     * @param year year of this month view (4 digits number)
     * @param month month of this month view (0~11)
     */
    public void make(int year, int month)
    {
        if(this_year == year && this_month == month) {
            return;
        }
        
        long makeTime = System.currentTimeMillis();
        
        this.this_year = year;
        this.this_month = month;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);//Sunday is first day of week in this sample
        
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);//Get day of the week in first day of this month
        int maxOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//Get max day number of this month
        ArrayList<OneDayData> oneDayDataList = new ArrayList<>();

        calendar.add(Calendar.DAY_OF_MONTH, Calendar.SUNDAY - dayOfWeek);//Move to first day of first week
        //HLog.d(TAG, CLASS, "first day : " + cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.KOREA) + " / " + cal.get(Calendar.DAY_OF_MONTH));

        /* add previous month */
        int seekDay;
        for(;;) {
            seekDay = calendar.get(Calendar.DAY_OF_WEEK);
            if(dayOfWeek == seekDay) break;
            
            OneDayData oneDayData = new OneDayData();
            oneDayData.setDay(calendar);
            oneDayDataList.add(oneDayData);
            //하루 증가
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        //HLog.d(TAG, CLASS, "this month : " + cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.KOREA) + " / " + cal.get(Calendar.DAY_OF_MONTH));
        /* add this month */
        for(int i=0; i < maxOfMonth; i++) {
            OneDayData oneDayData = new OneDayData();
            oneDayData.setDay(calendar);
            oneDayDataList.add(oneDayData);
            //add one day
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        /* add next month */
        for(;;) {
            if(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                OneDayData oneDayData = new OneDayData();
                oneDayData.setDay(calendar);
                oneDayDataList.add(oneDayData);
            } 
            else {
                break;
            }
            //add one day
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        if(oneDayDataList.size() == 0) return;

        //Remove all day-views
        this.removeAllViews();
        
        int count = 0;
        for(OneDayData oneDayData : oneDayDataList) {
            
            if(count % 7 == 0) {
                addView(weeks.get(count / 7));
            }
            OneDayLayout oneDayLayout = oneDayLayouts.get(count);

            oneDayLayout.setDay(oneDayData);
            oneDayLayout.refresh();
            count++;
        }

        //Set the weight-sum of LinearLayout to week counts
        this.setWeightSum(getChildCount());
 
    }


    protected String doubleString(int value) {

        String temp;
 
        if(value < 10){
            temp = "0"+ String.valueOf(value);
             
        }else {
            temp = String.valueOf(value);
        }
        return temp;
    }
 
    @Override
    public void onClick(View view) {

        OneDayLayout oneDayLayout = (OneDayLayout) view;

        this.onClickDayListener.onClick(oneDayLayout);

    }

}