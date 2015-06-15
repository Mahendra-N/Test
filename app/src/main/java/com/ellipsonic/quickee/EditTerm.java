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

import com.ellipsonic.database.NotesTable;
import com.ellipsonic.database.TermDb;

import java.util.ArrayList;
import java.util.Collections;


public class EditTerm extends Activity {
    String selectedTopic;
    String selectedCategory;
    public TermDb term_Db=null;
    public ArrayList<String> TermList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);
        getActionBar().hide();
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        selectedCategory =activityThatCalled.getExtras().getString("selectedCategory");
        EditTermListView(selectedTopic, selectedCategory);
        ImageView back_button =(ImageView) findViewById(R.id.edit_term_back_icon);
        TextView editclose=(TextView)findViewById(R.id.edit_term_save);
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
        getMenuInflater().inflate(R.menu.menu_edit_term, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void EditTermListView( final String selectedTopic,final String selectedCategory){
        term_Db=new TermDb(getApplicationContext());
        TermList =  term_Db.getEditTermList(selectedTopic,selectedCategory);
        TermList.removeAll(Collections.singleton(null));
        if(TermList!=null) {
            ArrayAdapter<String> Adapter = new
                    ArrayAdapter<String>(this,
                    R.layout.customeditlist,
                    R.id.Editname,TermList);
            ListView List = (ListView) this.findViewById(R.id.edit_term_listView);
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
                    UpdateTerm(defaultTextValue, updateTextValue, selectedTopic, selectedCategory);
                }else {
                    Toast.makeText(getApplicationContext(), "Enter value to update", Toast.LENGTH_SHORT).show();
                }
            }
        });


        alertDialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                String deleteTextValue = String.valueOf(input.getText());
                Delete_Term(deleteTextValue, selectedTopic, selectedCategory);

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void  Delete_Term(String deleteTextValue,String selectedTopic,String selectedCategory){
        //    Toast.makeText(getApplicationContext(), deleteTextValue, Toast.LENGTH_SHORT).show();
        term_Db=new TermDb(getApplicationContext());
        NotesTable tableinfo = new NotesTable();
        tableinfo.topic_name =selectedTopic;
        tableinfo.category_name=selectedCategory;
        tableinfo.term_name=deleteTextValue;
        term_Db.delete_term(tableinfo);
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }

    public void UpdateTerm(String defaultTextValue, String updateTextValue,String selectedTopic,String selectedCategory){


       String selectedTerm = updateTextValue;
        term_Db=new TermDb(getApplicationContext());
        TermList =  term_Db.RowsAffetedInTerm(selectedTopic,selectedCategory,selectedTerm);
        if (selectedTerm.length()> 0) {

            if(TermList.size()<=0){
                term_Db=new TermDb(getApplicationContext());
                NotesTable tableinfo = new NotesTable();
                tableinfo.old_term_name=defaultTextValue;
                tableinfo.term_name=updateTextValue;
                tableinfo.topic_name=selectedTopic;
                tableinfo.category_name=selectedCategory;
                term_Db.update_term(tableinfo);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "Term " + selectedTerm + " Exists,Enter Unique Term to Update",
                        Toast.LENGTH_LONG).show();
            }

        }
    }

}
