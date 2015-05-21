package com.ellipsonic.quickee;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

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
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


   /*    String[]  myStringArray={"Air Force","Plane","Auto","Military"};
        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                myStringArray);
        ListView myList=(ListView)
                findViewById(R.id.edit_topic_listView);
        myList.setAdapter(myAdapter);*/

    }

  public void EditTopicListView(Context context){

      TopicDb topic_Db = new TopicDb(context);

      ArrayList<String> topicList = topic_Db.getTopicList();
      topicList.removeAll(Collections.singleton(null));// String[]  myStringArray={"Air Force","Plane","Auto","Military","Sachin","BMW","AUDI","KING","Lemon","sweet"};
      if (topicList != null) {
          ArrayAdapter<String> myAdapter = new
                  ArrayAdapter<String>(this,
                  android.R.layout.simple_list_item_1,
                  topicList);
          ListView myList = (ListView) findViewById(R.id.edit_topic_listView);
          myList.setAdapter(myAdapter);


         /* myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position,
                                      long id) {
                  String clickedItem  = String.valueOf(parent.getItemAtPosition(position));
                  Intent intent = new Intent(getApplicationContext() , Category.class);
                  final int result = 1;
                  intent.putExtra("selectedTopic",clickedItem);
                  startActivityForResult(intent, result);
                  //     Intent intent = new Intent(getActivity(), Category.class);
                  //    startActivity(intent);
                  //     Toast.makeText(getActivity(), clickedItem, Toast.LENGTH_LONG).show();
              }
          });*/
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
}
