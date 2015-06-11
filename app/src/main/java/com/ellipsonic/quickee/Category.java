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

import com.ellipsonic.database.CategoryDb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class Category extends Activity {
    String selectedTopic;
    public CategoryDb cat_Db=null;
    public ArrayList<String> CatList=null;
    EditText myFilter;
 //   ArrayAdapter<String> Adapter;
    CustomCategoryListView adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        myFilter = (EditText) findViewById(R.id.search);
         myFilter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
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
            case android.R.id.home:
                finish();
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
        myFilter.clearFocus();
        CatListView(selectedTopic);
    }

   public void CatListView( final String selectedTopic){
      cat_Db=new CategoryDb(getApplicationContext());
      CatList =  cat_Db.getCatList(selectedTopic);
        CatList.removeAll(Collections.singleton(null));
       ArrayList<CategoryPopulating> catList = new ArrayList<CategoryPopulating>();
       ArrayList<String> no_term = new ArrayList<String>();
            if(CatList!=null) {
                for (int i=0;i<CatList.size();i++){
                    String[] parts = CatList.get(i).split("\n");
                //    catList.add(parts[0]); // topic
                 //   no_term.add(parts[1].concat(" term")); //noofcat
                    String catname =parts[0];
                    String no_of_term =(parts[1].concat("  term"));
                    CategoryPopulating wp = new CategoryPopulating(catname, no_of_term);
                    catList.add(wp);
                }
                adapter=new CustomCategoryListView(this, catList,selectedTopic);
             //   Adapter = new ArrayAdapter<String>(this, R.layout.customlist, R.id.Itemname,CatList);
           ListView List = (ListView) this.findViewById(R.id.cat_listView);
                List.setAdapter(adapter);
                List.setTextFilterEnabled(true);
          /*     List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position,
                                       long id) {
                   String clickedItem  = String.valueOf(parent.getItemAtPosition(position));
                 /*  String[] parts = clickedItem.split("\n");
                   String part1 = parts[0]; // cat
                   String part2 = parts[1]; //no of term
                  Intent intent = new Intent(Category.this, Term.class);
                   final int result = 1;
                   intent.putExtra("selectedTopic",selectedTopic);
                   intent.putExtra("selectedCategory",clickedItem);
                   startActivityForResult(intent, result);
               }

           });*/


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
