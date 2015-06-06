package com.ellipsonic.quickee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ellipsonic.database.CategoryDb;
import com.ellipsonic.database.NotesTable;

import java.util.ArrayList;


public class AddNewCat extends Activity {
    public ImageView backButton_cat = null;
    public TextView cat_save=null;
    public  EditText Category_Name=null;
    String selectedTopic;
    public CategoryDb cat_Db=null;
    public ArrayList<String> CatList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_cat);
        getActionBar().hide();
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
    //    Toast.makeText(this, selectedTopic, Toast.LENGTH_LONG).show();

        backButton_cat = (ImageView) findViewById(R.id.new_cat_back_icon);
        backButton_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cat_save=(TextView)findViewById(R.id.cat_save);
        cat_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Category_Name =(EditText)findViewById(R.id.Cat_Name);
                String CategoryName=Category_Name.getText().toString();
                cat_Db=new CategoryDb(getApplicationContext());
                CatList =  cat_Db.RowsAffetedInCategory(selectedTopic, CategoryName);

               /* if(Category_Name.getText().toString().length()>0){
                    CategoryDb categoryDb = new CategoryDb(getApplicationContext());
                    NotesTable tableinfo = new NotesTable();
                    tableinfo.category_name = Category_Name.getText().toString();
                    tableinfo.topic_name = selectedTopic;
                    categoryDb.insert_category(tableinfo);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Nothing  to Save",
                            Toast.LENGTH_LONG).show();
                }*/

                if (CategoryName.length()> 0) {
                    if(CatList.size()<=0){
                        CategoryDb categoryDb = new CategoryDb(getApplicationContext());
                        NotesTable tableinfo = new NotesTable();
                        tableinfo.category_name = Category_Name.getText().toString();
                        tableinfo.topic_name = selectedTopic;
                        categoryDb.insert_category(tableinfo);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Term " + CategoryName + " Exists,Please Enter Unique Term Name",
                                Toast.LENGTH_LONG).show();
                    }

                } else{
                    Toast.makeText(getApplicationContext(), "Enter Category Name",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
              finish();
    }
    @Override
    public void onPause() {
        super.onPause();
             finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_cat, menu);
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
