package com.resto.sugarbox.ifc;

import com.resto.sugarbox.model.ZomatoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;



public interface ZomatoDataService {
    //using location api
    @Headers("user_key: 327e75c31ca03dbb55cbabe4257acfa9")
    @GET("api/v2.1/search")
    Call<ZomatoResponse> getZomatoResponse(@Query("lat") String latittude,
                                           @Query("lon") String longtitude,
                                           @Query("count") int count);

    //using search menu
    @Headers("user_key: 327e75c31ca03dbb55cbabe4257acfa9")
    @GET("api/v2.1/search")
    Call<ZomatoResponse> getSearchItem(@Query("q") String name,
                                           @Query("count") int count);
}
