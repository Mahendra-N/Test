package com.ellipsonic.quickee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ellipsonic.database.CategoryDb;
import com.ellipsonic.database.NotesTable;
import com.ellipsonic.database.TermDb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class AddNewTerm extends Activity {
    private int PICK_IMAGE_REQUEST = 1;
    private int REQUEST_IMAGE_CAPTURE = 2;
    private int PICK_VIDEO_REQUEST = 3;
    private int PICK_AUDIO_REQUEST = 4;
    private int REQUEST_Video_CAPTURE = 6;
    public ImageButton backButton_term;
    public Button term_save;
    public EditText term_name;
    public EditText sel_topic;
    public EditText button;
     public EditText description;
    public  String selectedTopic;
    public  String selectedCategory;
    public  String selectedTerm;
    Spinner spinner;
    public TermDb term_Db=null;
    public ArrayList<String> TermList=null;
    public CategoryDb cat_Db=null;
    ArrayAdapter<String> Adapter;
    ArrayList<String> CatList=null;

    ImageView image;
    ImageView video;
    ImageView audio;
    String img_path_to_db;
    String video_path_to_db;
    String audio_path_to_db;
    String desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_term);
        getActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent activityThatCalled = getIntent();
        ScrollView scrollView= (ScrollView)findViewById(R.id.container);

        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        selectedCategory =activityThatCalled.getExtras().getString("selectedCategory");
        sel_topic=(EditText)findViewById(R.id.sel_topic_name);
        sel_topic.setText(selectedTopic);
        sel_topic.setTextColor(Color.parseColor("#000000"));
        sel_topic.setEnabled(false);
        CategoryDropDown(selectedTopic);
        backButton_term = (ImageButton) findViewById(R.id.new_term_back_icon);
        backButton_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      term_save = (Button) findViewById(R.id.Save_term);

        term_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                term_Db=new TermDb(getApplicationContext());
                selectedCategory= String.valueOf(spinner.getSelectedItem());
                term_name = (EditText) findViewById(R.id.input_term_name);
                selectedTerm = term_name.getText().toString().trim();

                           if (selectedTerm.length()> 0 && button.getText().length()>0) {

                 TermList =  term_Db.RowsAffetedInTerm(selectedTopic,selectedCategory,selectedTerm);
                    if(TermList.size()<=0){

                        //insert into db

                        NotesTable tableinfo = new NotesTable();
                        tableinfo.topic_name = selectedTopic;
                        tableinfo.category_name = selectedCategory;
                        tableinfo.term_name = selectedTerm;
                        tableinfo.description = desc;
                        if(img_path_to_db!=null){
                            tableinfo.image = img_path_to_db.toString();
                        }if(video_path_to_db!=null) {
                            tableinfo.video = video_path_to_db.toString();
                        }if(audio_path_to_db!=null) {
                            tableinfo.audio = audio_path_to_db.toString();
                        }
                        term_Db.insert_term(tableinfo);
                        Toast.makeText(getApplicationContext(), "Term "+selectedTerm+" is Added to " +selectedCategory +" Category" , Toast.LENGTH_LONG).show();
                        button.setText("");
                        img_path_to_db="";
                        video_path_to_db="";
                        audio_path_to_db="";
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Term "+selectedTerm+" Exists,Please Enter Unique Term Name",
                                Toast.LENGTH_LONG).show();
                    }

                } else{
                    Toast.makeText(getApplicationContext(), "Enter Required Fields",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


        button = (EditText) findViewById(R.id.desc_next_page);
        button.setFocusable(false);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                Intent intent = new Intent(v.getContext(), AddDescription.class);
                final int result = 5;
                intent.putExtra("Description",desc);
                startActivityForResult(intent, result);
                button.setText("");
              }
        });

        image = (ImageView) findViewById(R.id.image);
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertMediaWindow();
            }
        });
        video=(ImageView)findViewById(R.id.video);

        video.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //CallVideoActivity();
                AlertVideoWindow();
            }
        });

        audio=(ImageView)findViewById(R.id.audio);
        audio.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View v){
               // CallAudioActivity();
                AlertAudioWindow();
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
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void CategoryDropDown(String selectedTopic) {
        cat_Db = new CategoryDb(getApplicationContext());
        spinner = (Spinner) findViewById(R.id.sel_cat_name);
        CatList = cat_Db.getEditCatList(selectedTopic);
        CatList.removeAll(Collections.singleton(null));
        if (CatList != null) {
            Adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, CatList);
            Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(Adapter);
                   }
    }


    public void AlertWindow(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Quickee");
         // Setting Dialog Message
        alertDialog.setMessage("Term Name should be Unique,Please enter new Term Name");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            dialog.dismiss();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
    public void AlertMediaWindow() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");

        alertDialog.setMessage("You want select picture from gallery or take a pic?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                PackageManager packageManager=getApplicationContext().getPackageManager();
                if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Need Camera to use this Feature ", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
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
    public void AlertVideoWindow() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");

        alertDialog.setMessage("You want select video from gallery or record?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Record", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                PackageManager packageManager=getApplicationContext().getPackageManager();
                if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    Intent photoPickerIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(Intent.createChooser(photoPickerIntent, "Take Video"), REQUEST_Video_CAPTURE);
                }else{
                    Toast.makeText(getApplicationContext(), "Need Camera to use this Feature ", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select video"), PICK_VIDEO_REQUEST);

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    public void AlertAudioWindow() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");

        alertDialog.setMessage("You want to select audio from gallery or record?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Record", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), AudioRecorder.class);
                final int result = 7;
                startActivityForResult(intent, result);

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select audio"), PICK_AUDIO_REQUEST);

            }
        });

        // Showing Alert Message
        alertDialog.show();
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
        int orientation=00;

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //    Bundle extras = data.getExtras();
            //    Bitmap imageBitmap = (Bitmap) extras.get("data");
            //Get our saved file into a bitmap object:
            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
            Bitmap bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000,700);
            Matrix matrix = new Matrix();
            ExifInterface ei = null;
            try {
                ei = new ExifInterface(file.getAbsolutePath());
                orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            } catch (IOException e) {
                e.printStackTrace();
            }


            switch(orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    matrix.postRotate(00);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    break;
               /* case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    break;*/
                // etc.
            }

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            SaveImage(bitmap);
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
                video.setImageResource(R.drawable.ic_video_red);
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
                audio.setImageResource(R.drawable.ic_audio_red);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        if (requestCode == 5) {
            desc = data.getStringExtra("Description");
            if (desc.length() > 0) {
                if(desc.length() <= 18) {
                    String description = desc.substring(0, desc.length());
                    button.setText(description.concat("...."));
                }else {
                    String description = desc.substring(0, 18);
                    button.setText(description.concat("..."));
                }

            }
        }
        if (requestCode == 7) {
            audio_path_to_db=data.getStringExtra("ImgPath");
            if (audio_path_to_db.length() > 0) {

                ConfirmWindow("Audio");
                audio.setImageResource(R.drawable.ic_audio_red);
            }
        }
        if (requestCode == REQUEST_Video_CAPTURE) {

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
                video.setImageResource(R.drawable.ic_video_red);
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
        String fname = "Image-" + n + ".png";
        img_path_to_db = root + "/Quickee/Images/" + fname;
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            ConfirmWindow("Image");
            image.setImageResource(R.drawable.ic_img_red);
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
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight)
        {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth)
        {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }
}
