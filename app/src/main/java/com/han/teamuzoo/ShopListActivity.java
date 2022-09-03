package com.han.teamuzoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.han.teamuzoo.R;
import com.han.teamuzoo.adapter.ShopAdapter;
import com.han.teamuzoo.api.NetworkClient;
import com.han.teamuzoo.api.ShopApi;
import com.han.teamuzoo.config.Config;
import com.han.teamuzoo.model.Pet;
import com.han.teamuzoo.model.Shop;
import com.han.teamuzoo.model.ShopList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ShopListActivity extends AppCompatActivity {

    String accessToken;

    ProgressBar progressBar;
    RecyclerView recyclerView;
    ShopAdapter adapter;
    ArrayList<Shop> shopList = new ArrayList<>();

    ImageView iconDel;
    ImageView imgBanner;
    TextView txtTitle;


    // 페이징에 필요한 멤버변수
    int offset = 0;
    int count = 0;
    int limit = 25;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

//        pet = (Pet) getIntent().getSerializableExtra("pet");
//
//        getSupportActionBar().setTitle( pet.getPetName() + " 동물 이름");


        iconDel = findViewById(R.id.iconDel);
        imgBanner = findViewById(R.id.imgBanner);
        txtTitle = findViewById(R.id.txtTitle);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShopListActivity.this));
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
        shopList.clear();
        count = 0;
        offset = 0;

        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(ShopListActivity.this);
        ShopApi api = retrofit.create(ShopApi.class);


        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<ShopList> call = api.getShopList();

        call.enqueue(new Callback<ShopList>() {
            @Override
            public void onResponse(Call<ShopList> call, Response<ShopList> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    count = response.body().getCount();

                    shopList.addAll(response.body().getItems());

                    offset = offset + count;

                    adapter = new ShopAdapter(ShopListActivity.this, shopList);

                    recyclerView.setAdapter(adapter);

                } else {

                }
            }

            @Override
            public void onFailure(Call<ShopList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void addNetworkData() {

        // 데이터가져오는것을 표현하기 위해서 프로그래스바를 표시
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(ShopListActivity.this);
        ShopApi api = retrofit.create(ShopApi.class);

        SharedPreferences sp = getApplication().getSharedPreferences(Config.PREFERENCES_NAME, MODE_PRIVATE);
        String accessToken = sp.getString("accessToken", "");

        Call<ShopList> call = api.getShopList();

        call.enqueue(new Callback<ShopList>() {
            @Override
            public void onResponse(Call<ShopList> call, Response<ShopList> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                   count = response.body().getCount();
                   shopList.addAll(response.body().getItems());

                    offset = offset + count;

                    adapter.notifyDataSetChanged();
                } else {

                }
            }

            @Override
            public void onFailure(Call<ShopList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }
}

