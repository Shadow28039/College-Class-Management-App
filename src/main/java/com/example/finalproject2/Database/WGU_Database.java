package com.example.finalproject2.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.finalproject2.DAO.Assessment_DAO;
import com.example.finalproject2.DAO.Course_DAO;
import com.example.finalproject2.DAO.Mentor_DAO;
import com.example.finalproject2.DAO.Term_DAO;
import com.example.finalproject2.Entity.Assessment_Entity;
import com.example.finalproject2.Entity.Course_Entity;
import com.example.finalproject2.Entity.Mentor_Entity;
import com.example.finalproject2.Entity.Term_Entity;

@Database(entities = {Term_Entity.class, Course_Entity.class, Mentor_Entity.class, Assessment_Entity.class},
        exportSchema = false,version =1)
@TypeConverters({Converter.class})

public abstract class WGU_Database extends RoomDatabase {
    private static final String DataB_Name = "full_db";
    private static WGU_Database instance;

    public static synchronized WGU_Database getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WGU_Database.class, DataB_Name).allowMainThreadQueries().build();
        }//end if
        return instance;
    }// end synchronized
    public abstract Term_DAO term_dao();
    public abstract Course_DAO course_dao();
    public abstract Mentor_DAO mentor_dao();
    public abstract Assessment_DAO assessmentDAO();

}// end abstract class