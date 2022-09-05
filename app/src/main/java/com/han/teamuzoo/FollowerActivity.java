package com.han.teamuzoo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    // 페이징 처리
    int offset = 0;
    int count = 0;
    int limit = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower);

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