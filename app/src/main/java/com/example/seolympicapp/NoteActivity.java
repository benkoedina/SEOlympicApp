package com.example.seolympicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
    MyListAdapter adapter;
    RecyclerView recyclerView;
     List<Note> allNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        //format the date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        //database connection
        db = new DatabaseHelper(getApplicationContext());

        //get the user id
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final  int id = extras.getInt("Id");

        Button bt_addNew = findViewById(R.id.bt_add);

        //we get all the Notes of the user
        allNotes = db.getUserNotes(id);
        for (Note note : allNotes) {
            Log.d("Notes", note.toString());
        }

        //adding a new Note button
        bt_addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //open the alertDialog function
            openDialog(id);

            }});

        //RecyclerView setup
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(NoteActivity.this,DividerItemDecoration.VERTICAL));
        adapter = new MyListAdapter(allNotes,NoteActivity.this,db);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        db.closeDB();

    }

    public List<Note> allNotes(int user_id)
    {
        return db.getUserNotes(user_id);
    }

    private void openDialog(final int id)
    {
        //alertDialog view
        LayoutInflater inflater = LayoutInflater.from(NoteActivity.this);
        View subView = inflater.inflate(R.layout.alert_layout, null);

        //get the alertDialog item
        final EditText et_note = subView.findViewById(R.id.update);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Your Note");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        //adding a New Note to the database and refresh the adapter/recyclerView
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.d("Note from the EditText",et_note.getText().toString());
                Note note = new Note (checkMaxId()+1,et_note.getText().toString(),dateFormat(),id);
                db.createNote(note);
                allNotes.add(note);
                adapter.notifyDataSetChanged();

            }
        });
        builder.show();
    }

    //function to Format the Date
    private String dateFormat()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        return formattedDate;
    }

    //function to check the maxid of the notes in the database
    private int checkMaxId()
    {
        int id=0;
        for (Note note : allNotes) {
           if(note.getId()>id)
           {
               id=note.getId();
           }
        }
        return id;
    }
}
