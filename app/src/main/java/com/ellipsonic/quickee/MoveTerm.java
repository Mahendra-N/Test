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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ellipsonic.database.CategoryDb;
import com.ellipsonic.database.NotesTable;
import com.ellipsonic.database.TermDb;

import java.util.ArrayList;
import java.util.Collections;

    public class MoveTerm extends Activity implements View.OnClickListener {
        EditText from_cat;
        String SelectedTopic;
        String SelectedCategory;
        Button cancel;
        Button save;
        String old_category;
        ListView List;
        ArrayAdapter<String> Adapter;
        public ArrayList<String> TermList=null;
        Spinner move_to_cat;
        Boolean flag=false;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_move_term);
            getActionBar().hide();
            Intent activityThatCalled = getIntent();
            SelectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
            SelectedCategory = activityThatCalled.getExtras().getString("selectedCategory");
            old_category = SelectedCategory;
            ImageView back=(ImageView) findViewById(R.id.back_icon);
            cancel=(Button)findViewById(R.id.cancel);
            save=(Button)findViewById(R.id.savechanges);
            from_cat = (EditText) findViewById(R.id.from_to_cat);
            from_cat.setText(SelectedCategory);
            from_cat.setTextColor(Color.parseColor("#000000"));
            from_cat.setEnabled(false);
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
            CategoryDropDown();
            CategoryDropDownFrom();
            EditTermListView(SelectedTopic,SelectedCategory);

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

        public void CategoryDropDown() {
          /*  CategoryDb  cat_Db = new CategoryDb(getApplicationContext());
            ArrayList<String>   CatList =  cat_Db.getEditCatList(SelectedTopic);
            from_cat = (Spinner) findViewById(R.id.from_to_cat);
            CatList.removeAll(Collections.singleton(null));// String[]  myStringArray={"Air Force","Plane","Auto","Military","Sachin","BMW","AUDI","KING","Lemon","sweet"};
            if (CatList != null) {
                ArrayAdapter<String> myAdapter = new   ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item,   CatList);
                myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                from_cat.setAdapter(myAdapter);

            }*/
        }

        public void CategoryDropDownFrom() {
            CategoryDb  cat_Db = new CategoryDb(getApplicationContext());
            ArrayList<String>   CatList =  cat_Db.getEditCatList(SelectedTopic);
            move_to_cat = (Spinner) findViewById(R.id.move_to_cat);
            CatList.removeAll(Collections.singleton(null));// String[]  myStringArray={"Air Force","Plane","Auto","Military","Sachin","BMW","AUDI","KING","Lemon","sweet"};
            if (CatList != null) {
                ArrayAdapter<String> myAdapter = new   ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item,   CatList);
                myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                move_to_cat.setAdapter(myAdapter);

                move_to_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        String sel_cat = (String) parentView.getItemAtPosition(position);
                        old_category=sel_cat;
                       // EditTermListView(SelectedTopic,SelectedCategory);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        }
        public void EditTermListView( final String selectedTopic,final String selectedCategory) {
            TermDb term_Db = new TermDb(getApplicationContext());
            ArrayList<String> TermList = term_Db.getEditTermList(selectedTopic, selectedCategory);
            TermList.removeAll(Collections.singleton(null));
            if (TermList != null) {
                Adapter = new
                        ArrayAdapter<String>(this,  android.R.layout.simple_list_item_multiple_choice, TermList);
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

                TermDb termDb =new TermDb(getApplicationContext());
                NotesTable tableinfo = new NotesTable();
                for (int i = 0; i < selectedItems.size(); i++) {
                    //    outputStrArr[i] = selectedItems.get(i);
                 //   old_category= String.valueOf(from_cat.getSelectedItem());
                    String term =selectedItems.get(i);
                    tableinfo.old_cat_name=old_category;
                    tableinfo.category_name =SelectedCategory;
                    tableinfo.topic_name=SelectedTopic;
                    tableinfo.term_name=term;
                    if(old_category.equals(SelectedCategory)){
                        Toast.makeText(getApplicationContext(), "Selected Term is Under Same Category",
                                Toast.LENGTH_SHORT).show();
                        flag=false;
                    }else {
                        TermList =  termDb.RowsAffetedInTerm(SelectedTopic,old_category,term);
                        if(TermList.size()<=0) {
                            termDb.move_term(tableinfo);
                            flag = true;
                        }else{
                            Toast.makeText(getApplicationContext(), "Selected Term already exists in selected Category, CANNOT MOVE",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }else{
                Toast.makeText(getApplicationContext(), "Select atleast Term to Move",
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


