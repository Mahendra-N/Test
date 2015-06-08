package com.ellipsonic.quickee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ellipsonic.database.NotesTable;
import com.ellipsonic.database.TopicDb;

import java.util.ArrayList;
import java.util.Collections;


public class EditTopic extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_topic);
        getActionBar().hide();

        EditTopicListView(getApplicationContext());
        ImageView back_button =(ImageView) findViewById(R.id.edit_topic_back_icon);
        TextView editclose=(TextView) findViewById(R.id.edit_topic_save);

        editclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

  public void EditTopicListView(Context context){

      TopicDb topic_Db = new TopicDb(context);

      ArrayList<String> topicList = topic_Db.getTopicList();
      topicList.removeAll(Collections.singleton(null));// String[]  myStringArray={"Air Force","Plane","Auto","Military","Sachin","BMW","AUDI","KING","Lemon","sweet"};
      if (topicList != null) {
          ArrayAdapter<String> myAdapter = new
                  ArrayAdapter<String>(this,
                  R.layout.customeditlist,
                  R.id.Editname,topicList);
          ListView myList = (ListView) findViewById(R.id.edit_topic_listView);
          myList.setAdapter(myAdapter);


         myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position,
                                      long id) {
                  String clickedItem  = String.valueOf(parent.getItemAtPosition(position));
                  AlertWindowTopic(clickedItem);
              }
          });
      }else{
          Log.d("message", "nothing is there in database");
      }
  }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_topic, menu);
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

    public void AlertWindowTopic(final String clickedItem){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Quickee");
        final EditText input = new EditText(this);
        input.setText(clickedItem);
       final String defaultTextValue  =clickedItem;

        alertDialog.setView(input);
        // Setting Dialog Message
        alertDialog.setMessage("you want Update or Delete?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String updateTextValue = String.valueOf(input.getText());
                UpdateTopic(defaultTextValue, updateTextValue);
                //  Toast.makeText(getApplicationContext(), EditTextValue, Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                String deleteTextValue = String.valueOf(input.getText());
                DeleteTopic(deleteTextValue);
               // Toast.makeText(getApplicationContext(), "You clicked Delete", Toast.LENGTH_SHORT).show();
               // dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void UpdateTopic(String defaultTextValue, String updateTextValue){
     //   Toast.makeText(getApplicationContext(), updateTextValue, Toast.LENGTH_SHORT).show();
        TopicDb topicDb =new TopicDb(getApplicationContext());
        NotesTable tableinfo = new NotesTable();
        tableinfo.old_topic_name=defaultTextValue;
        tableinfo.topic_name =updateTextValue;
        topicDb.update_topic(tableinfo);
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }

    public void  DeleteTopic(String deleteTextValue){
    //    Toast.makeText(getApplicationContext(), deleteTextValue, Toast.LENGTH_SHORT).show();
        TopicDb topicDb =new TopicDb(getApplicationContext());
        NotesTable tableinfo = new NotesTable();
        tableinfo.topic_name =deleteTextValue;
        topicDb.delete_topic(tableinfo);
        Intent intent = getIntent();
       finish();
       startActivity(intent);

    }

}
