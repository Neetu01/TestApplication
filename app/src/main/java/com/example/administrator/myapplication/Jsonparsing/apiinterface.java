package com.example.administrator.myapplication.Jsonparsing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 6/6/2019.
 */

public interface apiinterface {

    @GET("retrofit/json_object.json")
    Call<Jsonmodel> getMyJSON();

    @GET("api/users")
    Call<Pagemodel> getpagedetail();
}
