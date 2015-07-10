package com.ellipsonic.quickee;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ellipsonic.database.CategoryDb;
import com.ellipsonic.database.NotesTable;
import com.ellipsonic.database.TopicDb;

import java.util.ArrayList;
import java.util.Collections;

public class MoveCategory extends Activity implements View.OnClickListener {
EditText move_to_topic;
Spinner from_topic;
String SelectedTopic;
Button cancel;
Button save;
String move_to_topic_spinner_selected_val;
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
     //   move_to_topic_spinner_selected_val = SelectedTopic;
            ImageButton back=(ImageButton) findViewById(R.id.back_icon);
        move_to_topic = (EditText) findViewById(R.id.move_to_topic);
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
             TopicsDropDownFromTopic();

        EDitCatListView( SelectedTopic);
            move_to_topic.setText(SelectedTopic);
            move_to_topic.setTextColor(Color.parseColor("#000000"));
            move_to_topic.setEnabled(false);
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



    public void TopicsDropDownFromTopic() {
        TopicDb topic_Db = new TopicDb(getApplicationContext());
        ArrayList<String> topicList = topic_Db.getEditTopicList();
        from_topic = (Spinner) findViewById(R.id.from_topic);
        topicList.removeAll(Collections.singleton(null));// String[]  myStringArray={"Air Force","Plane","Auto","Military","Sachin","BMW","AUDI","KING","Lemon","sweet"};

        if (topicList != null) {

            ArrayAdapter<String> myAdapter = new   ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item,   topicList);
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            from_topic.setAdapter(myAdapter);
            from_topic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String sel_topic = (String) parentView.getItemAtPosition(position);
                    move_to_topic_spinner_selected_val=sel_topic;
                 //   EDitCatListView(SelectedTopic);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
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
        //    move_to_topic_spinner_selected_val= String.valueOf(move_to_topic.getSelectedItem());
            String category =selectedItems.get(i);
            tableinfo.old_topic_name=move_to_topic_spinner_selected_val;
            tableinfo.category_name =category;
            tableinfo.topic_name=SelectedTopic;
            if(move_to_topic_spinner_selected_val.equals(SelectedTopic)){


                Toast.makeText(getApplicationContext(), "Selected category is under Same Topic",
                        Toast.LENGTH_SHORT).show();
                flag=false;
            }else {
                CatList =  catDB.RowsAffetedInCategory(move_to_topic_spinner_selected_val, category);
                if(CatList.size()<=0){
                catDB.move_category(tableinfo);
                    flag=true;}
                else{
                    Toast.makeText(getApplicationContext(), "Selected category already exists in Selected Topic, CANNOT MOVE",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }else{
            Toast.makeText(getApplicationContext(), "Select atleast one Category to Move",
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
