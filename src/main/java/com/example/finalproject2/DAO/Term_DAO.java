package com.example.finalproject2.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.finalproject2.Entity.Term_Entity;

import java.util.List;

@Dao
public interface Term_DAO {
    // get all terms by order of entry
    @Query("SELECT * FROM term_table ORDER BY term_id ")
    List<Term_Entity> getTermList();

    // get specific term
    @Query("SELECT * FROM term_table WHERE term_id = :ID")
    Term_Entity getTerm(int ID);

    // get all terms
    @Query("SELECT * FROM term_table")
    List<Term_Entity> getAllTerms();

    @Insert
    void insertTerm(Term_Entity term);

    @Insert
    void insertAllTerms(Term_Entity... term);

    @Update
    void updateTerm(Term_Entity term);

    @Delete
    void deleteTerm(Term_Entity term);

    // nuke terms from database
    @Query("DELETE FROM term_table")
    public void TermNuke();


}
