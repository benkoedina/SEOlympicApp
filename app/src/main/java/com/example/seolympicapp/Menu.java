package com.example.seolympicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //we get the id from the intent of the LoginActivity
        Intent mainIntent = getIntent();
        Bundle extras = mainIntent.getExtras();
        final int id = extras.getInt("Id");

        Button bt_notes = findViewById(R.id.button_notes);
        Button bt_clients = findViewById(R.id.button_clients);
        Button bt_reminder = findViewById(R.id.button_reminder);
        Button bt_email = findViewById(R.id.button_email);


        //Button menus
        bt_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEmail = new Intent(Menu.this, EmailActivity.class);
                startActivity(intentEmail);
            }
        });
        bt_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReminder = new Intent( Menu.this, ReminderActivity.class);
                startActivity((intentReminder));
            }
        });

        bt_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, NoteActivity.class);
                intent.putExtra("Id",id);
                startActivity(intent);
            }
        });

        bt_clients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentContact = new Intent(Menu.this, ClientsActivity.class);
                intentContact.putExtra("Id", id);
                startActivity(intentContact);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //header Menu
    public boolean onOptionsItemSelected(MenuItem item) {

       Intent mainIntent = getIntent();
        Bundle extras = mainIntent.getExtras();
        int id = extras.getInt("Id");

        switch (item.getItemId()) {
            case R.id.note:
                Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(Menu.this, NoteActivity.class);
                 intent.putExtra("Id",id);
                 startActivity(intent);
                return true;
            case R.id.clients:
            //    Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intentContact = new Intent(Menu.this, ClientsActivity.class);
                intentContact.putExtra("Id", id);
                startActivity(intentContact);
                return true;
            case R.id.email:
                Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intentEmail = new Intent(Menu.this, EmailActivity.class);
                startActivity(intentEmail);
                return true;
            case R.id.reminder:
                Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intentReminder = new Intent( Menu.this, ReminderActivity.class);
                startActivity((intentReminder));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
