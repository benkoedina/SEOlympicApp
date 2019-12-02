package com.example.seolympicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);

        db = new DatabaseHelper(getApplicationContext());
        /*
        //we use this if we check the emulator of the device
        db = new DatabaseHelper(getApplicationContext());
        db.deleteAllClients();
        Client client = new Client(1,"Nick","nick@nick.com","www.nick.com","0745113408","Nick agency","UK",1);
        Client client1 = new Client(2,"Nick","nick@nick.com","www.nick.com","0745113408","Nick agency","UK",1);
        db.createClient(client);
        db.createClient(client1);*/

        Button bt_add = findViewById(R.id.bt_addContact);

        //we get the user id from the MenuActivity
        Intent intentGet = getIntent();
        Bundle extras = intentGet.getExtras();
        id = extras.getInt("Id");

        //we get all the inserted clients of the user
        allClients =  db.getUserClients(id);
        for(Client c:allClients)
        {
            Log.d("InsertedClients",c.toString());
        }

        //button to add a new client
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the add client activity with resultcode
                Intent intent = new Intent(ClientsActivity.this, AddClientActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent,1);
            }
        });

        //recyclerview setup
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewClients);
        recyclerView.addItemDecoration(new DividerItemDecoration(ClientsActivity.this,DividerItemDecoration.VERTICAL));
        adapter = new MyClientListAdapter(allClients,ClientsActivity.this,db);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        db.closeDB();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //checking the returned requestcode from the resultactivity
        if(requestCode==1)
        {
            Log.d("Request Code:",requestCode + "");
            allClients = db.getUserClients(id);
            for(Client c:allClients)
                {
                    Log.d("List after insert",c.toString());
                }

            //adapter and recycler view refresh
            adapter = new MyClientListAdapter(allClients,ClientsActivity.this,db);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }


}
