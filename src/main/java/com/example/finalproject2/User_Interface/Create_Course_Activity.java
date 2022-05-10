package com.example.finalproject2.User_Interface;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Entity.Course_Entity;
import com.example.finalproject2.Entity.Mentor_Entity;
import com.example.finalproject2.Entity.Term_Entity;
import com.example.finalproject2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Create_Course_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    int id;
    Intent intent;
    WGU_Database db;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    /////////////////////
    EditText course_name;
    EditText start_date;
    EditText end_date;
    EditText Mentor;
    EditText phone;
    EditText email;
    EditText Alert;
    EditText Notes;
    ///////////////
    Calendar start = Calendar.getInstance();
    Calendar end = Calendar.getInstance();
    Date Sdate = start.getTime();
    Date Edate = end.getTime();
    Date Adate;
    int dateflag = 0;
    ///////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = WGU_Database.getInstance(getApplicationContext());
        intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        setTitle("Create Course");
        // get id value for term_id_fk
        id = intent.getIntExtra("termId", -1);
        // assign values from selected term to new object
        //assign xml boxes to variables
        /////
        course_name = findViewById(R.id.CreateCourse_TextName);
        start_date = findViewById(R.id.CreateCourse_startDate);
        end_date = findViewById(R.id.CreateCourse_endDate);
        Mentor = findViewById(R.id.CreateCourse_Mentor);
        phone = findViewById(R.id.CreateCourse_Phone);
        email = findViewById(R.id.CreateCourse_Email);
        Notes = findViewById(R.id.CreateCourse_notes);


    }

    public void showDateStart(View view) {
        dateflag = 1;
        DatePickerDialog Picker = new DatePickerDialog(this,
                this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Picker.show();
    }

    public void showDateEnd(View view) {
        dateflag = 2;
        DatePickerDialog Picker = new DatePickerDialog(this,
                this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Picker.show();
    }

    public void showDateAlert(View view) {
        dateflag = 3;
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
            start_date.setText(temp);
            try {
                Sdate = formatter.parse(temp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }// end if
        if (dateflag == 2) {
            end_date.setText(temp);
            try {

                Edate = formatter.parse(temp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }// end if
        if (dateflag == 3) {
            Alert.setText(temp);
            try {

                Adate = formatter.parse(temp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }// end if
    }// end on date set

    public void submit(View view) {
        // create object
        Course_Entity tempC1 = new Course_Entity();
        Mentor_Entity tempM1 = new Mentor_Entity();
        Course_Entity tempC2; // for mentor reference
        // assign values
        tempC1.setCourse_name(String.valueOf(course_name.getText()));
        tempC1.setTerm_id_fk(id);
        tempC1.setCourse_start(Sdate);
        tempC1.setCourse_end(Edate);
        tempC1.setCourse_status("pending");
        tempC1.setCourse_notes(String.valueOf(Notes.getText()));
        tempC1.setCourse_alert_date(Adate);
        //
        // add course to Database
        db.course_dao().insertCourse(tempC1);
        //get course id just created for passing into mentor
        int mentorPass;
        tempC2 = db.course_dao().LastValue();
        mentorPass = tempC2.getCourse_id();
        System.out.println(mentorPass);
        //
        // mentor section add
        tempM1.setCourse_id_fk(mentorPass);
        tempM1.setMentor_name(String.valueOf(Mentor.getText()));
        tempM1.setMentor_phone(String.valueOf(phone.getText()));
        tempM1.setMentor_email(String.valueOf(email.getText()));
        db.mentor_dao().insertMentor(tempM1);
        // back to term list page
        Intent intent = new Intent(Create_Course_Activity.this, View_Term_Activity.class);
        intent.putExtra("termId", id);
        startActivity(intent);
    }
    public void cancel(View view){
        Intent intent = new Intent(Create_Course_Activity.this, View_Term_Activity.class);
        intent.putExtra("termId", id);
        startActivity(intent);
    }

}
