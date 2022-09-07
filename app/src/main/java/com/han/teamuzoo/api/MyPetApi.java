package com.han.teamuzoo.api;

import com.han.teamuzoo.model.MyPetList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MyPetApi {

    @GET("/myanimals")
    Call<MyPetList> getMyPetList(@Header("Authorization") String token,
                                 @Query("limit") int limit,
                                 @Query("offset") int offset);
}
