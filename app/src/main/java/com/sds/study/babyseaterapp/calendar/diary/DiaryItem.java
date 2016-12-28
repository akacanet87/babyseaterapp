package com.sds.study.babyseaterapp.calendar.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sds.study.babyseaterapp.R;


public class DiaryItem extends LinearLayout {

    Diary diary;
    LayoutInflater inflater;
    TextView list_id, list_title, list_content;

    public DiaryItem(Context context, Diary diary) {
        super(context);
        this.diary = diary;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.item_diary, this);

        list_id = (TextView) findViewById(R.id.list_id);
        list_title=(TextView)findViewById(R.id.list_title);
        list_content=(TextView)findViewById(R.id.list_content);


        list_id.setText(Integer.toString(this.diary.getDiary_id()));
        list_title.setText(this.diary.getTitle());
        list_content.setText(this.diary.getContent());


    }
    public Diary getDiary(){
        return diary;
    }


    public void setDiary(Diary diary) {
        this.diary=diary;
        list_id.setText(diary.getDiary_id());
        list_title.setText(diary.getTitle());
        list_content.setText(diary.getContent());
    }
}
