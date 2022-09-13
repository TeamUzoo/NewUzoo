package com.han.teamuzoo.api;

import com.han.teamuzoo.model.ShopList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ShopApi {


    // 상점 목록 불러오는 API
    @GET("/storeanimals")
    Call<ShopList> getShopList();




}
