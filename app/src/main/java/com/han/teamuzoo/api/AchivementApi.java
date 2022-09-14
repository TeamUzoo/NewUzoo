package com.han.teamuzoo.api;

import com.han.teamuzoo.model.AchiveRes;
import com.han.teamuzoo.model.GetCoin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AchivementApi {

    // 코인 가져오는 APi
    @POST("/addcoin")
    Call<AchiveRes> addCoin(@Header("Authorization") String token,
                            @Body GetCoin getCoin);

}
