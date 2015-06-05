package com.ellipsonic.quickee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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


/**
 * Created by Ellipsonic PTE ltd on 5/27/2015.
 */

public class AddDescription extends Activity {
    private int PICK_IMAGE_REQUEST = 1;
    private int REQUEST_IMAGE_CAPTURE = 2;
    private int PICK_VIDEO_REQUEST = 3;
    private int PICK_AUDIO_REQUEST = 4;
    public ImageView backButton = null;
    public TextView description = null;
    public TextView des_save = null;
    public TextView Description_Name = null;
    public String selectedTerm;
    public String selectedTopic;
    public String selectedCategory;
    ImageView image;
    ImageView video;
    ImageView audio;
    String img_path_to_db;
    String video_path_to_db;
    String audio_path_to_db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_description);
        getActionBar().hide();
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        selectedCategory = activityThatCalled.getExtras().getString("selectedCategory");
        selectedTerm = activityThatCalled.getExtras().getString("selectedTerm");
        description = (EditText) findViewById(R.id.add_des);
        des_save = (TextView) findViewById(R.id.textView4);
        backButton = (ImageView) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        des_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Description_Name = (EditText) findViewById(R.id.add_des);
                if (Description_Name.getText().toString().length() > 0) {
                    //insert into db
                    TermDb termDb = new TermDb(getApplicationContext());
                    NotesTable tableinfo = new NotesTable();
                    tableinfo.topic_name = selectedTopic;
                    tableinfo.category_name = selectedCategory;
                    tableinfo.term_name = selectedTerm;
                    tableinfo.description = Description_Name.getText().toString();
                    if(img_path_to_db!=null){
                        tableinfo.image = img_path_to_db.toString();
                    }if(video_path_to_db!=null) {
                        tableinfo.video = video_path_to_db.toString();
                    }if(audio_path_to_db!=null) {
                        tableinfo.audio = audio_path_to_db.toString();
                    }
                    termDb.insert_term(tableinfo);
                    Toast.makeText(getApplicationContext(), "Term "+selectedTerm+" is Added to " +selectedCategory +" Category" , Toast.LENGTH_LONG).show();
                    Description_Name.setText("");
                    img_path_to_db="";
                    video_path_to_db="";
                    audio_path_to_db="";
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Add Description  to Save", Toast.LENGTH_LONG).show();
                }
            }
        });
        image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertWindow();
            }
        });
        video=(ImageView)findViewById(R.id.video);

        video.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CallVideoActivity();
            }
        });

        audio=(ImageView)findViewById(R.id.audio);
        audio.setOnClickListener(new View.OnClickListener(){
           public  void onClick(View v){
               CallAudioActivity();
           }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void AlertWindow() {
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
                ConfirmWindow("Video");
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
                ConfirmWindow("Audio");
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
        String fname = "Image-" + n + ".jpg";
        img_path_to_db = root + "/Quickee/Images/" + fname;
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            ConfirmWindow("Image");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ConfirmWindow(String selected) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");

        alertDialog.setMessage( selected +" Added Successfully");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            }

        });

       // Showing Alert Message
        alertDialog.show();
    }
}