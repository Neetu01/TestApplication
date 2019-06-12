package com.example.administrator.myapplication.Jsonparsing;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Jsonparsingg extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerviewAdapter recyclerviewAdapter;
    List<Employee> arrayLists;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonparsingg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog=new ProgressDialog(Jsonparsingg.this);
        progressDialog.setMessage("Loading Data.. Please wait..");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerView=findViewById(R.id.recyclerview);
        linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

       /* Retrofit retrofit=new Retrofit.Builder().baseUrl("http://api.androiddeft.com/").addConverterFactory(GsonConverterFactory.create()).build();
        apiinterface apiint=retrofit.create(com.example.administrator.myapplication.Jsonparsing.apiinterface.class);
        Call<Jsonmodel> call=apiint.getMyJSON();
        call.enqueue(new Callback<Jsonmodel>() {
            @Override
            public void onResponse(Call<Jsonmodel> call, Response<Jsonmodel> response) {
                progressDialog.dismiss();
                Toast.makeText(Jsonparsingg.this, "Sucees", Toast.LENGTH_SHORT).show();
                arrayLists=response.body().getEmployee();
                recyclerviewAdapter=new RecyclerviewAdapter(arrayLists);
                recyclerView.setAdapter(recyclerviewAdapter);

            }

            @Override
            public void onFailure(Call<Jsonmodel> call, Throwable t) {
                Toast.makeText(Jsonparsingg.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });*/

       Retrofit retrofit=new Retrofit.Builder().baseUrl("https://reqres.in/api/").addConverterFactory(GsonConverterFactory.create()).build();
       apiinterface apiinter=retrofit.create(apiinterface.class);
       Call<Pagemodel> call=apiinter.getpagedetail();
       call.enqueue(new Callback<Pagemodel>() {
           @Override
           public void onResponse(Call<Pagemodel> call, Response<Pagemodel> response) {
               //String list=response.body().toString();
              List<Datum> list=response.body().getData();
              Toast.makeText(Jsonparsingg.this, "Success"+list, Toast.LENGTH_SHORT).show();
             //  recyclerviewAdapter=new RecyclerviewAdapter(list,getApplicationContext());
              // recyclerView.setAdapter(recyclerviewAdapter);
           }

           @Override
           public void onFailure(Call<Pagemodel> call, Throwable t) {
               Toast.makeText(Jsonparsingg.this, "Error", Toast.LENGTH_SHORT).show();
           }
       });
    }

}
