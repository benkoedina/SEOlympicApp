package com.example.seolympicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class AddClientActivity extends AppCompatActivity {


    DatabaseHelper db;
   // List<Client> allClients;
    EditText et_name;
    EditText et_email;
    EditText et_website;
    EditText et_tel ;
    EditText et_company;
    EditText et_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final  int id = extras.getInt("id");
        Log.d("id", id+"");

        db = new DatabaseHelper(getApplicationContext());
       // db.deleteAllClients(1);
        //
   //     final List<Client> allClients = db.getUserClients(id);
      final  List<Client> allClientA = db.getAllClients();
       for(Client c : allClientA)
       {
           Log.d("client id", c.getId()+"");
       }

       Log.d("client",checkMaxId(allClientA)+1+"");

       et_name = findViewById(R.id.et_name);
       et_email = findViewById(R.id.et_email);
       et_website = findViewById(R.id.et_website);
       et_tel = findViewById(R.id.et_tel);
       et_company =findViewById(R.id.et_company);
       et_address = findViewById(R.id.et_address);




        Button bt_addClient = findViewById(R.id.bt_addClientForm);
        bt_addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String website = et_website.getText().toString();
                String tel = et_tel.getText().toString();
                String company = et_company.getText().toString();
                String address = et_address.getText().toString();

                Client client = new Client(checkMaxId(allClientA)+1,name,email,website,tel,company,address,id);
                Log.d("addC", client.toString());
                db.createClient(client);
                Log.d("addC",db.getAClient(id).toString());
                Intent intent=new Intent();
                intent.putExtra("MESSAGE","Sikerult");
                setResult(1,intent);
            }
        });


    }
    private int checkMaxId(List<Client> allClients)
    {
        int id=0;
        for (Client c: allClients) {
            if(c.getId()>id)
            {
                id=c.getId();
            }
        }
        return id;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
