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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ellipsonic.database.CategoryDb;
import com.ellipsonic.database.NotesTable;

import java.util.ArrayList;
import java.util.Collections;


public class EditCategory extends Activity {
    String selectedTopic;
    public CategoryDb cat_Db=null;
    public ArrayList<String> CatList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        getActionBar().hide();

        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");

        EDitCatListView(selectedTopic);
        ImageView back_button =(ImageView) findViewById(R.id.edit_cat_back_icon);
        TextView editclose=(TextView)findViewById(R.id.edit_cat_save);

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
        if(CatList!=null) {
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
        }else{
            Log.d("message", "nothing is there in database");
        }

    }

    public void AlertWindow(String clickedItem){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Quickee");
        final EditText input = new EditText(this);
        final String defaultTextValue  =clickedItem;
        input.setText(clickedItem);
        alertDialog.setView(input);
        // Setting Dialog Message
        alertDialog.setMessage("you want Update or Delete?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                 String updateTextValue = String.valueOf(input.getText());

                if(updateTextValue.length()>0) {
                    UpdateCategory(defaultTextValue, updateTextValue,selectedTopic);
                }else {
                    Toast.makeText(getApplicationContext(), "Enter value to update", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                String deleteTextValue = String.valueOf(input.getText());
                Delete_Category(deleteTextValue, selectedTopic);
               // Toast.makeText(getApplicationContext(), "You clicked Delete", Toast.LENGTH_SHORT).show();
                //dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void  Delete_Category(String deleteTextValue,String selectedTopic){
        //    Toast.makeText(getApplicationContext(), deleteTextValue, Toast.LENGTH_SHORT).show();
        CategoryDb catDB =new CategoryDb(getApplicationContext());
        NotesTable tableinfo = new NotesTable();
        tableinfo.topic_name =selectedTopic;
        tableinfo.category_name=deleteTextValue;
        catDB.delete_category(tableinfo);
        Intent intent = getIntent();
        finish();
        startActivity(intent);

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
            }else{
                Toast.makeText(getApplicationContext(), "Term " + CategoryName + " Exists, Enter Unique Term Name to Update",
                        Toast.LENGTH_LONG).show();
            }

        }

    }

}
