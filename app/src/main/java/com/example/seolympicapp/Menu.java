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

        Intent mainIntent = getIntent();
        Bundle extras = mainIntent.getExtras();
       final int id = extras.getInt("Id");

        Button bt_notes = findViewById(R.id.button_notes);
        Button bt_clients = findViewById(R.id.button_clients);

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
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent mainIntent = getIntent();
        Bundle extras = mainIntent.getExtras();
        int id = extras.getInt("Id");
    //    Toast.makeText(this, "Id " + id, Toast.LENGTH_SHORT).show();

        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.note:
                 Intent intent = new Intent(Menu.this, NoteActivity.class);
                 intent.putExtra("Id",id);
                 startActivity(intent);
                return true;
            case R.id.contacts:
                Intent intentContact = new Intent(Menu.this, ClientsActivity.class);
                intentContact.putExtra("Id", id);
                startActivity(intentContact);
            case R.id.email:
                Intent intentEmail = new Intent(Menu.this, EmailActivity.class);
                startActivity(intentEmail);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
