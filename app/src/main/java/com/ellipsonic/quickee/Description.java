package com.ellipsonic.quickee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.ellipsonic.database.DescriptionDb;
import com.ellipsonic.database.NotesTable;


public class Description extends Activity {
    public DescriptionDb desc_Db=null;
    MediaController mediaController;
    TextView article;
    TextView header;
    ImageView delete;
    ImageView edit;
    ImageView img;
    VideoView video;
    VideoView audio;
    TextView audio_name;
    ImageView speaker;
    String content;
    String selectedTopic;
    String selectedCategory;
    String selectedTerm;
    String imgpath;
    String videopath;
    String audiopath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        final ScrollView main = (ScrollView) findViewById(R.id.ScrollView01);
        main.post(new Runnable() {
            public void run() {
                main.scrollTo(0,0);
            }
        });
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        selectedCategory =activityThatCalled.getExtras().getString("selectedCategory");
        selectedTerm =activityThatCalled.getExtras().getString("selectedTerm");
        setTitle(selectedTerm.toUpperCase());
        article = (TextView) findViewById(R.id.content);
        img  = (ImageView) findViewById(R.id.img);
        video=(VideoView)findViewById(R.id.videoView);
        audio=(VideoView)findViewById(R.id.audioView);
        audio_name=(TextView)findViewById(R.id.audio_name);
        speaker=(ImageView)findViewById(R.id.speaker);
        Description(selectedTopic,selectedCategory,selectedTerm);

     }
    @Override
    public void onPause() {
        super.onPause();
        desc_Db = null;
        imgpath="";
        videopath="";
        audiopath="";
         }
    @Override
    public void onResume() {
        super.onResume();
      Description(selectedTopic, selectedCategory, selectedTerm);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
            case R.id.edit:
                Intent intent = new Intent(Description.this,EditDescription.class);
                intent.putExtra("content",content);
                intent.putExtra("selectedTopic",selectedTopic);
                intent.putExtra("selectedCategory",selectedCategory);
                intent.putExtra("selectedTerm",selectedTerm);
                intent.putExtra("selectedImage",imgpath);
                intent.putExtra("selectedVideo",videopath);
                intent.putExtra("selectedAudio",audiopath);
                startActivityForResult(intent,1);
                 break;
            case R.id.delete:
                AlertWindow();

                break;
                default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void Description(String selectedTopic,String selectedCategory,String selectedTerm){

        desc_Db=new DescriptionDb(getApplicationContext());
        NotesTable tableinfo = new NotesTable();
        tableinfo.topic_name=selectedTopic;
        tableinfo.category_name=selectedCategory;
        tableinfo.term_name=selectedTerm;
        String[] details=desc_Db.getDetails(tableinfo);
        content=details[0];
        article.setText(content);
     //   header.setText(details[1].toUpperCase());
        if((details[2])!=null) {
         imgpath=details[2];



           img.setImageURI(Uri.parse(details[2]));
           img.setVisibility(View.VISIBLE);
        }else{
            img.setVisibility(View.GONE);
        }
        if((details[3]!=null)){
             videopath=details[3];
            mediaController =new MediaController(this);
            DisplayMetrics dm = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(dm);
             int height = dm.heightPixels;
            int width = dm.widthPixels;
            video.setMinimumWidth(width);
            video.setMinimumHeight(height);
            video.setMediaController(mediaController);
            video.setVideoURI(Uri.parse(details[3]));
            video.seekTo(100);
            video.setVisibility(View.VISIBLE);
        }else{
            video.setVisibility(View.GONE);
        }
        if((details[4]!=null)){
            audiopath=details[4];
            audio_name.setText(selectedTerm+"_clip.mp3");
            mediaController =new MediaController(this);
            audio.setMediaController(mediaController);
            audio.setVideoURI(Uri.parse(details[4]));
            audio.setVisibility(View.VISIBLE);
            speaker.setVisibility(View.VISIBLE);
            audio_name.setVisibility(View.VISIBLE);
            audio.requestFocus();

        }else{
            audio.setVisibility(View.GONE);
            speaker.setVisibility(View.GONE);
            audio_name.setVisibility(View.GONE);
        }
    }

 public void  DeleteDescription(String selectedTopic,String selectedCategory,String selectedTerm){
     desc_Db=new DescriptionDb(getApplicationContext());
     NotesTable tableinfo = new NotesTable();
     tableinfo.topic_name=selectedTopic;
     tableinfo.category_name=selectedCategory;
     tableinfo.term_name=selectedTerm;
     desc_Db.delete_description(tableinfo);
     finish();
 }
    public void AlertWindow() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");

        alertDialog.setMessage("Are Sure you want to Delete?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DeleteDescription(selectedTopic,selectedCategory,selectedTerm);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
}
