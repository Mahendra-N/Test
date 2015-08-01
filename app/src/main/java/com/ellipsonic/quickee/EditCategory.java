package com.ellipsonic.quickee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Collections;


public class EditCategory extends Activity {
    String SelectedTopic;
    Spinner spinner;
    public CategoryDb cat_Db=null;
    public ArrayList<String> CatList=null;
    ImageButton back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_category);
        getActionBar().hide();
        Intent activityThatCalled = getIntent();
        SelectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        EDitCatListView( SelectedTopic);
       // TopicsDropDown();
         back_button =(ImageButton) findViewById(R.id.edit_cat_back_icon);


        Button editclose=(Button)findViewById(R.id.edit_cat_save);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_category, menu);
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

    public void EDitCatListView( String selectedTopic){

        CategoryDb cat_Db=new CategoryDb(getApplicationContext());
        ArrayList<String> CatList =  cat_Db.getEditCatList(selectedTopic);
        CatList.removeAll(Collections.singleton(null));
        if(CatList.size()>0) {
            findViewById(R.id.blank_msg).setVisibility(View.GONE);
            findViewById(R.id.alert).setVisibility(View.GONE);
            ArrayAdapter<String> Adapter = new
                    ArrayAdapter<String>(this,
                    R.layout.customeditlist,
                    R.id.Editname,CatList);
            ListView List = (ListView) this.findViewById(R.id.edit_cat_listView);
            List.setAdapter(Adapter);

            List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    String clickedItem  = String.valueOf(parent.getItemAtPosition(position));
                  AlertWindow(clickedItem);

                }
            });
        }
        if (CatList.size()==0){
            findViewById(R.id.blank_msg).setVisibility(View.VISIBLE);
            findViewById(R.id.alert).setVisibility(View.VISIBLE);
            Log.d("message", "nothing is there in database");
        }

    }

    public void AlertWindow(String clickedItem){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Quickee");
        final EditText input = new EditText(this);
        input.setSingleLine();
        final String defaultTextValue  =clickedItem;
        input.setText(clickedItem);
        alertDialog.setView(input);
        // Setting Dialog Message
        alertDialog.setMessage("you want to Update or Delete?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                 String updateTextValue = String.valueOf(input.getText()).trim();

                if(updateTextValue.length()>0) {
                    UpdateCategory(defaultTextValue, updateTextValue,SelectedTopic);
                }else {
                    Toast.makeText(getApplicationContext(), "Enter category name to update", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                String deleteTextValue = String.valueOf(input.getText()).trim();

                if(deleteTextValue.length()>0) {
                    Delete_Category(deleteTextValue, SelectedTopic,defaultTextValue);
                }else {
                    Toast.makeText(getApplicationContext(), "Enter category name to delete", Toast.LENGTH_SHORT).show();
                }
               // EDitCatListView( SelectedTopic);
               // Toast.makeText(getApplicationContext(), "You clicked Delete", Toast.LENGTH_SHORT).show();
                //dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void  Delete_Category(String deleteTextValue, String selectedTopic, String defaultTextValue){
        if(deleteTextValue.equals(defaultTextValue)) {
            CategoryDb catDB = new CategoryDb(getApplicationContext());
            NotesTable tableinfo = new NotesTable();
            tableinfo.topic_name = selectedTopic;
            tableinfo.category_name = deleteTextValue;
            catDB.delete_category(tableinfo);
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Category " + deleteTextValue + " deleted successfully",
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "Category not found to delete",   Toast.LENGTH_SHORT).show();
        }
    }
    public void UpdateCategory(String defaultTextValue, String updateTextValue,String selectedTopic){
        String CategoryName=updateTextValue;
        cat_Db=new CategoryDb(getApplicationContext());
        CatList =  cat_Db.RowsAffetedInCategory(selectedTopic, CategoryName);

        if (CategoryName.length()> 0) {
            if(CatList.size()<=0){
                CategoryDb catDB =new CategoryDb(getApplicationContext());
                NotesTable tableinfo = new NotesTable();
                tableinfo.old_cat_name=defaultTextValue;
                tableinfo.category_name =updateTextValue;
                tableinfo.topic_name=selectedTopic;
                catDB.update_category(tableinfo);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                Toast.makeText(getApplicationContext(), " Category " + defaultTextValue + " Updated to " +updateTextValue,
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Category " + CategoryName + " Exists, Enter Unique Category Name to Update",
                        Toast.LENGTH_LONG).show();
            }

        }

    }



}
