package com.example.finalproject2.User_Interface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Entity.Course_Entity;
import com.example.finalproject2.R;

public class Edit_Note_Activity extends AppCompatActivity {
    int id;
    Intent intent;
    WGU_Database db;
    Course_Entity selectedCourse;
    EditText notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Edit Notes");
        setContentView(R.layout.activity_edit_notes);
        notes = findViewById(R.id.edit_view_note);
        db = WGU_Database.getInstance(getApplicationContext());
        intent = getIntent();
        // get id value for term_id_fk
        id = intent.getIntExtra("noteId", -1);
        // get course;
        selectedCourse = db.course_dao().getCourse(id);
        System.out.println(selectedCourse.getCourse_notes());
        notes.setText(selectedCourse.getCourse_notes());
    }
    public void submit(View view)
    {
        // get text in string value from name edit text field.
        selectedCourse.setCourse_notes(String.valueOf(notes.getText()));
        // add to database
        db.course_dao().updateCourse(selectedCourse);
        //
        System.out.println("completed edit");
        // back to term list page
        ////////////////////////////////////////////////////
        id = selectedCourse.getCourse_id();
        Intent intent = new Intent(Edit_Note_Activity.this, View_Course_Activity.class);
        //assign term_id variable from the object clicked
        // add term id to intent
        intent.putExtra("courseId", id);
        // complete call
        startActivity(intent);
    }// end submit
    public void cancel(View view){
        id = selectedCourse.getCourse_id();
        Intent intent = new Intent(Edit_Note_Activity.this, View_Course_Activity.class);
        //assign term_id variable from the object clicked
        // add term id to intent
        intent.putExtra("courseId", id);
        // complete call
        startActivity(intent);
    }
    public void EmailNotes(View view){
        Intent intent = new Intent(Edit_Note_Activity.this, EmailSender.class);
        //assign term_id variable from the object clicked
        // add term id to intent
        String temp = String.valueOf(notes.getText());
        System.out.println(temp);
        intent.putExtra("notes",temp);
        // complete call
        startActivity(intent);
    }
}// end class
