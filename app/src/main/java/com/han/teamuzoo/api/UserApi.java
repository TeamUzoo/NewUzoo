package com.han.teamuzoo.api;

import com.han.teamuzoo.model.User;
import com.han.teamuzoo.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {

    @POST("/users/register")
    Call<UserRes> register(@Body User user);

    @POST("/users/login")
    Call<UserRes> login(@Body User user);

    // 로그아웃 API
    @POST("/users/logout")
    Call<UserRes> logout(@Header("Authorization") String token);


}
