package com.example.nico.first_laboratory_vers2;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;


public class UserAreaActivity extends AppCompatActivity {


    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bitmap imageBit;
        setContentView(R.layout.activity_user_area);

        Bundle bundle = getIntent().getExtras();


        preferences = getSharedPreferences("mypref", 0);
        String name= preferences.getString("n"+0, "fail name");
        int age =preferences.getInt("a"+0, 0);
        String bio = preferences.getString("b"+0, "fail bio");
        String email = preferences.getString("e"+0, "fail email");
        String image = preferences.getString("i"+0, "fail image");

        TextView tname = (TextView) findViewById(R.id.showname);
        TextView tage= (TextView) findViewById(R.id.showage);
        TextView tbio = (TextView) findViewById(R.id.showbio);
        TextView temail = (TextView) findViewById(R.id.showemail);
        ImageView iimage =  (ImageView) findViewById(R.id.Iv_image);

        tname.setText(name);
        tage.setText(""+age);
        tbio.setText(bio);
        temail.setText(email);
        if (image.compareTo("fail image")!=0){
            imageBit = decode(image);
            iimage.setImageBitmap(imageBit);
        }
//        iimage.setImageBitmap(bitmap);
    }

    public static Bitmap decode(String input){
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}