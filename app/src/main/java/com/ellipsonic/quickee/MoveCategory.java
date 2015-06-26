package com.ellipsonic.quickee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ellipsonic.database.CategoryDb;
import com.ellipsonic.database.NotesTable;
import com.ellipsonic.database.TopicDb;

import java.util.ArrayList;
import java.util.Collections;

public class MoveCategory extends Activity implements View.OnClickListener {
Spinner spinner;
Spinner from_spinner;
String SelectedTopic;
Button cancel;
Button save;
String old_topic;
ListView List;
ArrayAdapter<String> Adapter;
public ArrayList<String> CatList=null;
    Boolean flag=false;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move__category);
        getActionBar().hide();
        Intent activityThatCalled = getIntent();
        SelectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        old_topic = SelectedTopic;
        ImageView back=(ImageView) findViewById(R.id.back_icon);
        cancel=(Button)findViewById(R.id.cancel);
        save=(Button)findViewById(R.id.savechanges);
          back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            TextView done =(TextView)findViewById(R.id.done);
            done.setOnClickListener(new View.OnClickListener(){
                public  void onClick(View v){
                    finish();
                }
            });
        TopicsDropDownFrom();
        TopicsDropDown();
        EDitCatListView( SelectedTopic);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_move__category, menu);
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

    public void TopicsDropDown() {
        TopicDb topic_Db = new TopicDb(getApplicationContext());
        ArrayList<String> topicList = topic_Db.getEditTopicList();
        spinner = (Spinner) findViewById(R.id.sel_topic_name);
        topicList.removeAll(Collections.singleton(null));// String[]  myStringArray={"Air Force","Plane","Auto","Military","Sachin","BMW","AUDI","KING","Lemon","sweet"};
        if (topicList != null) {

            ArrayAdapter<String> myAdapter = new   ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item,   topicList);
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(myAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                   String sel_topic = (String) parentView.getItemAtPosition(position);
                    SelectedTopic=sel_topic;
                  EDitCatListView(SelectedTopic);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }
    }

    public void TopicsDropDownFrom() {
        TopicDb topic_Db = new TopicDb(getApplicationContext());
        ArrayList<String> topicList = topic_Db.getEditTopicList();
        from_spinner = (Spinner) findViewById(R.id.from_topic);
        topicList.removeAll(Collections.singleton(null));// String[]  myStringArray={"Air Force","Plane","Auto","Military","Sachin","BMW","AUDI","KING","Lemon","sweet"};
        if (topicList != null) {

            ArrayAdapter<String> myAdapter = new   ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item,   topicList);
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            from_spinner.setAdapter(myAdapter);

             }
    }
    public void EDitCatListView( String selectedTopic){

        CategoryDb cat_Db=new CategoryDb(getApplicationContext());
        ArrayList<String> CatList =  cat_Db.getEditCatList(selectedTopic);
        CatList.removeAll(Collections.singleton(null));
        if(CatList!=null) {
          Adapter = new
                    ArrayAdapter<String>(this,  android.R.layout.simple_list_item_multiple_choice, CatList);
            List = (ListView) this.findViewById(R.id.move_cat_list);
            List.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            List.setAdapter(Adapter);
            save.setOnClickListener(this);

        }
    }
    public void onClick(View v) {
        SparseBooleanArray checked_val = List.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();

        for (int i = 0; i < checked_val.size(); i++) {
            // Item position in adapter
            int position = checked_val.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked_val.valueAt(i))
                selectedItems.add(Adapter.getItem(position));
        }
if(selectedItems.size()>0){
             CategoryDb catDB =new CategoryDb(getApplicationContext());
      NotesTable tableinfo = new NotesTable();
        for (int i = 0; i < selectedItems.size(); i++) {
        //    outputStrArr[i] = selectedItems.get(i);
            old_topic= String.valueOf(from_spinner.getSelectedItem());
            String category =selectedItems.get(i);
            tableinfo.old_topic_name=old_topic;
            tableinfo.category_name =category;
            tableinfo.topic_name=SelectedTopic;
            if(old_topic.equals(SelectedTopic)){


                Toast.makeText(getApplicationContext(), "Selected Category is Under Same Topic",
                        Toast.LENGTH_SHORT).show();
                flag=false;
            }else {
                CatList =  catDB.RowsAffetedInCategory(SelectedTopic, category);
                if(CatList.size()<=0){
                catDB.move_category(tableinfo);
                    flag=true;}
                else{
                    Toast.makeText(getApplicationContext(), "Selected Category already Exists In Selected Topic, CANNOT MOVE",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }else{
            Toast.makeText(getApplicationContext(), "Select Atleast one Category to Move",
                    Toast.LENGTH_SHORT).show();
    flag=false;
        }
        if(flag==true) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }
}
