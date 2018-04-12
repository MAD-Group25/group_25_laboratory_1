package com.example.nico.first_laboratory_vers2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button add_user, show_user, login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_user = (Button) findViewById(R.id.Bt_add_user);
        show_user = (Button) findViewById(R.id.Bt_show_user);
        login = (Button) findViewById(R.id.Bt_login);
        




        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to do when I click

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("userN");
                myRef.setValue("");

                addUser(v);

            }
        });

        show_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // to do when I click
                showUser(view);

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(loginIntent);

            }
        });
    }

    public void addUser( View view){
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);

    }

    public void  showUser(View view){
        Intent showIntent = new Intent(this, UserAreaActivity.class);
        startActivity(showIntent);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }




}
