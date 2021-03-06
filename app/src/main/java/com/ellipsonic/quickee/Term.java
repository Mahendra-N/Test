package com.ellipsonic.quickee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.ellipsonic.database.TermDb;

import java.util.ArrayList;
import java.util.Collections;


public class Term extends Activity {
    String selectedTopic;
    String selectedCategory;
    public TermDb term_Db=null;
    public ArrayList<String> TermList=null;
    EditText myFilter;
    ArrayAdapter<String> Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        selectedCategory =activityThatCalled.getExtras().getString("selectedCategory");
        myFilter = (EditText) findViewById(R.id.search);
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
            Adapter   = new
                    ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    TermList);
            ListView List = (ListView) this.findViewById(R.id.term_listView);
            List.setAdapter(Adapter);
            List.setTextFilterEnabled(true);
            List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    String clickedItem  = String.valueOf(parent.getItemAtPosition(position));
                    Intent intent = new Intent(Term.this, Description.class);
                    final int result = 1;
                    intent.putExtra("selectedTopic",selectedTopic);
                    intent.putExtra("selectedCategory",selectedCategory);
                    intent.putExtra("selectedTerm",clickedItem);
                    startActivityForResult(intent, result);
                }

            });
            myFilter.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Adapter.getFilter().filter(s.toString());
                }
            });
        }else{
            Log.d("message", "nothing is there in database");
        }

    }

}
