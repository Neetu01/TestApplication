package com.example.administrator.myapplication;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Directions extends FragmentActivity implements OnMapReadyCallback  {

    private GoogleMap mMap;
    String origin="";
    LatLng latLng,des;
    String destination="";
    MarkerOptions markerOptions1,markerOptions2;
    Button driving,walking;
    Polyline line;
    TextView distance;
    ArrayList<LatLng> MarkerPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions2);
        driving=findViewById(R.id.driving);
        walking=findViewById(R.id.walk);
        distance=findViewById(R.id.distance);
        MarkerPoints = new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        PlaceAutocompleteFragment placeAutocompleteFragment=(PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.origin);
        placeAutocompleteFragment.setHint("Your Location");
        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                origin= place.getName().toString();
                Toast.makeText(Directions.this, ""+origin, Toast.LENGTH_SHORT).show();
                //  markerOptions1=new MarkerOptions().position(place.getLatLng()).title("origin");

                Geocoder geocoder=new Geocoder(getApplicationContext());
                List<Address> list=new ArrayList<>();

                try {
                    list=geocoder.getFromLocationName(origin,1);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(list.size()>0){

                    Address address=list.get(0);
                    LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
                    markerOptions1=new MarkerOptions().position(latLng).title("current");
                    mMap.addMarker(markerOptions1);

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));

                }

            }

            @Override
            public void onError(Status status) {

            }
        });

        PlaceAutocompleteFragment placeAutocompleteFragment1=(PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.destination);
        placeAutocompleteFragment1.setHint("Your Location");
        placeAutocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
               // markerOptions2=new MarkerOptions().position(place.getLatLng()).title("Destination");
                destination=place.getName().toString();
                Toast.makeText(Directions.this, ""+destination, Toast.LENGTH_SHORT).show();
                Geocoder geocoder=new Geocoder(getApplicationContext());
                List<Address> list=new ArrayList<>();

                try {
                    list=geocoder.getFromLocationName(destination,1);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(list.size()>0){
                    Address address=list.get(0);
                    LatLng latLng=new LatLng(address.getLatitude(),address.getLongitude());
                    markerOptions2=new MarkerOptions().position(latLng).title("destination").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    mMap.addMarker(markerOptions2);

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));

                }
            }

            @Override
            public void onError(Status status) {

            }
        });

     //markerOptions1=new MarkerOptions().position(new LatLng(28.604710,77.082207)).title("origin");

     //markerOptions2=new MarkerOptions().position(new LatLng(28.680050,77.343292)).title("Destination");

        driving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                build_retrofit_and_get_response("driving");

            }
        });

        walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                build_retrofit_and_get_response("walking");
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Model_Town = new LatLng(28.7158727, 77.1910738);
        mMap.addMarker(new MarkerOptions().position(Model_Town).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Model_Town));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        // Setting onclick event listener for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                // clearing map and generating new marker points if user clicks on map more than two times
                if (MarkerPoints.size() > 1) {
                    mMap.clear();
                    MarkerPoints.clear();
                    MarkerPoints = new ArrayList<>();
                    distance.setText("");
                }

                // Adding new item to the ArrayList
                MarkerPoints.add(point);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(point);

                /**
                 * For the start location, the color of marker is GREEN and
                 * for the end location, the color of marker is RED.
                 */
                if (MarkerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else if (MarkerPoints.size() == 2) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }


                // Add new marker to the Google Map Android API V2
                mMap.addMarker(options);

                // Checks, whether start and end locations are captured
                if (MarkerPoints.size() >= 2) {
                    latLng = MarkerPoints.get(0);
                    des = MarkerPoints.get(1);
                }

            }
        });

        Button btnDriving = (Button) findViewById(R.id.driving);
        btnDriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                build_retrofit_and_get_response("driving");
            }
        });

        Button btnWalk = (Button) findViewById(R.id.walk);
        btnWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                build_retrofit_and_get_response("walking");
            }
        });
    }

    private void build_retrofit_and_get_response(String type) {

        String url = "https://maps.googleapis.com/maps/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Googleplaces service = retrofit.create(Googleplaces.class);

        Call<Example> call = service.getDistanceDuration("metric", latLng.latitude + "," + latLng.longitude,des.latitude + "," + des.longitude, type);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                try {
                    //Remove previous line from map
                    if (line != null) {
                        line.remove();
                    }
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getRoutes().size(); i++) {
                        String distances = response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText();
                        String time = response.body().getRoutes().get(i).getLegs().get(i).getDuration().getText();
                        distance.setText("Distance:" + distances + ", Duration:" + time);
                        String encodedString = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                        List<LatLng> list = decodePoly(encodedString);
                        line = mMap.addPolyline(new PolylineOptions()
                                .addAll(list)
                                .width(20)
                                .color(Color.RED)
                                .geodesic(true)
                        );
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });


    }

    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }

    // Checking if Google Play Services Available or not
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


}
