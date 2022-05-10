package com.example.finalproject2.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalproject2.Entity.Course_Entity;
import com.example.finalproject2.Entity.Mentor_Entity;
import java.util.List;

@Dao
public interface Mentor_DAO {
// get mentor data by specific course_id

    @Query("SELECT * FROM mentor_table where course_id_fk = :course_id_fk")
    Mentor_Entity getMentor(int course_id_fk);

    @Query("SELECT * FROM mentor_table Order by mentor_id")
    List<Mentor_Entity> getMentorList();
    // delete all course values(nuke tables)
    // get last entered course
    @Query("SELECT * FROM mentor_table ORDER BY mentor_id DESC LIMIT 1")
    Mentor_Entity LastValue();

    @Query("Delete from mentor_table")
    public void MentorNuke();
    @Insert
    void insertMentor(Mentor_Entity mentor);
    @Insert
    void insertAllMentors(Mentor_Entity ... mentor);
    // update values
    @Update
    void updateMentors(Mentor_Entity mentor);
    // delete course
    @Delete
    void deleteMentor(Mentor_Entity mentor);
}
