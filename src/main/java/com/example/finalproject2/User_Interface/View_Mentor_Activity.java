package com.example.finalproject2.User_Interface;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Entity.Mentor_Entity;
import com.example.finalproject2.R;

import java.util.Objects;

public class View_Mentor_Activity extends AppCompatActivity {
    TextView mentor;
    TextView phone;
    TextView email;
    WGU_Database db;
    Intent intent;
    int id;// id is course id
    Mentor_Entity selectedMentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("View Mentor");
        setContentView(R.layout.activity_view_mentor);
        db = WGU_Database.getInstance(getApplicationContext());
        intent = getIntent();
        // get id value for term_id_fk
        id = intent.getIntExtra("courseId", -1);
        // get course
        //selectedMentor = db.mentor_dao().getMentorList(id);
        /////
        mentor = findViewById(R.id.Mentor_View_name);
        phone = findViewById(R.id.Mentor_View_phone);
        email = findViewById(R.id.Mentor_View_email);
        //
        update();
        }// end on create

    public void update(){
        // try clause to handle exception caused by no mentor being listed for course
        try {
            selectedMentor = db.mentor_dao().getMentor(id);
            mentor.setText(selectedMentor.getMentor_name());
            phone.setText(selectedMentor.getMentor_phone());
            email.setText(selectedMentor.getMentor_email());
        } catch (Exception e) {
            mentor.setText("N/A");
            phone.setText("N/A");
            email.setText("N/A");
        }// end catch
    }// end update

    public void editMentor(View view)
    {
        Intent intent = new Intent(View_Mentor_Activity.this, Edit_Mentor_Activity.class);
        //assign term_id variable from the object clicked
        // add term id to intent
        intent.putExtra("courseId",id);
        // complete call
        startActivity(intent);
    }// end edit course
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mentor_menu,menu);
        return true;
    }
    //menu listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            //delete course
            case R.id.DeleteMentor:
                db.mentor_dao().deleteMentor(selectedMentor);
                Intent intent = new Intent(View_Mentor_Activity.this, View_Course_Activity.class);
                // add course id for call back
                intent.putExtra("courseId",id);
                // complete call
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    ///
}// end class

