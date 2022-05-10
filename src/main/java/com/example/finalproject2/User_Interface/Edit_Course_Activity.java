package com.example.finalproject2.User_Interface;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Entity.Course_Entity;
import com.example.finalproject2.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Edit_Course_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    int id;
    Intent intent;
    WGU_Database db;
    Course_Entity selectedCourse;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    /////////////////////
    EditText course_name;
    EditText start_date;
    EditText end_date;
    int dateflag = 0;
    ///////////////////
    Calendar start = Calendar.getInstance();
    Calendar end = Calendar.getInstance();
    Date Sdate = start.getTime();
    Date Edate = end.getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        setTitle("Edit Course");
        db = WGU_Database.getInstance(getApplicationContext());
        intent = getIntent();
        // get passed values from last class
        id = intent.getIntExtra("courseId", -1 );
        // assign values from selected term to new object
        selectedCourse = db.course_dao().getCourse(id);
        // view info
        course_name = findViewById(R.id.EditCourse_TextName);
        start_date = findViewById(R.id.EditCourse_startDate);
        end_date = findViewById(R.id.EditCourse_endDate);
        // set data to values through method
        update();
    }// on create

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // start calendar pick
    public void showDateStart(View view){
        dateflag = 1;
        DatePickerDialog Picker = new DatePickerDialog(this,
                (DatePickerDialog.OnDateSetListener) this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Picker.show();
    }
    // end calendar pick
    public void showDateEnd(View view){
        dateflag = 2;
        DatePickerDialog Picker = new DatePickerDialog(this,
                (DatePickerDialog.OnDateSetListener) this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Picker.show();
    }
    //
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        System.out.println( month +","+ dayOfMonth +","+ year);
        // month on calendar starts at o, add 1 for correct values
        int newmonth = month + 1;
        String monthTemp;
        // if month less then 10 add 0 to front of string
        if(newmonth < 10 ) {
            monthTemp = "0"+ newmonth;
            System.out.println(monthTemp);
        }
        else {
            monthTemp = String.valueOf(newmonth);
        }
        String temp = monthTemp+"/"+dayOfMonth+"/"+year;

        if(dateflag == 1){
            start_date.setText(temp);
            try {
                Sdate = formatter.parse(temp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }// end if
        if(dateflag == 2){
            end_date.setText(temp);
            try {

                Edate = formatter.parse(temp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }// end if
    }// end on date set
    public void update()
    {
        // perform function if value was passed otherwise(else) go back to term_activity page
        // get date values from object
        Date start = selectedCourse.getCourse_start();
        Date end = selectedCourse.getCourse_end();
        // format to string values using formatting conditions
        String start_string = formatter.format(start);
        String end_string = formatter.format(end);
        // assign values to text views
        course_name.setText(selectedCourse.getCourse_name());
        start_date.setText(start_string);
        end_date.setText(end_string);

    }// end update
    public void submit(View view) {
        // get text in string value from name edit text field.
        selectedCourse.setCourse_name(String.valueOf(course_name.getText()));
        // get start date value from start date on calendar pick
        selectedCourse.setCourse_start(Sdate);
        // get end date value from end date on calendar pick
        selectedCourse.setCourse_end(Edate);
        // add to database
        db.course_dao().updateCourse(selectedCourse);
        //
        System.out.println("completed edit");
        // back to term list page
        ////////////////////////////////////////////////////
        id = selectedCourse.getCourse_id();
        Intent intent = new Intent(Edit_Course_Activity.this, View_Course_Activity.class);
        //assign term_id variable from the object clicked
        // add term id to intent
        intent.putExtra("courseId", id);
        // complete call
        startActivity(intent);
    }// edit submit
    public void cancel(View view){
        Intent intent = new Intent(Edit_Course_Activity.this, View_Course_Activity.class);
        //assign term_id variable from the object clicked
        // add term id to intent
        intent.putExtra("courseId", id);
        // complete call
        startActivity(intent);
    }
    }
