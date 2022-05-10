package com.example.finalproject2.User_Interface;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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

import com.example.finalproject2.DAO.Course_DAO;
import com.example.finalproject2.User_Interface.MyReceiver;
import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Entity.Assessment_Entity;
import com.example.finalproject2.Entity.Course_Entity;
import com.example.finalproject2.Entity.Term_Entity;
import com.example.finalproject2.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class View_Course_Activity extends AppCompatActivity {
    TextView nameView;
    TextView startView;
    TextView endView;
    TextView statusView;
    ListView Assessment_View;

    int course_id;
    WGU_Database db;
    Intent intent;
    Course_Entity selectedCourse;
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    protected void onCreate(Bundle savedInstanceState) {
        db = WGU_Database.getInstance(getApplicationContext());
        intent = getIntent();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course);
        setTitle("View Course");
        Assessment_View = findViewById(R.id.View_AssessmentListView);
        // get passed values from last class
        course_id = intent.getIntExtra("courseId", -1);
        // assign values from selected term to new object
        selectedCourse = db.course_dao().getCourse(course_id);
        // view info
        nameView = findViewById(R.id.CourseView_name);
        startView = findViewById(R.id.CourseView_StartValue);
        endView = findViewById(R.id.CourseView_EndValue);
        statusView = findViewById(R.id.CourseView_StatusValue);

        ///////
        // on click listener
        Assessment_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int assessment_id;
                // call view page for term
                Intent intent = new Intent(View_Course_Activity.this, View_Assessment_Activity.class);
                // get list
                List<Assessment_Entity> assessment_list = db.assessmentDAO().getAssessmentList(course_id);
                //assign term_id variable from the object clicked
                assessment_id = assessment_list.get(position).getAssessment_id();
                // add term id to intent
                intent.putExtra("assessmentId", assessment_id);
                // complete call

                startActivity(intent);
            }
        });// end on click listener
        update();
        ListUpdate();
    }// end on create
    // menu create
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.course_menu,menu);
        return true;
    }
    //menu listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            //delete course
            case R.id.DeleteCourse:
                db.course_dao().deleteCourse(selectedCourse);
                Intent intent = new Intent(View_Course_Activity.this, Term_Activity.class);
                //assign term_id variable from the object clicked
                // add term id to intent
                intent.putExtra("TermId",selectedCourse.getTerm_id_fk());
                // complete call
                startActivity(intent);
                return true;
            case R.id.StatusPending:
                selectedCourse.setCourse_status("plan to take");
                db.course_dao().updateCourse(selectedCourse);
                statusView.setText("plan to take");
                return true;
            case R.id.StatusDropped:
                selectedCourse.setCourse_status("dropped");
                db.course_dao().updateCourse(selectedCourse);
                statusView.setText("dropped");
                return true;
            case R.id.StatusCompleted:
                selectedCourse.setCourse_status("completed");
                db.course_dao().updateCourse(selectedCourse);
                statusView.setText("completed");
                return true;
            case R.id.StatusInProgress:
                selectedCourse.setCourse_status("in progress");
                db.course_dao().updateCourse(selectedCourse);
                statusView.setText("in progress");
                return true;
            case R.id.StartDateAlert:
                String Sdate = startView.getText().toString();
                String alarmformat = "MM/dd/yy";
                SimpleDateFormat dateformat = new SimpleDateFormat(alarmformat, Locale.US);
                Date newDate = null;
                try {
                    newDate = dateformat.parse(Sdate);
                    System.out.println(dateformat.parse(Sdate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long trigger = newDate.getTime();
                Intent intentStart = new Intent(this, MyReceiver.class);
                intentStart.putExtra("Message", "Class: " + nameView.getText()
                        + " is scheduled to begin today");
                intentStart.putExtra("Channel", "channel2");
                intentStart.putExtra("Title","Class Start");
                PendingIntent sender = PendingIntent.getBroadcast(this, ++MainActivity.AlertNum , intentStart, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);


            case R.id.EndDateAlert:
                Sdate = endView.getText().toString();
                alarmformat = "MM/dd/yy";
                dateformat = new SimpleDateFormat(alarmformat, Locale.US);
                newDate = null;
                try {
                    newDate = dateformat.parse(Sdate);
                    System.out.println(dateformat.parse(Sdate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                trigger = newDate.getTime();
                Intent intentEnd = new Intent(this, MyReceiver.class);
                intentEnd.putExtra("Message", "Class: " + nameView.getText()
                        + " is scheduled to end today");
                intentEnd.putExtra("Channel", "channel3");
                intentEnd.putExtra("Title","Class End");
                sender = PendingIntent.getBroadcast(this, ++MainActivity.AlertNum , intentEnd, 0);
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        }
        return super.onOptionsItemSelected(item);
    }
    ///
    public void update()
    {
        // perform function if value was passed otherwise(else) go back to term_activity page
        if(selectedCourse != null ) {
            // get date values from object
            Date start = selectedCourse.getCourse_start();
            Date end = selectedCourse.getCourse_end();
            // format to string values using formatting conditions
            String start_string = formatter.format(start);
            String end_string = formatter.format(end);
            // assign values to text views
            nameView.setText(selectedCourse.getCourse_name());
            startView.setText(start_string);
            endView.setText(end_string);
            statusView.setText(selectedCourse.getCourse_status());

        }// end if
        else {
            // call term_activity page
            Intent intent = new Intent(View_Course_Activity.this, View_Term_Activity.class);
            startActivity(intent);
        }// end else
    }// end update
    public void ListUpdate()
    {
        // get all assessments
        List<Assessment_Entity> AllAssessments = db.assessmentDAO().getAssessmentList(selectedCourse.getCourse_id());
        String[] Assessments = new String[AllAssessments.size()];
        for(int i= 0; i < AllAssessments.size(); i++){
            Assessments[i] = AllAssessments.get(i).getAssessment_Title();
            //System.out.println("data added");
        }// end for
        // assign array to list view
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,Assessments);
        Assessment_View.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }// end list update
    public void editCourse(View view)
    {
        course_id = selectedCourse.getCourse_id();
        Intent intent = new Intent(View_Course_Activity.this, Edit_Course_Activity.class);
        //assign term_id variable from the object clicked
        // add term id to intent
        intent.putExtra("courseId",course_id);
        // complete call
        startActivity(intent);
    }// end edit course
    public void newAssessment(View view){
        // create new assessment
        Intent intent = new Intent(View_Course_Activity.this, Create_Assessment_Activity.class);
        // pass course id for foriegn key
        intent.putExtra("courseId",course_id);
        startActivity(intent);
    }// end new course
    public void MentorCall(View view){

        course_id = selectedCourse.getCourse_id();
        Intent intent = new Intent(View_Course_Activity.this, View_Mentor_Activity.class);
        //assign term_id variable from the object clicked
        // add term id to intent
        intent.putExtra("courseId",course_id);
        // complete call
        startActivity(intent);
    }// end mentor call

    public void NotesCall(View view)
    {
        course_id = selectedCourse.getCourse_id();
        Intent intent = new Intent(View_Course_Activity.this, Edit_Note_Activity.class);
        //assign term_id variable from the object clicked
        // add term id to intent
        intent.putExtra("noteId",course_id);
        // complete call
        startActivity(intent);
    }
    public void SetStartAlert(){
        String name = nameView.getText().toString();
        // convert assessment date for alert message
        Calendar cal = Calendar.getInstance();
        cal.setTime(selectedCourse.getCourse_start());

        String Title = "W.G.U Class Start Date";
        String Message = name+": has reached its Start Date";

        // create alarm manager

        Intent intent = new Intent(this, MyReceiver.class);
        intent.putExtra("Message", Message);
        intent.putExtra("Title", Title);
        intent.putExtra("IDValue", selectedCourse.getCourse_id());
        intent.putExtra("Channel", "Class_Start_Channel");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
    }
    public void SetEndAlert(){
        String name = nameView.getText().toString();
        // convert assessment date for alert message
        Calendar cal = Calendar.getInstance();
        cal.setTime(selectedCourse.getCourse_start());

        String Title = "W.G.U Class End Date";
        String Message = name+": has reached its End Date";

        // create alarm manager

        Intent intent = new Intent(this, MyReceiver.class);
        intent.putExtra("Message", Message);
        intent.putExtra("Title", Title);
        intent.putExtra("IDValue", selectedCourse.getCourse_id());
        intent.putExtra("Channel", "Class_End_Channel");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
    }
}
