package com.han.teamuzoo.api;

import com.han.teamuzoo.model.FollowerList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface FollowerApi {

    @GET("/follow/list")
    Call<FollowerList> getFollowerList(@Header("Authorization") String token,
                                       @Query("limit") int limit,
                                       @Query("offset") int offset);



}
