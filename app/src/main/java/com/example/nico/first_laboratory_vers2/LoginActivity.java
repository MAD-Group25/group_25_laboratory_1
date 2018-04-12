package com.example.nico.first_laboratory_vers2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText password;
    private EditText name;
    private Button login;
    private TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        password = (EditText) findViewById(R.id.Et_password);
        name = (EditText) findViewById(R.id.Et_name);
        login = (Button) findViewById(R.id.Bt_login);
        registerLink = (TextView) findViewById(R.id.Tv_register);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Here we create an intent that open the RegisterActivity and

                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });






    }
}
