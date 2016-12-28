package com.sds.study.babyseaterapp.calendar.diary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by pch on 2016. 12. 16..
 */

public class DiaryListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Diary>list=new ArrayList<Diary>();
    SQLiteDatabase db;

    public DiaryListAdapter(Context context) {
        this.context = context;
        DiaryListActivity diaryListActivity=(DiaryListActivity)context;
        db=diaryListActivity.db;

        getList();

    }
    public void getList() {

        String sql = "select * from diary";

        Cursor rs = db.rawQuery(sql, null);

        list.removeAll(list);

        while (rs.moveToNext()) {

            int diary_id = rs.getInt(rs.getColumnIndex("diary_id"));
            String title = rs.getString(rs.getColumnIndex("title"));
            String content = rs.getString(rs.getColumnIndex("content"));


            Diary dto = new Diary();

            dto.setDiary_id(diary_id);
            dto.setTitle(title);
            dto.setContent(content);

            list.add(dto);

        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getDiary_id();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view=null;
        Diary diary=list.get(i);

        if(view!=null){
            view=convertView;
            DiaryItem item=(DiaryItem)view;
            item.setDiary(diary);

        }else{
            view=new DiaryItem(context,diary);
        }

        return view;
    }
}
