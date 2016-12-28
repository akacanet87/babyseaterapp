package com.sds.study.babyseaterapp.school;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sds.study.babyseaterapp.MainActivity;
import com.sds.study.babyseaterapp.R;
import com.sds.study.babyseaterapp.calendar.schedule.Schedule;

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

import static com.sds.study.babyseaterapp.calendar.CalendarActivity.db;

/**
 * Created by CANET on 2016-12-10.
 */

public class SchoolActivity extends AppCompatActivity implements OnMapReadyCallback{

    SupportMapFragment map;
    GoogleMap googleMap;
    LatLng myPoint;
    Intent intent;

    ArrayList<LatLng> pointList;

    String TAG;
    double lat;
    double lng;

    SchoolActivity schoolActivity;
    MapAsync mapAsync;
    GpsInfo gps;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        schoolActivity = this;
        TAG = this.getClass().getName() + "/Canet";
        setContentView(R.layout.activity_school);

        initGps();

        Log.d(TAG, "데이터 요청");

    }

    @Override
    public void onMapReady(GoogleMap googleMap){

        this.googleMap = googleMap;

        mapAsync = new MapAsync(googleMap);
        mapAsync.execute();

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPoint, 15));

    }

    public void goBack(){

        intent = new Intent(this, MainActivity.class);

        startActivity(intent);

        finish();

    }

    public void initGps(){

        gps = new GpsInfo(schoolActivity);
        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {

            lat = gps.getLatitude();
            lng = gps.getLongitude();

            Toast.makeText(
                    getApplicationContext(),
                    "당신의 위치 - \n위도: " + lat + "\n경도: " + lng,
                    Toast.LENGTH_LONG).show();
        } else {
            // GPS 를 사용할수 없으므로
            gps.showSettingsAlert();
        }

        myPoint = new LatLng(lat, lng);

        map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);

    }

    public void getList() {

        String sql = "select * from schedule";
        Cursor rs = db.rawQuery(sql, null);

        pointList.removeAll(pointList);

        while (rs.moveToNext()) {

            double lat = rs.getDouble(rs.getColumnIndex("lat"));
            double lng = rs.getDouble(rs.getColumnIndex("lng"));

            LatLng newPoint = new LatLng(lat, lng);

            pointList.add(newPoint);

        }

    }

    public void refreshGps(){

        for( int i=0 ; i<pointList.size() ; i++ ){

            LatLng gathered_point = pointList.get(i);

            googleMap.addMarker(new MarkerOptions().title("내 마커").position(gathered_point));

        }

    }

    public void btnSchoolClick(View view){

        switch(view.getId()){

            case R.id.btn_school_to_main : goBack(); break;
            case R.id.btn_refresh_gps : refreshGps(); break;


        }

    }

}
