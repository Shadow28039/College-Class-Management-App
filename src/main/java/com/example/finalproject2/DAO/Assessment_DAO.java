package com.example.finalproject2.DAO;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalproject2.Entity.Assessment_Entity;

@Dao
public interface Assessment_DAO {
    @Query("SELECT * FROM assessment_table where course_id_fk = :course_id_fk")
    List<Assessment_Entity> getAssessmentList(int course_id_fk);
    //
    @Query("SELECT * FROM assessment_table WHERE assessment_id = :assessment_id")
    Assessment_Entity getAssessment(int assessment_id);
    //
    @Query("Delete from assessment_table")
    public void AssessmentNuke();
    //
    @Insert
    void insertAssessment(Assessment_Entity assessment);
    @Insert
    void insertAllAssessment(Assessment_Entity ... assessment);
    // update values
    @Update
    void updateAssessment(Assessment_Entity assessment);
    // delete course
    @Delete
    void deleteAssessment(Assessment_Entity assessment);
}
