package com.sds.study.babyseaterapp;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.sds.study.babyseaterapp.calendar.CalendarActivity;
import com.sds.study.babyseaterapp.school.Kindergarten;
import com.sds.study.babyseaterapp.school.MapAsync;
import com.sds.study.babyseaterapp.school.SchoolActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    ImageButton btn_babyseater, btn_diary, btn_school;
    MapAsync mapAsync;

    String TAG;
    static final int OVERRAY_PERMISSION=1220;
    static final int COARSE_LOCATION_PERMISSION=1111;
    boolean babyseater_flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        TAG = getClass().getName()+"/Canet";

        setContentView(R.layout.activity_main);

        checkPermission( android.Manifest.permission.ACCESS_COARSE_LOCATION, COARSE_LOCATION_PERMISSION );
        checkPermission( Settings.ACTION_MANAGE_OVERLAY_PERMISSION, OVERRAY_PERMISSION );

        btn_babyseater = (ImageButton) findViewById(R.id.btn_babyseater);
        btn_diary = (ImageButton) findViewById(R.id.btn_calendar_site);
        btn_school = (ImageButton) findViewById(R.id.btn_school_site);

    }

    public void checkPermission( String manifestPermission, int staticPermission ){

        //  4.xx 초과 버전에서 필요한 Location 권한 체크 메서드
        int checkPermission = ContextCompat.checkSelfPermission(this, manifestPermission);

        if( checkPermission== PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, new String[]{

                    manifestPermission

            }, staticPermission );

        }else{

            if( staticPermission==COARSE_LOCATION_PERMISSION){

            }else if( staticPermission == OVERRAY_PERMISSION ){



            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        switch( requestCode ){

            case COARSE_LOCATION_PERMISSION :

                if(permissions.length>0 && grantResults[0]==PackageManager.PERMISSION_DENIED){

                    showAlertMsg( "Warning", "앱을 사용하기 위해 권한이 필요합니다." );

                }

                break;

            case OVERRAY_PERMISSION :

                if(permissions.length>0 && grantResults[0]==PackageManager.PERMISSION_DENIED){

                    //showAlertMsg( "Warning", "앱을 사용하기 위해 권한이 필요합니다." );

                }

                break;

        }

    }

    public void showAlertMsg(String title, String msg ){

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(title).setMessage(msg).show();

    }



    public void babySeater(){

        if( babyseater_flag ){

            Log.d( TAG, "BabySeater 꺼짐" );
            btn_babyseater.setImageResource(R.drawable.switch_off);
            babyseater_flag = !babyseater_flag;

        }else{

            Log.d( TAG, "BabySeater 켜짐" );
            btn_babyseater.setImageResource(R.drawable.switch_on);
            babyseater_flag = !babyseater_flag;

        }

    }

    public void showCalendarSite(){

        Log.d( TAG, "Diary버튼 클릭" );

        Intent intent = new Intent(this, CalendarActivity.class);

        startActivity(intent);

        finish();

    }

    public void showSchoolSite(){

        Log.d( TAG, "School버튼 클릭" );

        Intent intent = new Intent(this, SchoolActivity.class);

        startActivity(intent);

        finish();

    }

    public void btnClick(View view){

        switch(view.getId()){

            case R.id.btn_babyseater : babySeater(); break;
            case R.id.btn_calendar_site : showCalendarSite(); break;
            case R.id.btn_school_site : showSchoolSite(); break;

        }

    }

}
