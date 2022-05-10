package com.example.finalproject2.User_Interface;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Entity.Assessment_Entity;
import com.example.finalproject2.Entity.Course_Entity;
import com.example.finalproject2.Entity.Mentor_Entity;
import com.example.finalproject2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Create_Assessment_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText Type;
    EditText Title;
    EditText DueDate;
    EditText Info;
    EditText AlarmDate;
    WGU_Database db;
    Intent intent;
    int id;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    //////
    Calendar due = Calendar.getInstance();
    Calendar alarm = Calendar.getInstance();
    Date Ddate = due.getTime();
    Date Adate = alarm.getTime();
    int dateflag = 0;
    ///////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = WGU_Database.getInstance(getApplicationContext());
        intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assessment);
        setTitle("Create Assessment");
        // get id value for CourseID_foreign key
        id = intent.getIntExtra("courseId", -1);
        // assign values from selected term to new object
        //assign xml boxes to variables
        /////
        // assign references
        Type = findViewById(R.id.create_Assessment_Type);
        Title = findViewById(R.id.create_Assessment_Title);
        DueDate = findViewById(R.id.create_Assessment_DueDate);
        Info = findViewById(R.id.create_Assessment_Title);

    }// end on create

    // show date picker for date due
    public void showDateDue(View view) {
        dateflag = 1;
        DatePickerDialog Picker = new DatePickerDialog(this,
                this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Picker.show();
    }
    // date picker for date alert
    public void showAlertDate(View view) {
        dateflag = 2;
        DatePickerDialog Picker = new DatePickerDialog(this,
                this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Picker.show();
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        System.out.println(month + "," + dayOfMonth + "," + year);
        // month on calendar starts at o, add 1 for correct values
        int newmonth = month + 1;
        String monthTemp;
        // if month less then 10 add 0 to front of string
        if (newmonth < 10) {
            monthTemp = "0" + newmonth;
            System.out.println(monthTemp);
        } else {
            monthTemp = String.valueOf(newmonth);
        }
        String temp = monthTemp + "/" + dayOfMonth + "/" + year;

        if (dateflag == 1) {
            //value to view
            DueDate.setText(temp);
            try {
                //value to pass
                Ddate = formatter.parse(temp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }// end if
        if (dateflag == 2) {
            //value to view
            AlarmDate.setText(temp);
            try {
                // value to pass
                Adate = formatter.parse(temp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }// end if

    }// end on date set
    public void Assessment_Create_Submit(View view){
        Assessment_Entity tempA1 = new Assessment_Entity();
        // assign values
        tempA1.setAssessment_Title(String.valueOf(Title.getText()));
        tempA1.setAssessment_Type(String.valueOf(Type.getText()));
        tempA1.setCourse_id_fk(id);
        tempA1.setAssessment_Info(String.valueOf(Info.getText()));
        //
        tempA1.setAssessment_Due_date(Ddate);
        //add to DB
        db.assessmentDAO().insertAssessment(tempA1);
        // call course view and pass id
        Intent intent = new Intent(Create_Assessment_Activity.this, View_Course_Activity.class);
        intent.putExtra("courseId", id);
        startActivity(intent);
    }
    public void Cancel(View view){
        Intent intent = new Intent(Create_Assessment_Activity.this, View_Course_Activity.class);
        intent.putExtra("courseId", id);
        startActivity(intent);
    }
}// end main
