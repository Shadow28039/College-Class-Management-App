package com.example.finalproject2.User_Interface;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Entity.Course_Entity;
import com.example.finalproject2.Entity.Term_Entity;
import com.example.finalproject2.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class View_Term_Activity extends AppCompatActivity {
    TextView startView;
    TextView endView;
    TextView nameView;
    ListView courseList;
    TextView alertView;
    int id;
    WGU_Database db;
    Intent intent;
    Term_Entity selectedTerm;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    Boolean ClassListEmpty = false;


    protected void onCreate(Bundle savedInstanceState) {
        db = WGU_Database.getInstance(getApplicationContext());
        intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_term);
        setTitle("View Term");
        courseList = findViewById(R.id.course_listview);
        // get passed values from last class
        id = intent.getIntExtra("termId", -1 );
        // assign values from selected term to new object
        selectedTerm = db.term_dao().getTerm(id);
        // view info
        nameView = findViewById(R.id.Term_name);
        startView = findViewById(R.id.StartDateValue);
        endView = findViewById(R.id.EndDateValue);
        alertView = findViewById(R.id.errorMessage);
        //listener for list click
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int course_id;
                // call view page for term
                Intent intent = new Intent(View_Term_Activity.this, View_Course_Activity.class);
                // get list of courses based on id from term
                List<Course_Entity> course_list = db.course_dao().getCourseList(selectedTerm.getTerm_id());
                //assign course_id variable from the object clicked
                course_id = course_list.get(position).getCourse_id();
                // add course id to intent
                intent.putExtra("courseId", course_id);
                // complete call
                startActivity(intent);
            }// end listener
        });// end on click
        // set data to values through method
        update();
        ListUpdate();
    }// end on create
    // menu create
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.term_menu,menu);
        return true;
    }
    //menu listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            //delete course
            case R.id.DeleteTerm:
                // delete term if course list is not empty(false)
                if(ClassListEmpty==true) {
                    db.term_dao().deleteTerm(selectedTerm);
                    // return to terms list page
                    Intent intent = new Intent(View_Term_Activity.this, Term_Activity.class);
                    //assign term_id variable from the object clicked
                    // complete call
                    startActivity(intent);
                }
                // else send error message
                else{
                    alertView.setText("Term with assigned classes can not be deleted");
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void update()
    {
        // perform function if value was passed otherwise(else) go back to term_activity page
        if(selectedTerm != null ) {
            // get date values from object
            Date start = selectedTerm.getTerm_start();
            Date end = selectedTerm.getTerm_end();
            // format to string values using formatting conditions
            String start_string = formatter.format(start);
            String end_string = formatter.format(end);
            // assign values to text views
            nameView.setText(selectedTerm.getTerm_name());
            startView.setText(start_string);
            endView.setText(end_string);
        }// end if
        else {
            // call term_activity page
            Intent intent = new Intent(View_Term_Activity.this, Term_Activity.class);
            startActivity(intent);
        }// end else
    }// end update
    public void view_term_editTerm(View view)
    {
        id = selectedTerm.getTerm_id();
        Intent intent = new Intent(View_Term_Activity.this, Edit_Term_Activity.class);
        //assign term_id variable from the object clicked
        // add term id to intent
        intent.putExtra("termId",id);
        // complete call
        startActivity(intent);
    }
    public void ListUpdate()
    {
        List<Course_Entity> AllCourses = db.course_dao().getCourseList(selectedTerm.getTerm_id());
        String[] courses = new String[AllCourses.size()];
        if(AllCourses.isEmpty()){
            ClassListEmpty = true;
        }
        for(int i= 0; i < AllCourses.size(); i++){
            courses[i] = AllCourses.get(i).getCourse_name();
            //System.out.println("data added");
        }// end for
        // assign array to list view
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,courses);
        courseList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }// end list update
    public void newcourse(View view)
    {
        // call view page for term
        Intent intent = new Intent(View_Term_Activity.this, Create_Course_Activity.class);
        // get pass term id into add course
        intent.putExtra("termId", id);
        // complete call
        startActivity(intent);
    }
}// end class

