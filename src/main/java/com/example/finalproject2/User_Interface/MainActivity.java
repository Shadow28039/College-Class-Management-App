package com.example.finalproject2.User_Interface;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.finalproject2.Database.PopulateDatabase;
import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Entity.Course_Entity;
import com.example.finalproject2.R;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    WGU_Database db;
    TextView Dropped;
    TextView inProgress;
    TextView Completed;
    TextView PlantoTake;
    public static int AlertNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dropped = findViewById(R.id.droppedclass);
        inProgress = findViewById(R.id.totalclassesinprogress);
        Completed = findViewById(R.id.totalclassescompleted);
        PlantoTake = findViewById(R.id.totalclassespending);
        db = WGU_Database.getInstance(getApplicationContext());
        refreshviews();
    }
    public void TermsPageCall(View view) {
        // call TermPage
        Intent intent = new Intent(MainActivity.this, Term_Activity.class);
        startActivity(intent);
    }
    public void PopulateDB(View view){
        PopulateDatabase pop = new PopulateDatabase();
        pop.populate(getApplicationContext());
        refreshviews();

    }
    public void Nuke(View view){
        db.clearAllTables();
        refreshviews();
    }// end nuke

    private void refreshviews(){
        // values for summary view
        int Cpending = 0;
        int Ccompleted = 0;
        int Cdropped = 0;
        int Cinprogress = 0;
        String status;

        List<Course_Entity> allcourse = db.course_dao().getallcourses();
        for(int i= 0; i < allcourse.size(); i++) {
            status = allcourse.get(i).getCourse_status();
            System.out.println(status);
            if(status.equals("plan to take")){
                Cpending = Cpending+1;
            }
            if(status.equals("in progress")){
                Cinprogress = Cinprogress+1;
            }
            if(status.equals("completed")){
                Ccompleted = Ccompleted+1;
            }
            if(status.equals("dropped")){
                Cdropped = Cdropped+1;
            }
            // update views
        }// end for
        PlantoTake.setText(String.valueOf(Cpending));
        inProgress.setText(String.valueOf(Cinprogress));
        Dropped.setText(String.valueOf(Cdropped));
        Completed.setText(String.valueOf(Ccompleted));

    }// end refresh */
}