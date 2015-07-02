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
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ellipsonic.database.DescriptionDb;
import com.ellipsonic.database.MediaDb;
import com.ellipsonic.database.NotesTable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class EditDescription extends Activity {
    public DescriptionDb desc_Db=null;
    public MediaDb media_Db=null;
    private int PICK_IMAGE_REQUEST = 1;
    private int REQUEST_IMAGE_CAPTURE = 2;
    private int PICK_VIDEO_REQUEST = 3;
    private int PICK_AUDIO_REQUEST = 4;
    private int REQUEST_Video_CAPTURE = 5;
    String img_path_to_db;
    String video_path_to_db;
    String audio_path_to_db;
    String selecteddetails;
    EditText details;
    Button save;
    EditText updateTextValue;
    ImageButton backbutton;
    String selectedTopic;
    String selectedCategory;
    String selectedTerm;
  public  String selectedImage;
    String selectedVideo;
    String selectedAudio;
    Button delete_img;
    Button delete_video;
    Button delete_audio;
    Button Update_img;
    Button Update_video;
    Button Update_audio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_description);
        getActionBar().hide();
        Intent activityThatCalled = getIntent();
        selecteddetails = activityThatCalled.getExtras().getString("content");
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        selectedCategory =activityThatCalled.getExtras().getString("selectedCategory");
        selectedTerm =activityThatCalled.getExtras().getString("selectedTerm");
        selectedImage = activityThatCalled.getExtras().getString("selectedImage");
        selectedVideo =activityThatCalled.getExtras().getString("selectedVideo");
        selectedAudio =activityThatCalled.getExtras().getString("selectedAudio");
        details= (EditText)findViewById(R.id.editdetails);
        details.setText(selecteddetails);
        save=(Button) findViewById(R.id.edit_details_save);
        updateTextValue=(EditText)findViewById(R.id.editdetails);



        backbutton=(ImageButton)findViewById(R.id.edit_details_back_icon);
        backbutton.setOnClickListener(new View.OnClickListener() {
            public  void onClick(View view){
                finish();
            }
        });

        delete_img=(Button)findViewById(R.id.delete_img);
        delete_img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(selectedImage==null || selectedImage.isEmpty() ){
                    AlertWindow("Delete");

                }else {
                    media_Db =new MediaDb(getApplicationContext());
                    NotesTable tableinfo = new NotesTable();
                    tableinfo.image=selectedImage;
                    media_Db.delete_image(tableinfo);
                    selectedImage=null;
                    ConfirmWindow("Image");
                }
            }
        });

        delete_video=(Button)findViewById(R.id.delete_video);
        delete_video.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(selectedVideo==null || selectedVideo.isEmpty()){
                    AlertWindow("Delete");

                }else {
                    media_Db =new MediaDb(getApplicationContext());
                    NotesTable tableinfo = new NotesTable();
                    tableinfo.video=selectedVideo;
                    media_Db.delete_video(tableinfo);
                    selectedVideo=null;
                    ConfirmWindow("Video");
                }
            }
        });

        delete_audio=(Button) findViewById(R.id.delete_audio);
        delete_audio.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(selectedAudio==null || selectedAudio.isEmpty()){
                    AlertWindow("Delete");
                }else {

                    media_Db =new MediaDb(getApplicationContext());
                    NotesTable tableinfo = new NotesTable();
                    tableinfo.audio=selectedAudio;
                    media_Db.delete_audio(tableinfo);
                    selectedAudio=null;
                    ConfirmWindow("Audio");
                }
            }
        });

        Update_img=(Button)findViewById(R.id.Update_img);
        Update_img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                AlertWindow();
         }
        });

        Update_video=(Button)findViewById(R.id.Update_video);
        Update_video.setOnClickListener(new View.OnClickListener(){
            public void onClick(View View){
               // CallVideoActivity();
                AlertVideoWindow();
            }
        });

       Update_audio=(Button)findViewById(R.id.update_audio);
        Update_audio.setOnClickListener(new View.OnClickListener(){
            public void onClick(View View){
           //    CallAudioActivity();
                AlertAudioWindow();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UpdateTextValue= updateTextValue.getText().toString().trim();
                if(UpdateTextValue.length()>0) {
                    UpdateDescription(UpdateTextValue, selectedTopic, selectedCategory, selectedTerm);
                }else{
                    AlertDescWindow();
                }
            }
        });
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_description, menu);
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

    public void UpdateDescription(String updateTextValue,String selectedTopic,String selectedCategory,String selectedTerm){
        desc_Db=new DescriptionDb(getApplicationContext());
        NotesTable tableinfo = new NotesTable();
        tableinfo.description=updateTextValue;
        tableinfo.topic_name=selectedTopic;
        tableinfo.category_name=selectedCategory;
        tableinfo.term_name=selectedTerm;
        desc_Db.update_description(tableinfo);
        finish();
    }


    public void AlertWindow() {
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
                    takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
    public void AlertVideoWindow() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");

        alertDialog.setMessage("You want select Video from gallery or Record Video?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Record", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                PackageManager packageManager = getApplicationContext().getPackageManager();
                if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    Intent photoPickerIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    photoPickerIntent.putExtra(android.provider.MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    photoPickerIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(Intent.createChooser(photoPickerIntent, "Take Video"), REQUEST_Video_CAPTURE);

                } else {
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
         //  Bundle extras = data.getExtras();
         //   Bitmap imageBitmap = (Bitmap) extras.get("data");
            //Get our saved file into a bitmap object:
            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
              Bitmap bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 800,700);
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
                    break;
                // etc.*/
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
                File   file =new File(myDir,fname );

                FileOutputStream fos = new FileOutputStream(file);

                byte[] buf = new byte[1024];
                int len;
                while ((len = fis.read(buf)) > 0) {
                    fos.write(buf, 0, len);
                }
                fis.close();
                fos.close();
                UpdateVideo();
            }
            catch (Exception e)
            {
                e.printStackTrace();
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
                UpdateVideo();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        if (requestCode == 7) {
            audio_path_to_db=data.getStringExtra("ImgPath");
            if (audio_path_to_db.length() > 0) {
                UpdateAudio();
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
                int n = 1540;
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
                UpdateAudio();
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
            UpdateImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void UpdateImage(){

      NotesTable tableinfo = new NotesTable();
        media_Db = new MediaDb(getApplicationContext());
        if(img_path_to_db!=null){
            tableinfo.image = img_path_to_db.toString();
        }
            tableinfo.old_img_path=selectedImage;
            tableinfo.term_name=selectedTerm;
            tableinfo.category_name=selectedCategory;
            tableinfo.topic_name=selectedTopic;
            media_Db.Update_Image(tableinfo);
            img_path_to_db="";
            selectedImage=null;
            UpdateConfirmWindow("Image");

           }
 public  void UpdateVideo(){
     NotesTable tableinfo = new NotesTable();
     media_Db = new MediaDb(getApplicationContext());
     if(video_path_to_db!=null){
         tableinfo.video = video_path_to_db.toString();
     }
     tableinfo.old_video_path=selectedVideo;
     tableinfo.term_name=selectedTerm;
     tableinfo.category_name=selectedCategory;
     tableinfo.topic_name=selectedTopic;
     media_Db.Update_Video(tableinfo);
     video_path_to_db="";
     selectedVideo=null;
     UpdateConfirmWindow("Video");
 }
    public  void UpdateAudio(){
        NotesTable tableinfo = new NotesTable();
        media_Db = new MediaDb(getApplicationContext());
        if(audio_path_to_db!=null){
            tableinfo.audio = audio_path_to_db.toString();
        }
        tableinfo.old_audio_path=selectedAudio;
        tableinfo.term_name=selectedTerm;
        tableinfo.category_name=selectedCategory;
        tableinfo.topic_name=selectedTopic;
        media_Db.Update_Audio(tableinfo);
        audio_path_to_db="";
        selectedAudio=null;
        UpdateConfirmWindow("Audio");
    }
    public void AlertWindow(String selected) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");
        alertDialog.setMessage("Nothing to "+selected);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
    public void ConfirmWindow(String selected) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");
        alertDialog.setMessage(selected+" Deleted successfully");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
    public void UpdateConfirmWindow(String selected) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");
        alertDialog.setMessage(selected+" Updated successfully");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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

      if (height> reqHeight)
        {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth)
        {
          //  if(Math.round((float)width / (float)reqWidth) > inSampleSize); // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public void AlertDescWindow() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");

        alertDialog.setMessage("Defination cannot be Blank");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();

            }
        });


        // Showing Alert Message
        alertDialog.show();
    }
  public void  DisableButton(){
      if(selectedImage==null || selectedImage.isEmpty() ){
          delete_img.setEnabled(false);
      }
      if(selectedAudio==null || selectedAudio.isEmpty() ){
          delete_audio.setEnabled(false);
      }
      if(selectedVideo==null || selectedVideo.isEmpty() ){
          delete_video.setEnabled(false);
      }
    }

}
