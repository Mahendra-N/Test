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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.ellipsonic.database.NotesTable;
import com.ellipsonic.database.TopicDb;

import java.util.ArrayList;
import java.util.Collections;


public class EditTopic extends Activity {
    ArrayList<String> topicList;
    public  TopicDb topic_Db=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_topic);
        getActionBar().hide();

        EditTopicListView(getApplicationContext());
        ImageButton back_button =(ImageButton) findViewById(R.id.edit_topic_back_icon);
        Button editclose=(Button) findViewById(R.id.edit_topic_save);

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

      ArrayList<String> topicList = topic_Db.getEditTopicList();
      topicList.removeAll(Collections.singleton(null));// String[]  myStringArray={"Air Force","Plane","Auto","Military","Sachin","BMW","AUDI","KING","Lemon","sweet"};
      if (topicList.size()>0) {
          findViewById(R.id.alert).setVisibility(View.GONE);
          findViewById(R.id.blank_msg).setVisibility(View.GONE);
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
      }if(topicList.size()==0){
          findViewById(R.id.blank_msg).setVisibility(View.VISIBLE);
          findViewById(R.id.alert).setVisibility(View.VISIBLE);
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
        input.setSingleLine();
        input.setText(clickedItem);
       final String defaultTextValue  =clickedItem;

        alertDialog.setView(input);
        // Setting Dialog Message
        alertDialog.setMessage("you want to Update or Delete?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String updateTextValue = String.valueOf(input.getText()).trim();
                if(updateTextValue.length()>0) {
                    UpdateTopic(defaultTextValue, updateTextValue);
                }else {
                     Toast.makeText(getApplicationContext(), "Enter topic name to update", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                String deleteTextValue = String.valueOf(input.getText()).trim();
                if(deleteTextValue.length()>0) {
                    DeleteTopic(deleteTextValue, defaultTextValue);
                }else {
                    Toast.makeText(getApplicationContext(), "Enter topic name to delete", Toast.LENGTH_SHORT).show();
                }

               // Toast.makeText(getApplicationContext(), "You clicked Delete", Toast.LENGTH_SHORT).show();
               // dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void UpdateTopic(String defaultTextValue, String updateTextValue){

        String  TopicName = updateTextValue;
        topic_Db = new TopicDb(getApplicationContext());
        topicList = topic_Db.RowsAffetedInTopic(TopicName);
        if (TopicName.length()> 0) {

            if(topicList.size()<=0){
                TopicDb topicDb =new TopicDb(getApplicationContext());
                NotesTable tableinfo = new NotesTable();
                tableinfo.old_topic_name=defaultTextValue;
                tableinfo.topic_name =updateTextValue;
                topicDb.update_topic(tableinfo);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                Toast.makeText(getApplicationContext(), " Topic " + defaultTextValue + " Updated to " +updateTextValue,
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Topic " + TopicName + " Exists,Enter Unique Topic Name to Update",
                        Toast.LENGTH_LONG).show();
            }

        }

    }

    public void  DeleteTopic(String deleteTextValue, String defaultTextValue) {
        if (deleteTextValue.equals(defaultTextValue)) {
            TopicDb topicDb = new TopicDb(getApplicationContext());
            NotesTable tableinfo = new NotesTable();
            tableinfo.topic_name = deleteTextValue;
            topicDb.delete_topic(tableinfo);
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Topic " + deleteTextValue + " deleted successfully",
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Topic not found to Delete",
                    Toast.LENGTH_SHORT).show();
        }
    }

}
