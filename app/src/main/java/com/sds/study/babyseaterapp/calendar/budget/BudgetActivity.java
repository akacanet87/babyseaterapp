package com.sds.study.babyseaterapp.calendar.budget;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sds.study.babyseaterapp.R;

public class BudgetActivity extends AppCompatActivity{
    final static int REQUEST_RECEIVE_PERMISSION=1;
    ItemInputLayout itemInputLayout;
    static BudgetActivity budgetActivity;
    int x=1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        budgetActivity=this;
        setContentView(R.layout.activity_budget);

        chechAccessPermission();
        itemInputLayout = ItemInputLayout.inputview;
        Intent intent=getIntent();
        BudgetDTO budgetDTO =intent.getParcelableExtra("budgetDTO");
        itemInputLayout.budgetDTO = budgetDTO;
    }

    public void chechAccessPermission(){
        int receive_sms_permission= ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if(receive_sms_permission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.RECEIVE_SMS
            },REQUEST_RECEIVE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_RECEIVE_PERMISSION:
                if(permissions.length>0 && grantResults[0]==PackageManager.PERMISSION_DENIED){
                    showMsg("안내","권한을 부여하지 않으면 일부 기능을 사용 할 수 없습니다.");
                }
        }
    }

    public void btnClick(View view){
        itemInputLayout.btnClick(view);
    }

    public void showMsg(String title,String msg){
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle(title).setMessage(msg).show();
    }
}

