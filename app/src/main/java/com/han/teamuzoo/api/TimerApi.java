package com.han.teamuzoo.api;

import com.han.teamuzoo.model.FollowerList;
import com.han.teamuzoo.model.TimerRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface TimerApi {

    @GET("/gettime/today")
    Call<TimerRes> getTotalTimeToday(@Header("Authorization") String token);
}
