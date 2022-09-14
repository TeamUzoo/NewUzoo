package com.han.teamuzoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.han.teamuzoo.api.NetworkClient;
import com.han.teamuzoo.api.TimerApi;
import com.han.teamuzoo.api.myPlanetApi;
import com.han.teamuzoo.config.Config;
import com.han.teamuzoo.model.ResultRes;
import com.han.teamuzoo.model.TimerRes;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MyplanetActivity extends AppCompatActivity {

    ImageView iconDel;
    ImageView iconShare;
    ImageView imgPlanet;
    TextView txtDate;
    TextView txtSuc;
    TextView txtDea;

    TextView txtNoData;

    Button btnDay;
    Button btnWeek;
    Button btnMonth;
    Button btnYear;

    long dNow;
    Date dDate;
    SimpleDateFormat dFormat = new SimpleDateFormat("yyyy년 MM월 dd일 (오늘)");
    SimpleDateFormat dFormat2 = new SimpleDateFormat("MM월 dd일");
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy년 MM월");
    SimpleDateFormat yFormat = new SimpleDateFormat("yyyy년");

    int successed_count;
    int failed_count;

    List time_list;


    ArrayList<BarEntry> data;
    String label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplanet);

        iconDel = findViewById(R.id.imgBack);
        iconShare = findViewById(R.id.iconShare);
        imgPlanet = findViewById(R.id.imgPlanet);

        txtSuc = findViewById(R.id.txtSuc);
        txtDea = findViewById(R.id.txtDea);
        txtDate = findViewById(R.id.txtDate);

        txtNoData = findViewById(R.id.txtNoData);

        btnDay = findViewById(R.id.btnDay);
        btnWeek = findViewById(R.id.btnWeek);
        btnMonth = findViewById(R.id.btnMonth);
        btnYear = findViewById(R.id.btnYear);

        //초기화
        BarChart barChart = findViewById(R.id.bar_chart);

        // x 이미지를 누르면 activity 종료 = 화면 종료
        iconDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

         // 버튼 누르면: 날짜 범위 바뀌고, 리싸이클려뷰(집중시간 분포도, 총 집중시간) 분석도 바뀜, 버튼 표시 방식도 바뀜.
         // 기본값: [일]
         turnWhite(btnDay);
         data = data1();
         label = "Day Date";

        showingGraph(data,label);

         btnDay.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 // 오늘 날짜 표시
                 txtDate.setText(getTimeToday());

                 // 버튼 표시
                 turnWhite(btnDay);
                 turnBlack(btnWeek);
                 turnBlack(btnMonth);
                 turnBlack(btnYear);

                 // Todo 리싸이클러뷰 표시

                 data = data1();
                 label = "Day Date";

                 // Showing
                 txtNoData.setVisibility(View.GONE);
                 barChart.setVisibility(View.VISIBLE);
                 showingGraph(data,label);

             }
         });

         btnWeek.setOnClickListener((new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 // 날짜 변경
                 // Todo 연도, 월별 7일전과 다를 경우 판단하는 코드 필요
                 txtDate.setText(getTimeWeek() + " ~ " + getTimeToday2());

                 // 버튼 표시
                 turnBlack(btnDay);
                 turnWhite(btnWeek);
                 turnBlack(btnMonth);
                 turnBlack(btnYear);

                 //todo 리싸이클러뷰 표시

                 data = data2();
                 label = "Week Data";

                 // Showing 으로 일단 표시
                 txtNoData.setVisibility(View.GONE);
                 barChart.setVisibility(View.VISIBLE);
                 showingGraph(data,label);
             }
         }));

         btnMonth.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 // 날짜 변경
                 txtDate.setText(getTimeMonth());

                 // 버튼 표시
                 turnBlack(btnDay);
                 turnBlack(btnWeek);
                 turnWhite(btnMonth);
                 turnBlack(btnYear);

                 //todo 리싸이클러뷰 표시

                 data = data3();
                 label = "Month Data";

                 // Showing 으로 일단 표시
                 txtNoData.setVisibility(View.GONE);
                 barChart.setVisibility(View.VISIBLE);
                 showingGraph(data,label);

             }
         });

        btnYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 날짜 변경
                txtDate.setText(getTimeYear());

                //todo 리싸이클러뷰 표시

                // 버튼 표시
                turnBlack(btnDay);
                turnBlack(btnWeek);
                turnBlack(btnMonth);
                turnWhite(btnYear);

                // Showing
                barChart.setVisibility(View.GONE);
                txtNoData.setVisibility(View.VISIBLE);
                txtNoData.setText("No Data");
            }
        });


        // 내가 성공한 횟수
        Retrofit retrofit = NetworkClient.getRetrofitClient(MyplanetActivity.this);
        myPlanetApi api = retrofit.create(myPlanetApi.class);

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<ResultRes> call = api.getResultSuc("Bearer "+accessToken);

        call.enqueue((new Callback<ResultRes>() {
            @Override
            public void onResponse(Call<ResultRes> call, Response<ResultRes> response) {
                if(response.isSuccessful()){
                    successed_count = response.body().getSuccessed_count();
                    txtSuc.setText(""+successed_count );
                }
            }
            @Override
            public void onFailure(Call<ResultRes> call, Throwable t) {
            }
        }));

        // 내가 실패한 횟수
        Call<ResultRes> call2 = api.getResultFail("Bearer "+accessToken);

        call2.enqueue((new Callback<ResultRes>() {
            @Override
            public void onResponse(Call<ResultRes> call, Response<ResultRes> response) {
                if(response.isSuccessful()){
                    failed_count = response.body().getFailed_count();
                    txtDea.setText(""+failed_count );
                }
            }
            @Override
            public void onFailure(Call<ResultRes> call, Throwable t) {
            }
        }));
//        // 타임 리스트 받아오기
////        Retrofit retrofit = NetworkClient.getRetrofitClient(MyplanetActivity.this);
//        TimerApi api2 = retrofit.create(TimerApi.class);
//
////        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
////        String accessToken = sp.getString("accessToken", "");
//
//        Call<TimerRes> call3 = api2.getTotalTimeToday("Bearer "+accessToken);
//
//        call3.enqueue((new Callback<TimerRes>() {
//            @Override
//            public void onResponse(Call<TimerRes> call, Response<TimerRes> response) {
//                if(response.isSuccessful()){
//
//                   time_list = response.body().getTime_list();
//
//                }
//            }
//            @Override
//            public void onFailure(Call<TimerRes> call, Throwable t) {
//            }
//        }));


        Log.i("check","time_list : " +time_list );

        // Todo 그래프 데이터 불러오는 거 완성시켜야함.

        // Showing 으로 일단 표시
        // 그래프 사용 코드




    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("check", "onPause 실행됨");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("check", "onStop 실행됨");
    }

// 그래프 showing
    private void showingGraph(ArrayList<BarEntry> data,String label){
        BarChart barChart = findViewById(R.id.bar_chart);

        //1. 데이터 생성
        BarDataSet barDataSet1 = new BarDataSet(data, label);

        //2. 바 데이터 생성
        BarData barData = new BarData();

        //3. 바 데이터에 데이터셋 추가
        barData.addDataSet(barDataSet1);

        //4. 바차트에 바데이터 등록
        barChart.setData(barData);


    }

    // 그래프 함수(일)
    private ArrayList<BarEntry> data1(){
//
        ArrayList<BarEntry> dataList = new ArrayList<>();

        Log.i("check","그래프 함수 실행됨");
        ArrayList<Integer> showingList= new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24));

//        List<int[]> test_list = Arrays.asList(testlist);

        // 그래프 만들기
        for (int i = 0; i < 24; i++) {
             dataList.add(new BarEntry(i,showingList.get(i)));
//            dataList.add(new BarEntry(i, time_list.get(i)));
        }

        return dataList;
    }

    // 그래프 함수(주)
    private ArrayList<BarEntry> data2(){
        ArrayList<BarEntry> dataList = new ArrayList<>();
        Log.i("check","그래프 함수 실행됨");
        ArrayList<Integer> showingList= new ArrayList<>(Arrays.asList(100,200,300,400,500,600,700));

        // 그래프 만들기
        for (int i = 0; i < 7; i++) {
            dataList.add(new BarEntry(i,showingList.get(i)));}
        return dataList;
    }

    // 그래프 함수(월)
    private ArrayList<BarEntry> data3(){
        ArrayList<BarEntry> dataList = new ArrayList<>();
        Log.i("check","그래프 함수 실행됨");
        ArrayList<Integer> showingList= new ArrayList<>(Arrays.asList(1000,2000,3000,4000,5000,600,700,800,90,1000,110,1200));

        // 그래프 만들기
        for (int i = 0; i < 12; i++) {
            dataList.add(new BarEntry(i,showingList.get(i)));}
        return dataList;
    }

    // 버튼 눌렀을 때, 배경 하얀색 되게 설정하는 함수
    private Button turnWhite(Button btn){

        btn.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        btn.setTextColor(Color.parseColor("#FF000000"));

        return btn;
    }

    // 버튼 눌렀을 때, 배경 검게 되게 설정하는 함수
    private Button turnBlack(Button btn){

//        btn.setBa
        btn.setBackgroundColor(Color.parseColor("#00FF0000"));
        btn.setTextColor(Color.parseColor("#FFFFFFFF"));

        return btn;
    }

//    // 버튼 눌렀을 때, 선택된 버튼만 하얗게 되는 함수
//    private Button chosenButton(Button btn1,Button btn2,Button btn3,Button btn4){
//
//        //눌린 버튼: btn1
//        turnWhite(btn1);
//
//        //눌리지 않은 버튼: btn2,btn3,btn4
//        turnBlack(btn2);
//        turnBlack(btn3);
//        turnBlack(btn4);
//
//        Button[] array = new Button[btn1, btn2, btn3,btn4];
//        return array;
//
//    }

    // 오늘 날짜 표시하는 함수(년,월,일)
    private String getTimeToday(){
        dNow = System.currentTimeMillis();
        dDate = new Date(dNow);
        return dFormat.format(dDate);
    }

    // 오늘 날짜 표시하는 함수2(월,일)
    private String getTimeToday2(){
        dNow = System.currentTimeMillis();
        dDate = new Date(dNow);
        return dFormat2.format(dDate);
    }

    // 이번 달 표시하는 함수3(월)
    private String getTimeMonth(){
        dNow = System.currentTimeMillis();
        dDate = new Date(dNow);
        return mFormat.format(dDate);
    }

    // 올해 표시하는 함수3(월)
    private String getTimeYear(){
        dNow = System.currentTimeMillis();
        dDate = new Date(dNow);
        return yFormat.format(dDate);
    }


    // 일주일 날짜 표시하는 함수
    private String getTimeWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy년MM월dd일");
        System.out.println("current: " + df.format(cal.getTime()));

        cal.add(Calendar.DATE, -7);
        System.out.println("after: " + df.format(cal.getTime()));


        return df.format(cal.getTime());
    }
}


