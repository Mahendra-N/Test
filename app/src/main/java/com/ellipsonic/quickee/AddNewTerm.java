package com.ellipsonic.quickee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ellipsonic.database.NotesTable;
import com.ellipsonic.database.TermDb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;


public class AddNewTerm extends Activity {
    private int PICK_IMAGE_REQUEST = 1;
    private int REQUEST_IMAGE_CAPTURE = 2;
    private int PICK_VIDEO_REQUEST = 3;
    private int PICK_AUDIO_REQUEST = 4;
    public ImageButton video;
    public ImageView backButton_term;
    public TextView term_save;
    public TextView term_name;
    public EditText sel_topic;
    public EditText sel_cat;
    public EditText description;
    String selectedTopic;
    String selectedCategory;
    ImageButton image;
    ImageButton audio;
    String img_path_to_db;
    String video_path_to_db;
    String audio_path_to_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_term);
        getActionBar().hide();
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        selectedCategory =activityThatCalled.getExtras().getString("selectedCategory");
        sel_topic=(EditText)findViewById(R.id.sel_topic_name);
        sel_topic.setText(selectedTopic);
        sel_topic.setTextColor(Color.parseColor("#000000"));
        sel_topic.setEnabled(false);
        sel_cat=(EditText)findViewById(R.id.sel_cat_name);
        sel_cat.setText(selectedCategory);
        sel_cat.setTextColor(Color.parseColor("#000000"));
        sel_cat.setEnabled(false);
        backButton_term = (ImageView) findViewById(R.id.new_term_back_icon);
        video=(ImageButton)findViewById(R.id.video);

        video.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CallVideoActivity();
            }
        });
        audio=(ImageButton)findViewById(R.id.audio);
        audio.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V){
                CallAudioActivity();
            }
        });
        backButton_term.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        image=(ImageButton)findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){
                AlertWindowTopic();
            }
        });

        term_save=(TextView)findViewById(R.id.term_save);
        term_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                term_name =(EditText)findViewById(R.id.input_term_name);
                description =(EditText)findViewById(R.id.desc);
                if(term_name.getText().toString().length()>0 && description.getText().toString().length()>0 ){
                    //insert into db
                 TermDb termDb =new TermDb(getApplicationContext());
                    NotesTable tableinfo = new NotesTable();
                    tableinfo.topic_name=selectedTopic;
                    tableinfo.category_name=selectedCategory;
                    tableinfo.term_name =term_name.getText().toString();
                    tableinfo.description=description.getText().toString();
                    tableinfo.image= img_path_to_db.toString();
                    tableinfo.video=video_path_to_db.toString();
                    tableinfo.audio=audio_path_to_db.toString();
                    termDb.insert_term(tableinfo);
                    term_name.setText("");
                    description.setText("");
                    img_path_to_db="";
                    video_path_to_db="";


                     finish();

                }else{
                    Toast.makeText(getApplicationContext(), "Enter required Fields",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_term, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void AlertWindowTopic(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");

        alertDialog.setMessage("You want select picture from gallery or take a pic?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void CallVideoActivity(){

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select video"), PICK_VIDEO_REQUEST);

    }
    public void CallAudioActivity(){
        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select audio"), PICK_AUDIO_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                SaveImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            SaveImage(imageBitmap);
        }

        if (requestCode == PICK_VIDEO_REQUEST) {

            try
            {
                AssetFileDescriptor videoAsset = getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                FileInputStream fis = videoAsset.createInputStream();

                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/Quickee/Video/");
                Random generator = new Random();
                int n = 10000;
                n = generator.nextInt(n);
                String fname = "Video"+ n +".mp4";
                video_path_to_db =root + "/Quickee/Video/"+fname;
                File   file=new File(myDir,fname );

                FileOutputStream fos = new FileOutputStream(file);

                byte[] buf = new byte[1024];
                int len;
                while ((len = fis.read(buf)) > 0) {
                    fos.write(buf, 0, len);
                }
                fis.close();
                fos.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        if(requestCode==PICK_AUDIO_REQUEST){

            try
            {
                AssetFileDescriptor videoAsset = getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                FileInputStream fis = videoAsset.createInputStream();

                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/Quickee/Audio/");
                Random generator = new Random();
                int n = 10000;
                n = generator.nextInt(n);
                String fname = "Audio"+ n +".mp3";
                audio_path_to_db =root + "/Quickee/Audio/"+fname;
                File   file=new File(myDir,fname );

                FileOutputStream fos = new FileOutputStream(file);

                byte[] buf = new byte[1024];
                int len;
                while ((len = fis.read(buf)) > 0) {
                    fos.write(buf, 0, len);
                }
                fis.close();
                fos.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }



    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Quickee/Images/");
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-"+ n +".jpg";
        img_path_to_db =root + "/Quickee/Images/"+fname;
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    }


