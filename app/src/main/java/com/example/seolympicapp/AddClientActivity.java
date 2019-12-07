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

        //getting the id from the ClientsActivity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final  int id = extras.getInt("id");
        Log.d("id", id+"");
        db = new DatabaseHelper(getApplicationContext());
        final  List<Client> allClientA = db.getAllClients();
        for(Client c : allClientA)
        {
            Log.d("Actual client id's", c.getId()+"");
        }
        Log.d("Id for the next client",checkMaxId(allClientA)+1+"");
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
                //the completed field contents
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String website = et_website.getText().toString();
                String tel = et_tel.getText().toString();
                String company = et_company.getText().toString();
                String address = et_address.getText().toString();
                //adding client to the database
                Client client = new Client(checkMaxId(allClientA)+1,name,email,website,tel,company,address,id);
                Log.d("The new Client", client.toString());
                db.createClient(client);
                //go back with ok resultCode to the calling Activity
                Intent intent=new Intent();
                setResult(1,intent);
            }
        });
    }
    //checks the maxId for the clients
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


}
