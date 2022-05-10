package com.example.finalproject2.User_Interface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Database.WGU_Database;
import com.example.finalproject2.Entity.Term_Entity;
import com.example.finalproject2.R;

import java.util.List;

public class Term_Activity extends AppCompatActivity {
    ListView listView;
    WGU_Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        setTitle("Terms");
        listView = findViewById(R.id.term_list_view);
        db = WGU_Database.getInstance(getApplicationContext());
        //listener for list click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int term_id;
                // call view page for term
                Intent intent = new Intent(Term_Activity.this, View_Term_Activity.class);
                // get list
                List<Term_Entity> term_list =db.term_dao().getTermList();
                //assign term_id variable from the object clicked
                term_id = term_list.get(position).getTerm_id();
                // add term id to intent
                intent.putExtra("termId", term_id);
                // complete call
                startActivity(intent);

            }
        });// end on click

        //
        refresh_list();

    }

    public void Create_Term_PageCall(View view){
        // call TermPage
        Intent intent = new Intent(Term_Activity.this, Create_Term_Activity.class);
        startActivity(intent);

    }
    // populate and refresh data for list
    public void refresh_list(){
        List<Term_Entity> Allterm = db.term_dao().getTermList();

        String[] terms = new String[Allterm.size()];
        //if Allterm is not empty run if statement
        if(!Allterm.isEmpty()){
            for(int i= 0; i < Allterm.size(); i++){
                terms[i] = Allterm.get(i).getTerm_name();
                //System.out.println("data added");
            }// end for
        }// end if

        // assign array to list view
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,terms);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

}

