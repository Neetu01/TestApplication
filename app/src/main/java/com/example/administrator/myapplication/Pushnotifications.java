package com.example.administrator.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pushnotifications extends AppCompatActivity{
    MyFirebaseInstanceIdService id;
    String notification_channel_id="testapp";
    String notification_name="simplified coding";
    Button send;
    EditText title,message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushnotifications);
        title=findViewById(R.id.title);
        message=findViewById(R.id.message);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.43.35/").addConverterFactory(GsonConverterFactory.create()).build();
                Pushinterface pushinterface=retrofit.create(Pushinterface.class);
                Call<List<Notification>> call=pushinterface.getnotiifcation(title.getText().toString(),message.getText().toString());
                call.enqueue(new Callback<List<Notification>>() {
                    @Override
                    public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {

                        Toast.makeText(Pushnotifications.this, "Success", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onFailure(Call<List<Notification>> call, Throwable t) {
                        Toast.makeText(Pushnotifications.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

       if((Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)){
           NotificationChannel  notificationChannel = null;
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               notificationChannel = new NotificationChannel(notification_channel_id,notification_name, NotificationManager.IMPORTANCE_DEFAULT);
               notificationChannel.setDescription("TESTING APP");
               NotificationManager notificationManager=getSystemService(NotificationManager.class);
               notificationManager.createNotificationChannel(notificationChannel);

           }
       }

       FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
           @Override
           public void onComplete(@NonNull Task<InstanceIdResult> task) {

               if(task.isSuccessful()){
                   String token=task.getResult().getToken();
                   Log.e("Token",token);
                   Toast.makeText(getApplicationContext(), ""+token, Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(getApplicationContext(), ""+task.getException(), Toast.LENGTH_SHORT).show();
               }
           }
       });



      }



      public void displayNotifiaction()
      {
          NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this,notification_channel_id);
          notificationBuilder.setAutoCancel(true)
                  .setDefaults(NotificationCompat.DEFAULT_ALL)
                  .setWhen(System.currentTimeMillis())
                  .setContentTitle("Its working")
                  .setContentText("First Notification")
                  .setSmallIcon(R.drawable.com_facebook_button_icon)
                  .setContentInfo("Info")
                  .setPriority(NotificationCompat.PRIORITY_DEFAULT);

          NotificationManagerCompat notification_channel_id=NotificationManagerCompat.from(this);
          notification_channel_id.notify();

      }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
