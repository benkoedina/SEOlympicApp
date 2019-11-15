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

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        db = new DatabaseHelper(getApplicationContext());
        db.deleteAllUser();
        User user = new User (1,"benkoedina98@gmail.com","12345" );

        Button bt_addNew = findViewById(R.id.bt_add);

        allNotes = db.getUserNotes(1);
        for (Note note : allNotes) {
            Log.d("Tag Name", note.toString());
        }
        bt_addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            openDialog();

            }});


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

    private void openDialog()
    {

        LayoutInflater inflater = LayoutInflater.from(NoteActivity.this);
        View subView = inflater.inflate(R.layout.alert_layout, null);

        final EditText et_note = subView.findViewById(R.id.update);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Your Note");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Note",et_note.getText().toString());
                Note note = new Note (checkMaxId()+1,et_note.getText().toString(),dateFormat(),1);
                db.createNote(note);
                allNotes.add(note);
                adapter.notifyDataSetChanged();

            }
        });
        builder.show();
    }

    private String dateFormat()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        final String formattedDate = df.format(c);

        return formattedDate;
    }

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
