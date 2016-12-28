package com.sds.study.babyseaterapp.school;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
 * Created by CANET on 2016-12-16.
 */

public class MapAsync extends AsyncTask<String, Void, String>{

    URL url;
    HttpURLConnection con;
    GoogleMap googleMap;

    String json;                        //json String형
    JSONObject object;             //json JsonObject형
    JSONArray array;

    double mylat;
    double mylng;
    double range=0.08;


    ArrayList<Kindergarten> list_kindergarten;

    String TAG;

    public MapAsync(GoogleMap googleMap){

        TAG = this.getClass().getName() + "/Canet";
        this.googleMap = googleMap;

    }

    @Override
    protected void onPreExecute(){

        super.onPreExecute();

        list_kindergarten = new ArrayList<>();

    }

    @Override
    protected String doInBackground(String... strings){

        Log.d(TAG, "doInBackground 실행");

        json = getJson();
        //parseJson(json);

        return json;
    }

    @Override
    protected void onProgressUpdate(Void... values){

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s){

        Log.d(TAG, "onPostExecute 실행");
        Log.d(TAG, s);

        parseJson(s);

        super.onPostExecute(s);
    }

    public String getJson(){

        String key = "zNUxHwqZV0QfCgDkhHtNKqyPfsEEHmNfp0%2FO0zFHfmg1sujkxk%2FJVxf4qml60BaH219L795Fhlwx7vuiGAFahg%3D%3D";
        String api_address = "http://api.data.go.kr/openapi/0c9e6948-e327-404b-89bf-2506d4684c1c";

        String string_url = api_address + "?serviceKey=" + key + "&s_page=1" + "&s_list=50000" + "&type=json" + "&numOfRows=1" + "&pageNo=1";

        StringBuffer sb = new StringBuffer();
        int count = 0;

        try{
            Log.d(TAG, "JSON 요청 주소 " + string_url);
            url = new URL(string_url);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");

            int responseCode = urlConn.getResponseCode();
            Log.d(TAG, Integer.toString(responseCode));
            if(responseCode != HttpURLConnection.HTTP_OK) return null;

            BufferedReader buffR = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "euc-kr"));
            String input = null;
            sb = new StringBuffer();

            while((input = buffR.readLine()) != null){
                sb.append(input);
                count++;
            }
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return sb.toString();
    }

    public void parseJson(String json){

        Log.d(TAG, "파싱시작");

        try{
            Log.d(TAG, "트라이문 진입");
            array = new JSONArray(json);

            Log.d(TAG, "포문 전");
            for(int i = 0; i < array.length(); i++){
                if(array.get(i) == null){
                    break;
                }
                JSONObject obj_kindergarten = array.getJSONObject(i);

                String sql="insert into school(addr,school_name,lat,lng,schoolbus,max_stu_num,teacher_num,call_num,cctv_num)";
                sql+=" values(?,?,?,?,?,?,?,?,?)";

                String addr=(obj_kindergarten.getString("소재지도로명주소"));
                String school_name=(obj_kindergarten.getString("어린이집명"));
                String lat=Double.toString(obj_kindergarten.getDouble("위도"));
                String lng=Double.toString(obj_kindergarten.getDouble("경도"));
                String schoolbus=(obj_kindergarten.getString("통학차량운영여부"));
                String max_stu_num=Integer.toString(obj_kindergarten.getInt("정원수"));
                String teacher_num=Integer.toString(obj_kindergarten.getInt("보육교직원수"));
                String call_num=(obj_kindergarten.getString("어린이집전화번호"));
                String cctv_num=Integer.toString(obj_kindergarten.getInt("CCTV설치수"));

                db.execSQL(sql,new String[]{addr,school_name,lat,lng,schoolbus,max_stu_num,teacher_num,call_num,cctv_num});

            }

            Log.d(TAG, "list_kindergarten 길이 :" + Integer.toString(list_kindergarten.size()));
            Log.d(TAG, "array의 길이 :" + Integer.toString(array.length()));

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

}
