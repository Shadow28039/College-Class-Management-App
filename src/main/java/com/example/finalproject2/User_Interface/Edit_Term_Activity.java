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

public class Edit_Term_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    EditText Term_name;
    EditText dateStart;
    EditText dateEnd;
    int dateflag = 0;
    int id;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    WGU_Database db;
    Calendar start = Calendar.getInstance();
    Calendar end = Calendar.getInstance();
    Date Sdate = start.getTime();
    Date Edate = end.getTime();
    Intent intent;
    Term_Entity selectedTerm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        setTitle("Edit Term");
        db = WGU_Database.getInstance(getApplicationContext());
        intent = getIntent();
        // get passed values from last class
        id = intent.getIntExtra("termId", -1 );
        // assign values from selected term to new object
        selectedTerm = db.term_dao().getTerm(id);
        // view info
        Term_name = findViewById(R.id.EditTerm_TextName);
        dateStart = findViewById(R.id.EditTerm_startDate);
        dateEnd = findViewById(R.id.EditTerm_endDate);
        // set data to values through method
        update();
    }// on create

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // start calendar pick
    public void showDateStart(View view){
        dateflag = 1; // use same date picker method but change output based on flag
        DatePickerDialog Picker = new DatePickerDialog(this,
                this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        Picker.show();
    }
    // end calendar pick
    public void showDateEnd(View view){
        dateflag = 2;
        DatePickerDialog Picker = new DatePickerDialog(this,
                this, Calendar.getInstance().get(Calendar.YEAR),
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
    public void update()
    {
        // perform function if value was passed otherwise(else) go back to term_activity page
        // get date values from object
        Date start = selectedTerm.getTerm_start();
        Date end = selectedTerm.getTerm_end();
        // format to string values using formatting conditions
        String start_string = formatter.format(start);
        String end_string = formatter.format(end);
        // assign values to text views
        Term_name.setText(selectedTerm.getTerm_name());
        dateStart.setText(start_string);
        dateEnd.setText(end_string);

    }// end update
    public void submit(View view){

        // get text in string value from name edit text field.
        selectedTerm.setTerm_name(String.valueOf((Term_name.getText())));
        // get start date value from start date on calendar pick
        selectedTerm.setTerm_start(Sdate);
        //tempT1.setTerm_start(start.getTime());
        // get end date value from end date on calendar pick
        selectedTerm.setTerm_end(Edate);
        //tempT1.setTerm_end(end.getTime());
        // add to database
        db.term_dao().updateTerm(selectedTerm);
        //
        System.out.println("completed edit");
        // back to term list page
        ////////////////////////////////////////////////////
        id = selectedTerm.getTerm_id();
        Intent intent = new Intent(Edit_Term_Activity.this, View_Term_Activity.class);
        //assign term_id variable from the object clicked
        // add term id to intent
        intent.putExtra("termId",id);
        // complete call
        startActivity(intent);
    }
    public void cancel(View view){
        Intent intent = new Intent(Edit_Term_Activity.this, View_Term_Activity.class);
        //assign term_id variable from the object clicked
        // add term id to intent
        intent.putExtra("termId",id);
        // complete call
        startActivity(intent);
    }
}// end class

