package com.example.seolympicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddClientActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        EditText et_name = findViewById(R.id.et_name);
        EditText et_email = findViewById(R.id.et_email);
        EditText et_website = findViewById(R.id.et_website);
        EditText et_tel = findViewById(R.id.et_tel);
        EditText et_company =findViewById(R.id.et_company);
        EditText et_address = findViewById(R.id.et_address);

        Button bt_addClient = findViewById(R.id.bt_addClientForm);
        bt_addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}
