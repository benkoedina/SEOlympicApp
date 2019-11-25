package com.example.seolympicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class Login extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button_login = (Button)findViewById(R.id.button_login);

        db = new DatabaseHelper(getApplicationContext());

     /*  User user1=new User(1, "benko@seolympic.com","12345" );
        User user2=new User(2, "makkai@seolympic.com","12345");
        db.createUser(user1);
        db.createUser(user2);*/

        final List<User> users = db.getAllUsers();

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText et_email = findViewById(R.id.et_email);
                EditText et_password = findViewById(R.id.et_password);

                String email = et_email.getText().toString();
                String password = et_password.getText().toString();


                if (email.isEmpty() || password.isEmpty())
                {
                    et_email.setError("All the fileds are required");
                    et_password.setError("All the fileds are required");

                    Toast.makeText(getApplicationContext(), "All the fields are required", Toast.LENGTH_SHORT).show();

                }
                else {
                    User userEdina = users.get(0);
                    Log.d("Users", userEdina.toString());
                    User userMatyas=users.get(1);

                    if(email.matches(userEdina.getEmail()) && password.matches(userEdina.getPassword()))
                    {
                        Toast.makeText(getApplicationContext(), "Hello Edina!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Menu.class);
                        intent.putExtra("Id",1);
                        startActivity(intent);
                    }
                    if(email.matches(userMatyas.getEmail()) && password.matches(userMatyas.getPassword()))

                    {
                        Toast.makeText(getApplicationContext(), "Hello Matyas!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, Menu.class);
                        intent.putExtra("Id",2);
                        startActivity(intent);
                    }
                }
            }
        });
    }



}
