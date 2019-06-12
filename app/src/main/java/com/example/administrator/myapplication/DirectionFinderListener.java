package com.example.administrator.myapplication;

import java.util.List;

import okhttp3.Route;

/**
 * Created by Administrator on 5/19/2019.
 */

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);

}
