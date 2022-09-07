package com.han.teamuzoo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.han.teamuzoo.adapter.FollowerAdapter;
import com.han.teamuzoo.api.FollowerApi;
import com.han.teamuzoo.api.NetworkClient;
import com.han.teamuzoo.api.ShopApi;
import com.han.teamuzoo.config.Config;
import com.han.teamuzoo.model.Follower;
import com.han.teamuzoo.model.FollowerList;
import com.han.teamuzoo.model.ShopList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FollowerActivity extends AppCompatActivity {

    FollowerAdapter adapter;
    ArrayList<Follower> followerList = new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton fab;

    // 페이징 처리
    int offset = 0;
    int count = 0;
    int limit = 25;

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;

    FloatingActionButton fb_add_btn;
    FloatingActionButton fb_edit_btn;
    FloatingActionButton fb_image_btn;

    private boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);

        // 플로팅 버튼
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);

        // 추가 버튼
        fb_add_btn = findViewById(R.id.fb_add_btn);
        fb_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });

        // 쓰기 버튼
        fb_edit_btn = findViewById(R.id.fb_edit_btn);
        fb_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"edit btn clicked",Toast.LENGTH_SHORT).show();
            }
        });

        // 이미지 버튼
        fb_image_btn = findViewById(R.id.fb_image_btn);
        fb_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"image btn clicked",Toast.LENGTH_SHORT).show();
            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(FollowerActivity.this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();


                if (lastPosition + 1 == totalCount) {
                    if (count == limit) {
                        // 데이터 더 불러오기.
                        addNetworkData();
                    }

                }
            }
        });
    }

    // 플로팅 버튼
    // 클릭 이벤트
    private void onAddButtonClicked(){
        setVisibility(clicked);
        setAnimation(clicked);
        clicked = !clicked;
    }

    // 보여짐 사라짐
    // @param clicked 클릭여부
    private void setVisibility(boolean clicked){

        if(!clicked){
            // 보여진다
            fb_edit_btn.setVisibility(fb_add_btn.VISIBLE);
            fb_image_btn.setVisibility(fb_image_btn.VISIBLE);
        } else {
            // 숨긴다
            fb_edit_btn.setVisibility(fb_add_btn.INVISIBLE);
            fb_image_btn.setVisibility(fb_image_btn.INVISIBLE);
        }
    }

    // 애니메이션 효과
    // @param clicked 클릭여부
    private void setAnimation(boolean clicked){

        if(!clicked){
            fb_edit_btn.startAnimation(fromBottom);
            fb_image_btn.startAnimation(fromBottom);
            fb_add_btn.startAnimation(rotateOpen);
        } else {
            fb_edit_btn.startAnimation(toBottom);
            fb_image_btn.startAnimation(toBottom);
            fb_add_btn.startAnimation(rotateClose);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNetworkData();
    }


    // 데이터를 처음 가져올때 함수
    private void getNetworkData() {
        followerList.clear();

        Retrofit retrofit = NetworkClient.getRetrofitClient(FollowerActivity.this);
        FollowerApi api = retrofit.create(FollowerApi.class);

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<FollowerList> call = api.getFollowerList("Bearer " + accessToken,
                limit,
                offset);

        call.enqueue(new Callback<FollowerList>() {
            @Override
            public void onResponse(Call<FollowerList> call, Response<FollowerList> response) {

                if (response.isSuccessful()) {
                    count = response.body().getCount();
                    followerList.addAll(response.body().getItems());
                    offset = offset + count;
                    adapter = new FollowerAdapter(FollowerActivity.this, followerList);
                    recyclerView.setAdapter(adapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<FollowerList> call, Throwable t) {

            }
        });

    }

    private void addNetworkData() {


        Retrofit retrofit = NetworkClient.getRetrofitClient(FollowerActivity.this);
        FollowerApi api = retrofit.create(FollowerApi.class);

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<FollowerList> call = api.getFollowerList("Bearer " + accessToken,
                limit,
                offset);

        call.enqueue(new Callback<FollowerList>() {
            @Override
            public void onResponse(Call<FollowerList> call, Response<FollowerList> response) {


                if (response.isSuccessful()) {

                    count = response.body().getCount();
                    followerList.addAll(response.body().getItems());

                    offset = offset + count;

                    adapter.notifyDataSetChanged();
                } else {

                }
            }

            @Override
            public void onFailure(Call<FollowerList> call, Throwable t) {
            }
        });


    }
}