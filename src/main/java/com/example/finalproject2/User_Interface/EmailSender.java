package com.example.finalproject2.User_Interface;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject2.R;

public class EmailSender extends AppCompatActivity {
    private EditText ToSender;
    private EditText Subject;
    private TextView Message;
    Intent intent;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_email_sender);
        setTitle("Email Sender");
        ToSender = findViewById(R.id.editTextEmail);
        Subject = findViewById(R.id.editTextSubject);
        Message = findViewById(R.id.NoteText);
        intent = getIntent();
        Message.setText(intent.getStringExtra("notes"));


    }// end on create
    public void sendEmail(View view){
        String emailList = ToSender.getText().toString();
        // break down emails into multiple list using , as a reference
        String[] List = emailList.split(",");
        String subject = Subject.getText().toString();
        String message = Message.getText().toString();
        //
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,List);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose a Email Client"));

        Intent intentMain = new Intent(EmailSender.this, MainActivity.class);

        startActivity(intentMain);

    }

}
