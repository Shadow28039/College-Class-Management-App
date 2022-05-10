package com.example.finalproject2.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "assessment_table",
        foreignKeys = @ForeignKey(
                entity = Course_Entity.class,
                parentColumns = "course_id",
                childColumns = "course_id_fk",
                onDelete = CASCADE))
public class Assessment_Entity {

    //
    @PrimaryKey(autoGenerate = true)
    private int assessment_id;
    @ColumnInfo(name = "course_id_fk")
    private int course_id_fk;
    @ColumnInfo(name = "Assessment_Type")
    private String assessment_Type;
    @ColumnInfo(name = "Assessment_Title")
    private String assessment_Title;
    @ColumnInfo(name = "Assessment_Due_date")
    private Date assessment_Due_date;
    @ColumnInfo(name = "Assessment_Info")
    private String assessment_Info;
    ////////////////////////
    public int getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(int assessment_id) {
        this.assessment_id = assessment_id;
    }

    public int getCourse_id_fk() {
        return course_id_fk;
    }

    public void setCourse_id_fk(int course_id_fk) {
        this.course_id_fk = course_id_fk;
    }

    public String getAssessment_Type() {
        return assessment_Type;
    }

    public void setAssessment_Type(String assessment_type) {
        this.assessment_Type = assessment_type;
    }

    public String getAssessment_Title() {
        return assessment_Title;
    }

    public void setAssessment_Title(String assessment_title) {
        this.assessment_Title = assessment_title;
    }

    public Date getAssessment_Due_date() {
        return assessment_Due_date;
    }

    public void setAssessment_Due_date(Date assessment_due_date) {
        this.assessment_Due_date = assessment_due_date;
    }

    public String getAssessment_Info() {
        return assessment_Info;
    }

    public void setAssessment_Info(String assessment_info) {
        this.assessment_Info = assessment_info;
    }
    

}// end assessment
