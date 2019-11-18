package com.example.seolympicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
