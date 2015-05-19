package com.ellipsonic.quickee;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ellipsonic.database.NotesTable;
import com.ellipsonic.database.TopicDb;

public class AddNewTopic extends Activity {
    public ImageView backButton = null;
    public TextView  topic_save=null;
    public  EditText Topic_Name=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_new_topic);
        getActionBar().hide();
        backButton = (ImageView) findViewById(R.id.back_icon);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();

            }
        });
        topic_save=(TextView)findViewById(R.id.topic_save);
        topic_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                  Topic_Name =(EditText)findViewById(R.id.Topic_Name);

                if(Topic_Name.getText().toString().length()>0){

                    TopicDb topicDb =new TopicDb(getApplicationContext());
                    NotesTable tableinfo = new NotesTable();
                    tableinfo.topic_name =Topic_Name.getText().toString();
                    topicDb.insert_topic(tableinfo);
                    Topic_Name.setText("");
                    TopicsFragment topic = new TopicsFragment();
                    topic.TopicListView();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Nothing  to Save",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
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
