package com.example.finalproject2.User_Interface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Entity.Mentor_Entity;
import com.example.finalproject2.Entity.Term_Entity;
import com.example.finalproject2.R;

import java.util.List;

public class Edit_Mentor_Activity extends AppCompatActivity {

    int id;
    int flag = 0;
    Intent intent;
    WGU_Database db;
    Mentor_Entity selectedMentor;
    Mentor_Entity tempm1;
    /////////////////////////////
    EditText mentor_name;
    EditText mentor_phone;
    EditText mentor_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mentor);
        setTitle("Edit Mentor");
        db = WGU_Database.getInstance(getApplicationContext());
        intent = getIntent();
        // get id value for term_id_fk
        id = intent.getIntExtra("courseId", -1);
        /////
        mentor_name = findViewById(R.id.EditMentor_TextName);
        mentor_phone = findViewById(R.id.EditMentor_phone);
        mentor_email = findViewById(R.id.EditMentor_email);
        update();
    }

    public void update(){
        // try clause for editing mentor on a null mentor listing for course
        try{
            selectedMentor = db.mentor_dao().getMentor(id);
            mentor_name.setText(selectedMentor.getMentor_name());
            mentor_phone.setText(selectedMentor.getMentor_phone());
            mentor_email.setText(selectedMentor.getMentor_email());
        } // end try
        catch (Exception e) {
            mentor_name.setText("N/A");
            mentor_phone.setText("N/A");
            mentor_email.setText("N/A");
            flag = 1;// flag for no value passed
        }//end catch
    }// end update
    public void submit(View view) {
        // if mentor doesn't already exist throws exception to create new mentor entry
        try{
            selectedMentor.setMentor_name(String.valueOf(mentor_name.getText()));
            selectedMentor.setMentor_phone(String.valueOf(mentor_phone.getText()));
            selectedMentor.setMentor_email(String.valueOf(mentor_email.getText()));
            db.mentor_dao().updateMentors(selectedMentor);
        }
        catch (Exception e) {
            Mentor_Entity newmentor = new Mentor_Entity();
            newmentor.setCourse_id_fk(id);
            newmentor.setMentor_name(String.valueOf(mentor_name.getText()));
            newmentor.setMentor_phone(String.valueOf(mentor_phone.getText()));
            newmentor.setMentor_email(String.valueOf(mentor_email.getText()));
            db.mentor_dao().insertMentor(newmentor);
        }
        // back to Mentor page
        Intent intent = new Intent(Edit_Mentor_Activity.this, View_Mentor_Activity.class);
        //pass course fr_key for reference
        intent.putExtra("courseId", id);
        // complete call
        startActivity(intent);
    }// end submit
    public void Cancel(View view){
        Intent intent = new Intent(Edit_Mentor_Activity.this, View_Mentor_Activity.class);
        //pass course fr_key for reference
        intent.putExtra("courseId", id);
        // complete call
        startActivity(intent);
    }
    public void cancel(View view){
        // back to Mentor page
        Intent intent = new Intent(Edit_Mentor_Activity.this, View_Mentor_Activity.class);
        //pass course fr_key for reference
        intent.putExtra("courseId", id);
        // complete call
        startActivity(intent);
    }
}// edit mentor
