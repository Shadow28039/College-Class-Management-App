package com.example.finalproject2.Database;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.finalproject2.R;

import java.util.Date;


public class NotificationHelper extends ContextWrapper {
    public static final String AssessmentAlertID = "AssessmentAlertID";
    public static final String ClassStartDateAlertID = "ClassStartDateAlertID";
    public static final String ClassEndDateAlertID = "ClassEndDateAlertID";
    //
    public static final String AssessmentAlertName = "AssessmentAlertName";
    public static final String ClassStartDateAlertName = "ClassStartDateAlertName";
    public static final String ClassEndDateAlertName = "ClassEndDateAlertName";


    //
    private NotificationManager manager;
    //
    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }

    }//notification helper(public)

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels(){

        NotificationChannel Assessment_Channel = new NotificationChannel(
                AssessmentAlertID,AssessmentAlertName,
                NotificationManager.IMPORTANCE_HIGH);
        Assessment_Channel.enableLights(true);
        Assessment_Channel.enableVibration(true);
        Assessment_Channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(Assessment_Channel);

        // second channel(start date)
        NotificationChannel Class_Start_Channel = new NotificationChannel(
                ClassStartDateAlertID,ClassStartDateAlertName,
                NotificationManager.IMPORTANCE_HIGH);
        Assessment_Channel.enableLights(true);
        Assessment_Channel.enableVibration(true);
        Assessment_Channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(Class_Start_Channel);
        // third channel(end date)
        NotificationChannel Class_End_Channel = new NotificationChannel(
                ClassEndDateAlertID,ClassEndDateAlertName,
                NotificationManager.IMPORTANCE_HIGH);
        Assessment_Channel.enableLights(true);
        Assessment_Channel.enableVibration(true);
        Assessment_Channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(Class_End_Channel);

    }// create channels
    public NotificationManager getManager(){
    if (manager == null){
        manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    }// end if for null value
    return manager;
    }// end getManager
    //
    public NotificationCompat.Builder getAssessmentNotification(String date, String Name){
        System.out.println("notification section");
        String Title = "W.G.U Assesssment Goal Date";
        String Message = Name+": is Due on "+date;
        return new NotificationCompat.Builder(getApplicationContext(),AssessmentAlertID)
        .setSmallIcon(R.drawable.ic_assessment_alarm)
        .setContentTitle(Title)
        .setContentText(Message);
    }
/*
start and end date notification sections.
    public NotificationCompat.Builder getClassStartNotification(String date, String Name){
        String Title = "W.G.U Class Start";

        String Message = Name+" is Due to Start on: "+date;

        return new NotificationCompat.Builder(getApplicationContext(),ClassStartDateAlertID)
                .setSmallIcon(R.drawable.ic_assessment_alarm)
                .setContentTitle(Title)
                .setContentText(Message);
    }
    public NotificationCompat.Builder getClassEndNotification(String date, String Name) {
        String Title = "W.G.U Class End";

        String Message = Name + " is Due to End on: " + date;

        return new NotificationCompat.Builder(getApplicationContext(), ClassEndDateAlertID)
                .setSmallIcon(R.drawable.ic_assessment_alarm)
                .setContentTitle(Title)
                .setContentText(Message);
    } */

}// notification helper(class)
