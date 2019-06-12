package com.example.administrator.myapplication;

import android.util.Log;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;



public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token=FirebaseInstanceId.getInstance().getToken();

        Log.d("MyRefreshedToken",token);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.edit().putString("Token", token).apply();

    }
}
