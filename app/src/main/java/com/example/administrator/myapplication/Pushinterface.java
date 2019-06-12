package com.example.administrator.myapplication;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Pushinterface {

    @POST("sendnotification.php")
    Call<List<Notification>> getnotiifcation(@Query("Title")String title,@Query("Message")String message);

}
