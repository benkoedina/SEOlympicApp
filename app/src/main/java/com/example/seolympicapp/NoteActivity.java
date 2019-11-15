package com.example.seolympicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

        final List<Note> allNotes = db.getUserNotes(1);
        for (Note note : allNotes) {
            Log.d("Tag Name", note.toString());
        }
        bt_addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CustomAlertDialog.showOpenDialog(NoteActivity.this,db,adapter);
                allNotes.add(CustomAlertDialog.noteR);
                adapter.notifyDataSetChanged();

            } });



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

}
