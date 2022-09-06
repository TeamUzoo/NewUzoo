package com.han.teamuzoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.han.teamuzoo.api.NetworkClient;
import com.han.teamuzoo.api.myPlanetApi;
import com.han.teamuzoo.config.Config;
import com.han.teamuzoo.model.ResultRes;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MyplanetActivity extends AppCompatActivity {

    ImageView iconDel;
    ImageView iconShare;
    ImageView imgPlanet;
    Toolbar toolbar;
    TextView txtDate;
    TextView txtSuc;
    TextView txtDea;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplanet);

        iconDel = findViewById(R.id.iconDel);
        iconShare = findViewById(R.id.iconShare);
        imgPlanet = findViewById(R.id.imgPlanet);

        txtSuc = findViewById(R.id.txtSuc);
        txtDea = findViewById(R.id.txtDea);
        txtDate = findViewById(R.id.txtDate);

        btnDay = findViewById(R.id.btnDay);
        btnWeek = findViewById(R.id.btnWeek);
        btnMonth = findViewById(R.id.btnMonth);
        btnYear = findViewById(R.id.btnYear);

        // x 이미지를 누르면 activity 종료 = 화면 종료
        iconDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

         // 버튼 누르면: 날짜 범위 바뀌고, 리싸이클려뷰(집중시간 분포도, 총 집중시간) 분석도 바뀜, 버튼 표시 방식도 바뀜.
         // 기본값: [일]
         turnWhite(btnDay);

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
            }
        });



//      툴바 기능 구현
        // 추가된 소스, Toolbar 를 생성한다.
//        toolbar = (Toolbar) findViewById(R.id.chtl_toolbar);
//        setSupportActionBar(toolbar);
//        class Problems extends AppCompatActivity {
//            @Override
//            protected void onCreate(Bundle savedInstanceState) {
//                super.onCreate(savedInstanceState);
//                setContentView(R.layout.activity_myplanet);
//                LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View v = inflator.inflate(R.layout.toolbar_myplanet, null);
//                getSupportActionBar().setCustomView(v);
//            }
//        }
    }

    // 버튼 눌렀을 때, 배경 하얀색 되게 설정하는 함수
    private Button turnWhite(Button btn){

        btn.setBackgroundColor(Color.parseColor("#ffffff"));
        btn.setTextColor(Color.parseColor("#404040"));

        return btn;
    }

    // 버튼 눌렀을 때, 배경 검게 되게 설정하는 함수
    private Button turnBlack(Button btn){

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


