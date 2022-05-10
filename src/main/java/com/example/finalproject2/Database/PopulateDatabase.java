package com.example.finalproject2.Database;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject2.Entity.Assessment_Entity;
import com.example.finalproject2.Entity.Course_Entity;
import com.example.finalproject2.Entity.Mentor_Entity;
import com.example.finalproject2.Entity.Term_Entity;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Date;
import java.util.List;

public class PopulateDatabase{
    public static String Log_Tag = "popDatabase";
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    // term
    Term_Entity tempT1 = new Term_Entity();
    Term_Entity tempT2 = new Term_Entity();
    Term_Entity tempT3 = new Term_Entity();
    //course
    Course_Entity tempC1 = new Course_Entity();
    Course_Entity tempC2 = new Course_Entity();
    Course_Entity tempC3 = new Course_Entity();
    Course_Entity tempC4 = new Course_Entity();
    Course_Entity tempC5 = new Course_Entity();
    Course_Entity tempC6 = new Course_Entity();
    Course_Entity tempC7 = new Course_Entity();
    Course_Entity tempC8 = new Course_Entity();
    Course_Entity tempC9 = new Course_Entity();
    //mentor
    Mentor_Entity tempM1 = new Mentor_Entity();
    Mentor_Entity tempM2 = new Mentor_Entity();
    Mentor_Entity tempM3 = new Mentor_Entity();
    Mentor_Entity tempM4 = new Mentor_Entity();
    Mentor_Entity tempM5 = new Mentor_Entity();
    Mentor_Entity tempM6 = new Mentor_Entity();
    Mentor_Entity tempM7 = new Mentor_Entity();
    Mentor_Entity tempM8 = new Mentor_Entity();
    Mentor_Entity tempM9 = new Mentor_Entity();
    // assessment
    Assessment_Entity tempA1 = new Assessment_Entity();
    Assessment_Entity tempA2 = new Assessment_Entity();
    Assessment_Entity tempA3 = new Assessment_Entity();
    Assessment_Entity tempA4 = new Assessment_Entity();
    Assessment_Entity tempA5 = new Assessment_Entity();
    Assessment_Entity tempA6 = new Assessment_Entity();
    Assessment_Entity tempA7 = new Assessment_Entity();
    Assessment_Entity tempA8 = new Assessment_Entity();
    Assessment_Entity tempA9 = new Assessment_Entity();
    // database
    WGU_Database db;

    public void populate(Context context){
        db = WGU_Database.getInstance(context);
        // logic check list for data population.
        List<Term_Entity> term_count = db.term_dao().getTermList();
        try{
            // only populate table if no data is present in table
            if(term_count.isEmpty()) {
                Insert_Terms();
                Insert_Courses();
                Insert_Mentor();
                Insert_Assessment();
                System.out.println("data populate");
                // clear term_count after for populate after purge
                term_count.clear();
            }// end if

        }// end try
        catch (Exception e){
            e.printStackTrace();
            Log.d(Log_Tag,"populate DB Failed");
        }// end catch
    }// end populate

    private void Insert_Terms(){
        // Term 1
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        // increase end date by 3 months
        end.add(Calendar.MONTH,3);
        //
        tempT1.setTerm_name("Term 1");
        tempT1.setTerm_start(start.getTime());
        tempT1.setTerm_end(end.getTime());
        // Term 2
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        //
        start.add(Calendar.MONTH,4);
        end.add(Calendar.MONTH,6);
        //
        tempT2.setTerm_name("Term 2");
        tempT2.setTerm_start(start.getTime());
        tempT2.setTerm_end(end.getTime());
        // Term 3
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        //
        start.add(Calendar.MONTH,7);
        end.add(Calendar.MONTH,10);
        //
        tempT3.setTerm_name("Term 3");
        tempT3.setTerm_start(start.getTime());
        tempT3.setTerm_end(end.getTime());
        // fill database
        db.term_dao().insertAllTerms(tempT1,tempT2,tempT3);
    }// end insert terms

    private void Insert_Mentor()
    {
        List<Course_Entity> Mlist = db.course_dao().getallcourses();
        tempM1.setCourse_id_fk(Mlist.get(0).getCourse_id());
        tempM1.setMentor_name("Mentor 1");
        tempM1.setMentor_phone("336-546-8768");
        tempM1.setMentor_email("tempdsffd1@gmail.com");
        //
        tempM2.setCourse_id_fk(Mlist.get(1).getCourse_id());
        tempM2.setMentor_name("Mentor 2");
        tempM2.setMentor_phone("336-678-5468");
        tempM2.setMentor_email("temp2fgtersc@gmail.com");
        //
        tempM3.setCourse_id_fk(Mlist.get(2).getCourse_id());
        tempM3.setMentor_name("Mentor 3");
        tempM3.setMentor_phone("336-876-3468");
        tempM3.setMentor_email("temp3sffd1@gmail.com");
        //
        tempM4.setCourse_id_fk(Mlist.get(3).getCourse_id());
        tempM4.setMentor_name("Mentor 4");
        tempM4.setMentor_phone("336-678-3268");
        tempM4.setMentor_email("temp4sffd1@gmail.com");
        //
        tempM5.setCourse_id_fk(Mlist.get(4).getCourse_id());
        tempM5.setMentor_name("Mentor 5");
        tempM5.setMentor_phone("336-567-2468");
        tempM5.setMentor_email("temp5sffd1@gmail.com");
        //
        tempM6.setCourse_id_fk(Mlist.get(5).getCourse_id());
        tempM6.setMentor_name("Mentor 6");
        tempM6.setMentor_phone("336-789-7868");
        tempM6.setMentor_email("temp6sffd1@gmail.com");
        //
        tempM7.setCourse_id_fk(Mlist.get(6).getCourse_id());
        tempM7.setMentor_name("Mentor 7");
        tempM7.setMentor_phone("336-789-4568");
        tempM7.setMentor_email("temp7sffd1@gmail.com");
        //
        tempM8.setCourse_id_fk(Mlist.get(7).getCourse_id());
        tempM8.setMentor_name("Mentor 8");
        tempM8.setMentor_phone("336-545-8968");
        tempM8.setMentor_email("temp8sffd1@gmail.com");
        //
        tempM9.setCourse_id_fk(Mlist.get(8).getCourse_id());
        tempM9.setMentor_name("Mentor 9");
        tempM9.setMentor_phone("336-456-9068");
        tempM9.setMentor_email("temp9sffd1@gmail.com");
        db.mentor_dao().insertAllMentors(tempM1,tempM2,tempM3,tempM4,tempM5,tempM6,tempM7,tempM8,tempM9);
    }// end insert mentor
    private void Insert_Assessment()
    {
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MONTH,3);
        List<Course_Entity> Alist = db.course_dao().getallcourses();
        //
        tempA1.setCourse_id_fk(Alist.get(0).getCourse_id());
        tempA1.setAssessment_Type("performance");
        tempA1.setAssessment_Title("inventory SQL System");
        tempA1.setAssessment_Due_date(end.getTime());
        tempA1.setAssessment_Info("SQL");
        System.out.println(String.valueOf(end));
        //
        end = Calendar.getInstance();
        end.add(Calendar.MONTH,4);
        tempA2.setCourse_id_fk(Alist.get(1).getCourse_id());
        tempA2.setAssessment_Type("performance");
        tempA2.setAssessment_Title("inventory HTML System");
        tempA2.setAssessment_Due_date(end.getTime());
        tempA2.setAssessment_Info("HTML");
        //
        end = Calendar.getInstance();
        end.add(Calendar.MONTH,5);
        tempA3.setCourse_id_fk(Alist.get(2).getCourse_id());
        tempA3.setAssessment_Type("performance");
        tempA3.setAssessment_Title("inventory javaFX System");
        tempA3.setAssessment_Due_date(end.getTime());
        tempA3.setAssessment_Info("JAVAFX");
        //
        end = Calendar.getInstance();
        end.add(Calendar.MONTH,6);
        tempA4.setCourse_id_fk(Alist.get(3).getCourse_id());
        tempA4.setAssessment_Type("performance");
        tempA4.setAssessment_Title("inventory JAVA System");
        tempA4.setAssessment_Due_date(end.getTime());
        tempA4.setAssessment_Info("JAVA");
        //
        end = Calendar.getInstance();
        end.add(Calendar.MONTH,7);
        tempA5.setCourse_id_fk(Alist.get(4).getCourse_id());
        tempA5.setAssessment_Type("performance");
        tempA5.setAssessment_Title("inventory HTML System");
        tempA5.setAssessment_Due_date(end.getTime());
        tempA5.setAssessment_Info("HTML");
        //
        end = Calendar.getInstance();
        end.add(Calendar.MONTH,8);
        tempA6.setCourse_id_fk(Alist.get(5).getCourse_id());
        tempA6.setAssessment_Type("performance");
        tempA6.setAssessment_Title("inventory Android System");
        tempA6.setAssessment_Due_date(end.getTime());
        tempA6.setAssessment_Info("Android");
        //
        end = Calendar.getInstance();
        end.add(Calendar.MONTH,9);
        tempA7.setCourse_id_fk(Alist.get(6).getCourse_id());
        tempA7.setAssessment_Type("performance");
        tempA7.setAssessment_Title("Management HTML System");
        tempA7.setAssessment_Due_date(end.getTime());
        tempA7.setAssessment_Info("Management");
        //
        end = Calendar.getInstance();
        end.add(Calendar.MONTH,10);
        tempA8.setCourse_id_fk(Alist.get(7).getCourse_id());
        tempA8.setAssessment_Type("performance");
        tempA8.setAssessment_Title("SQL HTML System");
        tempA8.setAssessment_Due_date(end.getTime());
        tempA8.setAssessment_Info("HTML 3");
        //
        end = Calendar.getInstance();
        end.add(Calendar.MONTH,12);
        tempA9.setCourse_id_fk(Alist.get(8).getCourse_id());
        tempA9.setAssessment_Type("performance");
        tempA9.setAssessment_Title("SQL JAVAFX System");
        tempA9.setAssessment_Due_date(end.getTime());
        tempA9.setAssessment_Info("HTML 2");
        // populate
        db.assessmentDAO().insertAllAssessment(tempA1,tempA2,tempA3,tempA4,
                tempA5,tempA6,tempA7,tempA8,tempA9);

    }
    ////************************************************
    private void Insert_Courses(){

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        List<Term_Entity> Tlist = db.term_dao().getTermList();
        // increase end date by 3 months
        end.add(Calendar.MONTH,3);
        //
        //term 1(0)
        tempC1.setTerm_id_fk(Tlist.get(0).getTerm_id());
        tempC1.setCourse_name("Course 1");
        tempC1.setCourse_start(start.getTime());
        tempC1.setCourse_end(end.getTime());
        tempC1.setCourse_status("plan to take");
        tempC1.setCourse_notes("pending notes");
        tempC1.setCourse_alert_date(end.getTime());
        //
        tempC2.setTerm_id_fk(Tlist.get(0).getTerm_id());
        tempC2.setCourse_name("Course 2");
        tempC2.setCourse_start(start.getTime());
        tempC2.setCourse_end(end.getTime());
        tempC2.setCourse_status("plan to take");
        tempC2.setCourse_notes("pending notes 2");
        tempC2.setCourse_alert_date(end.getTime());
        //
        tempC3.setTerm_id_fk(Tlist.get(0).getTerm_id());
        tempC3.setCourse_name("Course 3");
        tempC3.setCourse_start(start.getTime());
        tempC3.setCourse_end(end.getTime());
        tempC3.setCourse_status("plan to take");
        tempC3.setCourse_notes("pending notes 3");
        tempC3.setCourse_alert_date(end.getTime());
        // term 2 (1)
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        //
        start.add(Calendar.MONTH,4);
        end.add(Calendar.MONTH,6);
        tempC4.setTerm_id_fk(Tlist.get(1).getTerm_id());
        tempC4.setCourse_name("Course 4");
        tempC4.setCourse_start(start.getTime());
        tempC4.setCourse_end(end.getTime());
        tempC4.setCourse_status("plan to take");
        tempC4.setCourse_notes("pending notes 4");
        tempC4.setCourse_alert_date(end.getTime());
        //
        tempC5.setTerm_id_fk(Tlist.get(1).getTerm_id());
        tempC5.setCourse_name("Course 5");
        tempC5.setCourse_start(start.getTime());
        tempC5.setCourse_end(end.getTime());
        tempC5.setCourse_status("plan to take");
        tempC5.setCourse_notes("pending notes 5");
        tempC5.setCourse_alert_date(end.getTime());
        //
        tempC6.setTerm_id_fk(Tlist.get(1).getTerm_id());
        tempC6.setCourse_name("Course 6");
        tempC6.setCourse_start(start.getTime());
        tempC6.setCourse_end(end.getTime());
        tempC6.setCourse_status("plan to take");
        tempC6.setCourse_notes("pending notes 6");
        tempC6.setCourse_alert_date(end.getTime());
        // term 3 (2)
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        //
        start.add(Calendar.MONTH,7);
        end.add(Calendar.MONTH,10);
        tempC7.setTerm_id_fk(Tlist.get(2).getTerm_id());
        tempC7.setCourse_name("Course 7");
        tempC7.setCourse_start(start.getTime());
        tempC7.setCourse_end(end.getTime());
        tempC7.setCourse_status("plan to take");
        tempC7.setCourse_notes("pending notes 7");
        tempC7.setCourse_alert_date(end.getTime());
        //
        tempC8.setTerm_id_fk(Tlist.get(2).getTerm_id());
        tempC8.setCourse_name("Course 8");
        tempC8.setCourse_start(start.getTime());
        tempC8.setCourse_end(end.getTime());
        tempC8.setCourse_status("plan to take");
        tempC8.setCourse_notes("pending notes 8");
        tempC8.setCourse_alert_date(end.getTime());
        //
        tempC9.setTerm_id_fk(Tlist.get(2).getTerm_id());
        tempC9.setCourse_name("Course 9");
        tempC9.setCourse_start(start.getTime());
        tempC9.setCourse_end(end.getTime());
        tempC9.setCourse_status("plan to take");
        tempC9.setCourse_notes("pending notes 9");
        tempC9.setCourse_alert_date(end.getTime());

        db.course_dao().insertAllCourses(tempC1,tempC2,tempC3,tempC4,tempC5,tempC6,
                tempC7,tempC8,tempC9);

    }// end course populate
}// end class


