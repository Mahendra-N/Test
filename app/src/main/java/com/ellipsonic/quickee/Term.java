package com.ellipsonic.quickee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ellipsonic.database.TermDb;

import java.util.ArrayList;
import java.util.Collections;


public class Term extends Activity {
    String selectedTopic;
    String selectedCategory;
    public TermDb term_Db=null;
    public ArrayList<String> TermList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        selectedCategory =activityThatCalled.getExtras().getString("selectedCategory");

     //   TermListView(selectedTopic, selectedCategory);
       /* String[]  myStringArray={"Air Force","Plane","Auto","Military"};
        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                myStringArray);
        ListView myList=(ListView)
                findViewById(R.id.term_listView);
        myList.setAdapter(myAdapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

               Intent intent = new Intent(Term.this, Description.class);
               startActivity(intent);

            }        });*/
    }
    @Override
    public void onPause() {
        super.onPause();
        term_Db = null;
        TermList = null;
    }
    @Override
    public void onResume() {
        super.onResume();
        TermListView(selectedTopic, selectedCategory);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_term, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
               case R.id.add_term:
               Intent intent = new Intent(this, AddNewTerm.class);
                   final int result = 1;
                   intent.putExtra("selectedTopic",selectedTopic);
                   intent.putExtra("selectedCategory",selectedCategory);
                   startActivityForResult(intent, result);
               break;
            case R.id.edit_term:
                Intent edit_intent = new Intent(this, EditTerm.class);
                final int resultone = 1;
                edit_intent.putExtra("selectedTopic",selectedTopic);
                edit_intent.putExtra("selectedCategory",selectedCategory);
                startActivityForResult(edit_intent, resultone);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
    public void TermListView( final String selectedTopic,final String selectedCategory){
        term_Db=new TermDb(getApplicationContext());
        TermList =  term_Db.getTermList(selectedTopic,selectedCategory);
       TermList.removeAll(Collections.singleton(null));
        if(TermList!=null) {
            ArrayAdapter<String> Adapter = new
                    ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    TermList);
            ListView List = (ListView) this.findViewById(R.id.term_listView);
            List.setAdapter(Adapter);

            List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    Intent intent = new Intent(Term.this, Description.class);
                        startActivity(intent);
                 /*   String clickedItem  = String.valueOf(parent.getItemAtPosition(position));
                    Intent intent = new Intent(Category.this, Term.class);
                    final int result = 1;
                    intent.putExtra("selectedTopic",selectedTopic);
                    intent.putExtra("selectedCategory",clickedItem);
                    startActivityForResult(intent, result);*/
                }

            });
        }else{
            Log.d("message", "nothing is there in database");
        }

    }

}
