package com.sds.study.babyseaterapp.calendar.diary;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sds.study.babyseaterapp.BabySeaterSQLHelper;
import com.sds.study.babyseaterapp.R;

import static com.sds.study.babyseaterapp.calendar.CalendarActivity.db;

/**
 * Created by pch on 2016. 12. 19..
 */

public class DetailDiaryActivity extends AppCompatActivity{
    EditText edit_diarytitle, edit_diarycontent;
    Intent intent;
    Diary diary;
    int diary_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_update_diary);

        intent = getIntent();
        diary = intent.getParcelableExtra("diary");
        diary_id=diary.getDiary_id();

        edit_diarytitle = (EditText) findViewById(R.id.edit_diarytitle);
        edit_diarycontent = (EditText) findViewById(R.id.edit_diarycontent);

        edit_diarytitle.setText(diary.getTitle());
        edit_diarycontent.setText(diary.getContent());

    }

    public void update() {
        String sql;
        sql = "update diary set title=?, content=? where diary_id=?";

        db.execSQL(sql, new Object[]{

                edit_diarytitle.getText().toString(), edit_diarycontent.getText().toString(), Integer.toString(diary_id)
        });
        setResult(RESULT_OK, intent);

        finish();
    }

    public void delete() {
        String sql;
        sql = "delete from diary where diary_id=?";
        db.execSQL(sql, new String[]{Integer.toString(diary_id)});
        setResult(RESULT_OK, intent);

        finish();
    }

    public void btnClick(View view) {
        if (view.getId() == R.id.bt_confirm) {
            update();
            showMsg("안내","저장하였습니다");


        }else if (view.getId()==R.id.bt_delete){
            delete();
            showMsg("안내","삭제하였습니다");


        }else if(view.getId()==R.id.bt_cancel){
            showMsg("안내","취소하였습니다");
            back();

        }
    }
    public void back(){
        Intent intent=new Intent(this, DiaryListActivity.class);
        startActivity(intent);
    }

    public void showMsg(String title, String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title).setMessage(msg).show();

    }

}
