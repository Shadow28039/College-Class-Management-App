package com.example.finalproject2.User_Interface;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Entity.Term_Entity;
import com.example.finalproject2.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class Create_Term_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener   {


    EditText Term_name;
    EditText dateStart;
    EditText dateEnd;
    int dateflag = 0;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    WGU_Database db;

    Calendar start = Calendar.getInstance();
    Calendar end = Calendar.getInstance();
    Date Sdate = start.getTime();
    Date Edate = end.getTime();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_term);
        setTitle("Create Term");
        dateStart = findViewById(R.id.CreateTerm_startDate);
        dateEnd = findViewById(R.id.CreateTerm_endDate);
        Term_name =findViewById(R.id.CreateTerm_TextName);
        db = WGU_Database.getInstance(getApplicationContext());
    }// end on create

    public void showDateStart(View view){
        dateflag = 1;
        DatePickerDialog Picker = new DatePickerDialog(this,
                this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Picker.show();
    }
    public void showDateEnd(View view){
        dateflag = 2;
        DatePickerDialog Picker = new DatePickerDialog(this,
                this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Picker.show();
    }

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
            dateStart.setText(temp);
            try {
                Sdate = formatter.parse(temp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }// end if
        if(dateflag == 2){
            dateEnd.setText(temp);
            try {

                Edate = formatter.parse(temp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }// end if
    }// end on date set

    public void submit(View view) {
        // assign values from name field

        Term_Entity tempT1 = new Term_Entity();

        // get text in string value from name edit text field.
        tempT1.setTerm_name(String.valueOf((Term_name.getText())));
        // get start date value from start date on calendar pick
        tempT1.setTerm_start(Sdate);
        // get end date value from end date on calendar pick
        tempT1.setTerm_end(Edate);
        // add to database
        db.term_dao().insertTerm(tempT1);
        //
        System.out.println("completed add");
        // back to term list page
        Intent intent = new Intent(Create_Term_Activity.this, Term_Activity.class);
        startActivity(intent);
    }
    public void Cancel(View view){
        Intent intent = new Intent(Create_Term_Activity.this, Term_Activity.class);
        startActivity(intent);
    }
}// end class
