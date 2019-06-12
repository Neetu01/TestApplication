package com.example.administrator.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PointsParser extends AsyncTask<String,Integer,List<List<HashMap<String,String>>>> {
    TaskLoadedCallback taskLoadedCallback;
    String drivingMode="driving";

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
        JSONObject jsonObject = null;
        List<List<HashMap<String,String>>> routes=null;

        try {
            JSONObject jsonObject1=new JSONObject(strings[0]);
         //   Log.d("mylog",strings[0].toString());
            DataParser dataParser=new DataParser();
//            Log.d("mylog",routes.toString());
            routes=dataParser.parse(jsonObject1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return routes;
    }
    public PointsParser(Context context,String directionMode)
    {
        this.taskLoadedCallback=(TaskLoadedCallback)context;
        this.drivingMode=directionMode;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
        ArrayList<LatLng> points;
        PolylineOptions lineOptions=null;
        for(int i=0;i<lists.size();i++)
        {
            points=new ArrayList<>();
            lineOptions=new PolylineOptions();
            List<HashMap<String,String>> path=lists.get(i);
            for(int j=0;j<path.size();j++)
            {
                HashMap<String, String> point = path.get(j);
                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                double  dis=Double.parseDouble(point.get("distance"));
                LatLng position = new LatLng(lat, lng);
                points.add(position);
            }
            lineOptions.addAll(points);
            if (drivingMode.equalsIgnoreCase("walking")) {
                lineOptions.width(10);
                lineOptions.color(Color.MAGENTA);
            } else {
                lineOptions.width(20);
                lineOptions.color(Color.BLUE);
            }
            Log.d("mylog", "onPostExecute lineoptions decoded");
        }

        // Drawing polyline in the Google Map for the i-th route
        if (lineOptions != null) {
            //mMap.addPolyline(lineOptions);
            taskLoadedCallback.onTaskDone(lineOptions);

        } else {
            Log.d("mylog", "without Polylines drawn");
        }
        }
    }

