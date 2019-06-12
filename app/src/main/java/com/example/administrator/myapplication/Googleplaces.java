package com.example.administrator.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Administrator on 5/17/2019.
 */

public interface Googleplaces {

    @GET("api/directions/json?key=AIzaSyAuDxpYcvAne5qjXX3kD2VmiQqv8IOufxU")
    Call<Example> getDistanceDuration(@Query("units")String unit,@Query("origin")String origin,@Query("destination")String destination,@Query("mode")String mode);

    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyDG0VzeOlcn16KZ1HIgblRe7AY-pWrwKVU")
    Call<Example> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius);
}
