package com.han.teamuzoo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.han.teamuzoo.api.NetworkClient;
import com.han.teamuzoo.api.myPlanetApi;
import com.han.teamuzoo.config.Config;
import com.han.teamuzoo.model.ResultRes;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

    int successed_count;
    int failed_count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myplanet);

        iconDel = findViewById(R.id.iconDel);
        iconShare = findViewById(R.id.iconShare);
        imgPlanet = findViewById(R.id.imgPlanet);

        toolbar = findViewById(R.id.toolbar);

        txtSuc = findViewById(R.id.txtSuc);
        txtDea = findViewById(R.id.txtDea);
        txtDate = findViewById(R.id.txtDate);

        // x 이미지를 누르면 activity 종료 = 화면 종료
        iconDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 오늘 날짜 표시
        txtDate.setText(getTime());

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

        //txtDea.setText(// Todo 채워넣기 );

//        Toolbar toolbar = (Toolbar) findViewById(R.id.chtl_toolbar);
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

    // 오늘 날짜 표시하는 함수
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}

