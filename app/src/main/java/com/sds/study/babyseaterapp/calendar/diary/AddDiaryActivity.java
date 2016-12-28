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

public class AddDiaryActivity extends AppCompatActivity{
    EditText edit_diarytitle,edit_diarycontent;
    Button bt_confirm,bt_cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_diary);

        edit_diarytitle=(EditText)findViewById(R.id.edit_diarytitle);
        edit_diarycontent=(EditText)findViewById(R.id.edit_diarycontent);

        bt_confirm=(Button)findViewById(R.id.bt_confirm);
        bt_cancel=(Button)findViewById(R.id.bt_cancel);

    }


    public void getList(){
        String sql="insert into diary(title, content)";
        sql+=" values(?,?)";

        db.execSQL(sql, new String[]{
                edit_diarytitle.getText().toString(),
                edit_diarycontent.getText().toString()
        });


    }

    public void btnClick(View view){
        if(view.getId()==R.id.bt_confirm){
            getList();
        }else if(view.getId()==R.id.bt_cancel){
            showMsg("안내","취소하였습니다");

        }
        Intent intent=new Intent(this, DiaryListActivity.class);
        startActivity(intent);
    }
    public void showMsg(String title, String msg){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle(title).setMessage(msg).show();

    }

}
