package com.example.administrator.myapplication;

/**
 * Created by Administrator on 5/17/2019.
 */

public class Common {
    private static final String GOOGLE_API_URl="https://maps.googleapis.com/";
    public static Googleplaces getGoogleAPIService()
    {
        return RetrofitClient.getClient(GOOGLE_API_URl).create(Googleplaces.class);
    }
}
