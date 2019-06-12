package com.example.administrator.myapplication;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 6/3/2019.
 */

public class Notification {
    @SerializedName("title")
    String title;

    @SerializedName("Message")
    String message;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
