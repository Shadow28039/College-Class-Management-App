package com.example.finalproject2.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalproject2.Entity.Course_Entity;
import com.example.finalproject2.Entity.Term_Entity;

import java.util.List;

@Dao
public interface Course_DAO {
    // get all courses with a tem_id_fk value matching passed value
    @Query("SELECT * FROM course_table where term_id_fk = :term_id_fk")
    List<Course_Entity> getCourseList(int term_id_fk);
    // get specific course
    @Query("SELECT * FROM course_table WHERE course_id = :ID")
    Course_Entity getCourse(int ID);
    // get all courses
    @Query("SELECT * FROM course_table ORDER BY course_id")
    List<Course_Entity> getallcourses();
    // delete all course values(nuke tables)
    @Query("Delete from course_table")
    public void courseNuke();
    // get last entered course
    @Query("SELECT * FROM course_table ORDER BY course_id DESC LIMIT 1")
    Course_Entity LastValue();

    // add course
    @Insert
    void insertCourse(Course_Entity course);
    // insert all course values(populate)
    @Insert
    void insertAllCourses(Course_Entity... course);
    // update values
    @Update
    void updateCourse(Course_Entity course);
    // delete course
    @Delete
    void deleteCourse(Course_Entity course);

}
