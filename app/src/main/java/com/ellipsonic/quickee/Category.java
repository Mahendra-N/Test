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

import com.ellipsonic.database.CategoryDb;

import java.util.ArrayList;
import java.util.Collections;


public class Category extends Activity {
    String selectedTopic;
    public CategoryDb cat_Db=null;
    public ArrayList<String> CatList=null;
    EditText myFilter;
    ArrayAdapter<String> Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        myFilter = (EditText) findViewById(R.id.search);
      //  Toast.makeText(this, selectedTopic, Toast.LENGTH_LONG).show();
       CatListView(selectedTopic);
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
               case R.id.add_category:
               Intent intent = new Intent(this, AddNewCat.class);
            //   this.startActivity(intent);
               final int result = 1;
               intent.putExtra("selectedTopic",selectedTopic);
               startActivityForResult(intent, result);
                break;
            case R.id.edit_category:
               Intent edit_intent = new Intent(this, EditCategory.class);
               // this.startActivity(edit_intent);
                final int resulteditcat = 1;
                edit_intent.putExtra("selectedTopic",selectedTopic);
                startActivityForResult(edit_intent, resulteditcat);
                break;
                default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        cat_Db = null;
        CatList = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        CatListView(selectedTopic);
    }

   public void CatListView( final String selectedTopic){
      cat_Db=new CategoryDb(getApplicationContext());
      CatList =  cat_Db.getCatList(selectedTopic);
        CatList.removeAll(Collections.singleton(null));
            if(CatList!=null) {
                Adapter = new
                   ArrayAdapter<String>(this,
                   android.R.layout.simple_list_item_1,
                   CatList);
           ListView List = (ListView) this.findViewById(R.id.cat_listView);
                  List.setAdapter(Adapter);
                List.setTextFilterEnabled(true);
           List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position,
                                       long id) {
                   String clickedItem  = String.valueOf(parent.getItemAtPosition(position));
                   Intent intent = new Intent(Category.this, Term.class);
                   final int result = 1;
                   intent.putExtra("selectedTopic",selectedTopic);
                   intent.putExtra("selectedCategory",clickedItem);
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
           Log.d("message","nothing is there in database");
       }

    }


}
