package com.han.teamuzoo.api;

import com.han.teamuzoo.model.AchiveRes;
import com.han.teamuzoo.model.GetCoin;
import com.han.teamuzoo.model.User;
import com.han.teamuzoo.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AchivementApi {

    // 업적 APi

    // 코인 추가 API
    @POST("/addcoin")
    Call<AchiveRes> addCoin(@Header("Authorization") String token,
                            @Body GetCoin getCoin);




}
