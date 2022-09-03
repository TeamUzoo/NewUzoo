package com.han.teamuzoo.api;

import com.han.teamuzoo.model.ResultRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface myPlanetApi {

    @GET("/result/success")
    Call<ResultRes> getResultSuc(@Header("Authorization") String token);

    @GET("/result/fail")
    Call<ResultRes> getResultFail(@Header("Authorization") String token);
}
