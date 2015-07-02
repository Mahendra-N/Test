package com.ellipsonic.quickee;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ellipsonic.database.NotesTable;
import com.ellipsonic.database.TopicDb;

import java.util.ArrayList;

public class AddNewTopic extends Activity {
    public ImageButton backButton = null;
    public Button  topic_save=null;
    public  EditText Topic_Name=null;
   public  TopicDb topic_Db=null;
    ArrayList<String> topicList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_topic);
        getActionBar().hide();
        backButton = (ImageButton) findViewById(R.id.back_icon);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();

            }
        });
        topic_save=(Button)findViewById(R.id.topic_save);
        topic_save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                  Topic_Name =(EditText)findViewById(R.id.Topic_Name);
                topic_Db = new TopicDb(getApplicationContext());
            String  TopicName = Topic_Name.getText().toString().trim();
                topicList = topic_Db.RowsAffetedInTopic(TopicName);
                             if (TopicName.length()> 0) {

                    if(topicList.size()<=0){
                        TopicDb topicDb =new TopicDb(getApplicationContext());
                        NotesTable tableinfo = new NotesTable();
                        tableinfo.topic_name = Topic_Name.getText().toString();
                        topicDb.insert_topic(tableinfo);
                        Topic_Name.setText("");
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Topic "+TopicName+" Exists,Please Enter Unique Topic Name",
                                Toast.LENGTH_LONG).show();
                    }

                } else{
                    Toast.makeText(getApplicationContext(), "Enter Topic Name",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_topic, menu);
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

}
