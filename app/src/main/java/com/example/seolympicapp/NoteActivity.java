package com.example.seolympicapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class NoteActivity extends AppCompatActivity {

    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        db = new DatabaseHelper(getApplicationContext());


      /*  Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);


        User user1 = new User(4,"benkoedina@gmail.com","12345");
        long userid = db.createUser(user1);

        Note note1= new Note(6,"Probalni az adatbazist",formattedDate,4);
        Note note2= new Note(7,"Probalni az adatbazist",formattedDate,4);

       long noteid1=db.createNote(note1);
       long noteid2 = db.createNote(note2);

        List<Note> allNotes = db.getUserNotes(4);
        for (Note note : allNotes) {
            Log.d("Tag Name", note.toString());
        }
        note1.setNote("UPDATET");
        db.updateNote(note1);
   /*    db.deleteAllNotes(4);
       // db.deleteOneNote(note1);
       List<Note> allNotes1 = db.getUserNotes(4);
       if(allNotes1.isEmpty())
        {
            Log.d("Tag Name","Nincs");
        }
        for (Note note : allNotes1) {
            //Log.d("Tag Name","Nincs");
            Log.d("Tag Name", note.toString());
        }
*/




    }


}
