package com.example.seolympicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ClientsActivity extends AppCompatActivity {

   DatabaseHelper db;
   List<Client> allClients;
    RecyclerView recyclerView;
    MyClientListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);

        db = new DatabaseHelper(getApplicationContext());
        db.deleteAllClients();

        Client client = new Client(1,"Nick","nick@nick.com","www.nick.com","0745113408","Nick agency","UK",1);
        Client client1 = new Client(2,"Nick","nick@nick.com","www.nick.com","0745113408","Nick agency","UK",1);

        db.createClient(client);
        db.createClient(client1);

        Button bt_add = findViewById(R.id.bt_addContact);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final  int id = extras.getInt("Id");

        allClients =  db.getUserClients(id);
        for(Client c:allClients)
        {
            Log.d("ActualClients",c.toString());
        }

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClientsActivity.this,AddClientActivity.class);
                intent.putExtra("Id", id);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewClients);
        recyclerView.addItemDecoration(new DividerItemDecoration(ClientsActivity.this,DividerItemDecoration.VERTICAL));
        adapter = new MyClientListAdapter(allClients,ClientsActivity.this,db);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        db.closeDB();


    }
}
