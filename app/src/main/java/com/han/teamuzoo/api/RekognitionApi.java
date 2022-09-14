package com.han.teamuzoo.api;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import com.han.teamuzoo.model.RekogRes;
import retrofit2.http.Header;
import retrofit2.http.Part;

public interface RekognitionApi {

    @Multipart
    @POST("/object_detection")
    Call<RekogRes> rekognition(@Header("Authorization") String token,
                               @Part MultipartBody.Part photo);

}
