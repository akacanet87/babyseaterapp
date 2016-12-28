package com.sds.study.babyseaterapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 안드로이드에서는 SQLITE 데이터베이스의 위치가 이미 data/data/앱패키지/databases로
 * 정해져있기 때문에 오직 Sqliteopenhelper라는 클래스를 통해 접근 및 제어 해야 한다.
 * 즉! 임의로 개발자가 디렉토리에 접근 불가
 */

public class BabySeaterSQLHelper extends SQLiteOpenHelper{
    String TAG;

    //String name: 생성할 db파일명
    //int version: 최초의 버전 넘버
    public BabySeaterSQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        TAG=this.getClass().getName();
    }

    //데이터베이스가 최초에 생성될 때 호출됨..
    //즉! 파일이 존재하지 않을 때 이 메서드가 호출됨.
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuffer schedule_sql=new StringBuffer();
        schedule_sql.append("create table schedule(");
        schedule_sql.append("schedule_id integer primary key autoincrement");
        schedule_sql.append(",baby_id int(20)");
        schedule_sql.append(",mom_id int(20)");
        schedule_sql.append(",sc_year int(20)");
        schedule_sql.append(",sc_month int(20)");
        schedule_sql.append(",sc_date int(20)");
        schedule_sql.append(",sc_hour int(20)");
        schedule_sql.append(",sc_minute int(20)");
        schedule_sql.append(",sc_content varchar(500)");
        schedule_sql.append(");");

        sqLiteDatabase.execSQL(schedule_sql.toString());

        StringBuffer diary_sql=new StringBuffer();

        diary_sql.append("create table diary(");
        diary_sql.append("diary_id integer primary key autoincrement");
        diary_sql.append(", title varchar(30)");
        diary_sql.append(", content text");
        diary_sql.append(");");

        sqLiteDatabase.execSQL(diary_sql.toString());

        StringBuffer budget_sql=new StringBuffer();

        budget_sql.append("create table budget(");
        budget_sql.append("budget_id integer primary key autoincrement");
        budget_sql.append(",cardcompany varchar(200)");
        budget_sql.append(",regdate varchar(200)");
        budget_sql.append(",category varchar(200)");
        budget_sql.append(",budget varchar(100)");
        budget_sql.append(",content varchar(100)");
        budget_sql.append(")");

        sqLiteDatabase.execSQL(budget_sql.toString());

        StringBuffer school_sql=new StringBuffer();

        school_sql.append("create table school(");
        school_sql.append("school_id integer primary key autoincrement");
        school_sql.append(",addr varchar(300)");
        school_sql.append(",school_name varchar(50)");
        school_sql.append(",lat varchar(50)");
        school_sql.append(",lng varchar(50)");
        school_sql.append(",schoolbus varchar(4)");
        school_sql.append(",max_stu_num integer");
        school_sql.append(",teacher_num integer");
        school_sql.append(",call_num varchar(20)");
        school_sql.append(",cctv_num integer");
        school_sql.append(")");

        sqLiteDatabase.execSQL(school_sql.toString());

        Log.d(TAG,"데이터 베이스 구축");
    }

    //해당 파일이 이미 존재하나, 버전 숫자가 다른경우우
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists schedule");
        sqLiteDatabase.execSQL("drop table if exists diary");
        sqLiteDatabase.execSQL("drop table if exists budget");
        sqLiteDatabase.execSQL("drop table if exists school");
        onCreate(sqLiteDatabase);
        Log.d(TAG,"upgrade");
    }
}
