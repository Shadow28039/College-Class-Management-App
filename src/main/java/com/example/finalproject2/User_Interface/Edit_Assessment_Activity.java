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
import com.example.finalproject2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Edit_Assessment_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    EditText Type;
    EditText Title;
    EditText DueDate;
    EditText Info;
    WGU_Database db;
    Intent intent;
    int id;
    Assessment_Entity selectedAssessment;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    //////
    Date Ddate;
    ///////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);
        setTitle("Edit Assessment");
        db = WGU_Database.getInstance(getApplicationContext());
        intent = getIntent();

        // get id value for CourseID_foreign key
        id = intent.getIntExtra("assessmentId", -1);
        // assign values from selected term to new object
        //assign xml boxes to variables
        /////
        // assign references
        Type = findViewById(R.id.edit_Assessment_Type);
        Title = findViewById(R.id.edit_Assessment_Title);
        DueDate = findViewById(R.id.edit_Assessment_DueDate);
        Info = findViewById(R.id.edit_Assessment_Info);
        // get values from id
        selectedAssessment = db.assessmentDAO().getAssessment(id);
        // assign values to edit boxes
        Type.setText(String.valueOf(selectedAssessment.getAssessment_Type()));
        Title.setText(String.valueOf(selectedAssessment.getAssessment_Title()));
        String DateFormatted = formatter.format(selectedAssessment.getAssessment_Due_date());
        DueDate.setText(DateFormatted);
        Info.setText(String.valueOf(selectedAssessment.getAssessment_Info()));
        //assign default values to Ddate and Adate (will be passed through if not edited)

    }// end on create


    // show date picker for date due
    public void showDateDue(View view) {
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
            DueDate.setText(temp);
        try {
            Ddate = formatter.parse(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }// end on date set
    public void Submit(View view){
        // assign values
        selectedAssessment.setAssessment_Title(String.valueOf(Title.getText()));
        selectedAssessment.setAssessment_Type(String.valueOf(Type.getText()));
        selectedAssessment.setCourse_id_fk(id);
        selectedAssessment.setAssessment_Info(String.valueOf(Info.getText()));
        //
        selectedAssessment.setAssessment_Due_date(Ddate);
        //add to DB
        db.assessmentDAO().updateAssessment(selectedAssessment);
        // call course view and pass id
        Intent intent = new Intent(Edit_Assessment_Activity.this, View_Assessment_Activity.class);
        intent.putExtra("assessmentId", id);
        startActivity(intent);

    }// end submit
    public void cancel(View view){
        Intent intent = new Intent(Edit_Assessment_Activity.this, View_Assessment_Activity.class);
        intent.putExtra("assessmentId", id);
        startActivity(intent);
    }//end cancel
}
