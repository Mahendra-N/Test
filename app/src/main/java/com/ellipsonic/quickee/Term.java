package com.ellipsonic.quickee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.ellipsonic.database.TermDb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class Term extends Activity {
    String selectedTopic;
    String selectedCategory;
    public TermDb term_Db=null;
    public ArrayList<String> TermList=null;
    EditText myFilter;
 //   ArrayAdapter<String> Adapter;
 CustomTermListView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        selectedCategory =activityThatCalled.getExtras().getString("selectedCategory");
        setTitle(selectedCategory.toUpperCase());
        myFilter = (EditText) findViewById(R.id.search);
        myFilter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
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
        myFilter.clearFocus();
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
            case android.R.id.home:
                finish();
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
        ArrayList<TermPopulating> termList = new ArrayList<TermPopulating>();
        ArrayList<String> desc = new ArrayList<String>();
        if(TermList!=null) {
            for (int i=0;i<TermList.size();i++){
                String[] parts = TermList.get(i).split("\n");
             //   termList.add(parts[0]); // topic
              //  desc.add(parts[1].concat("....")); //noofcat
                String termname =parts[0];
                String description =(parts[1].concat("...."));
                TermPopulating wp = new TermPopulating(termname, description);
                termList.add(wp);
            }
            adapter=new CustomTermListView(this, termList, selectedTopic,selectedCategory);
          //  Adapter   = new ArrayAdapter<String>(this,R.layout.customlist,R.id.Itemname,TermList);
            ListView List = (ListView) this.findViewById(R.id.term_listView);
            List.setAdapter(adapter);
            List.setTextFilterEnabled(true);

            myFilter.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String text = myFilter.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }
            });
        }else{
            Log.d("message", "nothing is there in database");
        }

    }

}
