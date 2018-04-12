package com.example.nico.first_laboratory_vers2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {


    private static int RESULT_LOAD_IMAGE = 0;
    private EditText name, email, bio, age , password;
    SharedPreferences preferences;
    int pos;
    private Button register, back;
    private ImageButton buttonLoadImage;
    private Bitmap bitmap1,bitmap2;
    private static final String IMAGE_DIRECTORY = "/MyNewImages";
    private int GALLERY = 0, CAMERA = 1;
    private ImageView image1, image2, imageToPass;
    private String simage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imageToPass = (ImageView) findViewById(R.id.Iv_image);

        //Intent intent = getIntent();
        preferences=getSharedPreferences("mypref", MODE_PRIVATE);
        register = findViewById(R.id.Bt_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerProfile(view);


//                Intent passintent = new Intent(RegisterActivity.this, UserAreaActivity.class);
//                passintent.putExtras("value", name.getText().toString());
//                startActivity(passintent);
                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        back = (Button) findViewById(R.id.Bt_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rback(view);
            }
        });

//#####################CODICE PER ACQUISIRE IMMAGINE DA FOTOCAMERA#############################

        buttonLoadImage = (ImageButton) findViewById(R.id.Ib_set_image);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });


}

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        image1 = findViewById(R.id.Iv_image);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap1);
                    Toast.makeText(RegisterActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    image1.setImageBitmap(bitmap1);
                    simage=savingImage(bitmap1);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }


        } else if (requestCode == CAMERA) {

            image2 = (ImageView) findViewById(R.id.Iv_image);
            bitmap2 = (Bitmap) data.getExtras().get("data");
            image2.setImageBitmap(bitmap2);
            Toast.makeText(RegisterActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            simage=savingImage(bitmap2);
        }
    }

    public static String savingImage(Bitmap bitmap){
        Bitmap image = bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    public void registerProfile (View view){


        preferences=getSharedPreferences("mypref", 0);
        pos=preferences.getInt("pos", 0);
        name = findViewById(R.id.Et_name);
        email = findViewById(R.id.Et_mail);
        bio = findViewById(R.id.Et_info);
        age = findViewById(R.id.Et_age);
        password = findViewById(R.id.Et_password);
        String sname= name.getText().toString();
        String semail = email.getText().toString();
        String sbio = bio.getText().toString();
        String sage = age.getText().toString();
        String spass = password.getText().toString();
        int iage =Integer.parseInt(sage);
        SharedPreferences.Editor ed = preferences.edit();
        ed.putString("n"+pos, sname);
        ed.putString("e"+pos, semail);
        ed.putString("b"+pos, sbio);
        ed.putString("i"+pos, simage);
//        ed.putString("i"+pos, simage);
        ed.putInt("a"+pos, iage);
        pos++;
        ed.putInt("pos",pos);
        ed.putString("p"+pos, spass);
        ed.commit();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefname = database.getReference("/userN/name");
        DatabaseReference myRefmail = database.getReference("/userN/mail");
        DatabaseReference myRefage = database.getReference("/userN/age");
        DatabaseReference myRefbio = database.getReference("/userN/bio");
        


        myRefname.setValue(sname);
        myRefmail.setValue(semail);
        myRefbio.setValue(sbio);
        myRefage.setValue(sage);



//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("/users/u1/name");
//        myRef.setValue(sname);
    }

    public void rback(View view){
        finish();
    }





}



