package com.example.finalproject2.User_Interface;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.finalproject2.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends BroadcastReceiver {
    public static final String Channel_1_ID="channel1";
    public static final String Channel_2_ID="channel2";
    public static final String Channel_3_ID="channel3";
    static int notificationID;
    String Channel_ID = "test";
    String AlertMessage;
    String AlertTitle = "";
    int AlertID;

    @Override
    public void onReceive(Context context, Intent intent) {

        AlertMessage = intent.getStringExtra("Message");
        Channel_ID = intent.getStringExtra("Channel");
        AlertTitle = intent.getStringExtra("Title");
        CreateChannel(context, Channel_ID);
        Notification create = new NotificationCompat.Builder(context, Channel_ID)
                .setSmallIcon(R.drawable.ic_assessment_alarm)
                .setContentTitle(AlertTitle)
                .setContentText(AlertMessage).build();
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++,create);

    }

    private void CreateChannel(Context context, String Channel_ID) {
        // SDK check for orea
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // first channel
            NotificationChannel channel1 = new NotificationChannel(Channel_1_ID,"channel 1",NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setDescription("notification for Assessment Due Dates");
            //second channel
            NotificationChannel channel2 = new NotificationChannel(Channel_2_ID,"channel 2",NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setDescription("notification for Class Start Dates");
            // third channel
            NotificationChannel channel3 = new NotificationChannel(Channel_3_ID,"channel 3",NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setDescription("notification for Class End Dates");
            // create channels
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
            notificationManager.createNotificationChannel(channel2);
            notificationManager.createNotificationChannel(channel3);

        }// end if outer
    }
}
