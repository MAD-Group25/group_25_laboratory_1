package com.example.nico.first_laboratory_vers2;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowingProfile extends AppCompatActivity {

    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showingprofile);

        preferences = getSharedPreferences("mypref", 0);
        String name= preferences.getString("n"+0, "fail name");
        int age =preferences.getInt("a"+0, 0);
        String bio = preferences.getString("b"+0, "fail bio");
        String email = preferences.getString("e"+0, "fail email");

        TextView tname = (TextView) findViewById(R.id.showname);
        TextView tage= (TextView) findViewById(R.id.showage);
        TextView tbio = (TextView) findViewById(R.id.showbio);
        TextView temail = (TextView) findViewById(R.id.showemail);

        tname.setText(name);
        tage.setText(""+age);
        tbio.setText(bio);
        temail.setText(email);
    }
}
