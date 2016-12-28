package com.sds.study.babyseaterapp.calendar.diary;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pch on 2016. 12. 13..
 */

public class Diary implements Parcelable{
    private int diary_id;
    private String title;
    private String content;

    public int getDiary_id() {
        return diary_id;
    }

    public void setDiary_id(int diary_id) {
        this.diary_id = diary_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(this.diary_id);
        parcel.writeString(this.title);
        parcel.writeString(this.content);
    }
    public Diary(){

    }

    protected Diary(Parcel in){

        this.diary_id = in.readInt();
        this.title = in.readString();
        this.content = in.readString();

    }

    public static final Creator<Diary> CREATOR = new Creator<Diary>(){

        @Override
        public Diary createFromParcel(Parcel source){

            return new Diary(source);
        }

        @Override
        public Diary[] newArray(int size){

            return new Diary[size];
        }
    };
}

