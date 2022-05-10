package com.example.finalproject2.User_Interface;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;


import com.example.finalproject2.User_Interface.MyReceiver;
import com.example.finalproject2.Database.NotificationHelper;
import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Entity.Assessment_Entity;
import com.example.finalproject2.R;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class View_Assessment_Activity extends AppCompatActivity{
    TextView TypeView;
    TextView TitleView;
    TextView DueDateView;
    TextView InfoView;
    ////
    int numAlert;
    WGU_Database db;
    Intent intent;
    int Assessment_id;
    Assessment_Entity selectedAssessment;
    private NotificationManagerCompat notificationManager;
    //////
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    ///////////////
    private NotificationHelper mNotificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = WGU_Database.getInstance(getApplicationContext());
        notificationManager = NotificationManagerCompat.from(this);
        intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assessment);
        setTitle("View Assessment");
        // get id value for assessment id default to -1 if no value passed
        Assessment_id = intent.getIntExtra("assessmentId", -1);
        // assign references
        TypeView = findViewById(R.id.view_Assessment_Type);
        TitleView = findViewById(R.id.view_Assessment_Title);
        DueDateView = findViewById(R.id.view_Assessment_DueDate);
        InfoView = findViewById(R.id.view_Assessment_Info);
        // get values from id
        selectedAssessment = db.assessmentDAO().getAssessment(Assessment_id);
        // assign values to edit boxes
        TypeView.setText(selectedAssessment.getAssessment_Type());
        TitleView.setText(selectedAssessment.getAssessment_Title());
        // formatter for DUE DATE
        String DateFormatted = formatter.format(selectedAssessment.getAssessment_Due_date());
        DueDateView.setText(DateFormatted);
        InfoView.setText(selectedAssessment.getAssessment_Info());
        mNotificationHelper = new NotificationHelper(this);

    }// end on create
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.assessment_menu,menu);
        return true;
    }
    //menu listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            //delete course and call course view
            case R.id.DeleteAssessment:
                View_Assessment_delete();
                return true;
            // set alert
            case R.id.SubNotification1Assessment:
                String Sdate = DueDateView.getText().toString();
                String alarmformat = "MM/dd/yy";
                SimpleDateFormat dateformat = new SimpleDateFormat(alarmformat, Locale.US);
                Date newDate = null;
                try {
                    newDate = dateformat.parse(Sdate);
                    System.out.println(dateformat.parse(Sdate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long trigger = newDate.getTime();
                Intent intent = new Intent(this, MyReceiver.class);
                intent.putExtra("Message", "Assessment: " + TitleView.getText()
                        + " is currently due");
                intent.putExtra("Channel", "channel1");
                intent.putExtra("Title","Assessment Due");
                PendingIntent sender = PendingIntent.getBroadcast(this, ++MainActivity.AlertNum , intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        }
            //case R.id.SubNotification2Assessment:

            return super.onOptionsItemSelected(item);
        }

    // edit assessment
   public void View_Assessment_edit(View view)
   {
       Intent intent = new Intent(View_Assessment_Activity.this, Edit_Assessment_Activity.class);
       //assign term_id variable from the object clicked
       // add term id to intent
       intent.putExtra("assessmentId",Assessment_id);
       // complete call
       startActivity(intent);
   }// end edit
   // delete Assessment from db
   public void View_Assessment_delete()
   {
       db.assessmentDAO().deleteAssessment(selectedAssessment);

       Intent intent = new Intent(this, View_Course_Activity.class);
       //assign term_id variable from the object clicked
       // add term id to intent
       intent.putExtra("courseId",selectedAssessment.getCourse_id_fk());
       // complete call
       startActivity(intent);
   }// end delete

    public void SetAlert(){

    }
}// end main
