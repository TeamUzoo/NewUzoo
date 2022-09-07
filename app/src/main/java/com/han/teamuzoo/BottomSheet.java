package com.han.teamuzoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.han.teamuzoo.adapter.FollowerAdapter;
import com.han.teamuzoo.adapter.MyPetAdapter;
import com.han.teamuzoo.api.FollowerApi;
import com.han.teamuzoo.api.MyPetApi;
import com.han.teamuzoo.api.NetworkClient;
import com.han.teamuzoo.api.myPlanetApi;
import com.han.teamuzoo.config.Config;
import com.han.teamuzoo.model.FollowerList;
import com.han.teamuzoo.model.MyPet;
import com.han.teamuzoo.model.MyPetList;
import com.han.teamuzoo.model.ResultRes;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BottomSheet extends AppCompatActivity {

    String accessToken;

    RecyclerView recyclerView;
    MyPetAdapter adapter;
    ArrayList<MyPet> myPetList = new ArrayList<>();

    RoundedImageView imagePet;

    int offset = 0;
    int count = 0;
    int limit = 25;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);

        Log.i("aaa","바텀시트 oncreate");

        recyclerView = findViewById(R.id.mprecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(BottomSheet.this));
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
        myPetList.clear();

        Retrofit retrofit = NetworkClient.getRetrofitClient(BottomSheet.this);
        MyPetApi api = retrofit.create(MyPetApi.class);

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<MyPetList> call = api.getMyPetList("Bearer " + accessToken,
                limit,
                offset);

        call.enqueue(new Callback<MyPetList>() {
            @Override
            public void onResponse(Call<MyPetList> call, Response<MyPetList> response) {

                if (response.isSuccessful()) {
                    count = response.body().getCount();
                    myPetList.addAll(response.body().getPets());
                    offset = offset + count;
                    adapter = new MyPetAdapter(BottomSheet.this, myPetList);
                    recyclerView.setAdapter(adapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<MyPetList> call, Throwable t) {

            }
        });

    }

    private void addNetworkData() {


        Retrofit retrofit = NetworkClient.getRetrofitClient(BottomSheet.this);
        MyPetApi api = retrofit.create(MyPetApi.class);

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<MyPetList> call = api.getMyPetList("Bearer " + accessToken,
                limit,
                offset);

        call.enqueue(new Callback<MyPetList>() {
            @Override
            public void onResponse(Call<MyPetList> call, Response<MyPetList> response) {


                if (response.isSuccessful()) {

                    count = response.body().getCount();
                    myPetList.addAll(response.body().getPets());

                    offset = offset + count;

                    adapter.notifyDataSetChanged();
                } else {

                }
            }

            @Override
            public void onFailure(Call<MyPetList> call, Throwable t) {
            }
        });


    }
}