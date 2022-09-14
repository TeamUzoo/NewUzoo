package com.han.teamuzoo.api;

import com.han.teamuzoo.model.MainTimer;
import com.han.teamuzoo.model.MainTimerRes;
import com.han.teamuzoo.model.TimerRes;

import java.util.Timer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TimerApi {

    @GET("/gettime/today")
    Call<TimerRes> getTotalTimeToday(@Header("Authorization") String token);

    @POST("/timer/start")
    Call<MainTimerRes> startTimer(@Header("Authorization") String token,
                                  @Body MainTimer timer);

    @DELETE("/timer/delete/{timerId}")
    Call<MainTimerRes> deleteTimer(@Header("Authorization") String token,
                                   @Path("timerId") int timerId);

    @PUT("/timer/fail/{timerId}")
    Call<MainTimerRes> failTimer(@Header("Authorization") String token,
                                 @Path("timerId") int timerId);

    @GET("/timer/list")
    Call<MainTimerRes> timerList(@Header("Authorization") String token);



}


