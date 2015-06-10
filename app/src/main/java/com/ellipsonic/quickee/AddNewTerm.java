package com.ellipsonic.quickee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ellipsonic.database.CategoryDb;
import com.ellipsonic.database.TermDb;

import java.util.ArrayList;
import java.util.Collections;


public class AddNewTerm extends Activity {
    public ImageView backButton_term;
    public TextView term_save;
    public EditText term_name;
    public EditText sel_topic;
    public EditText sel_cat;
    public EditText description;
    public TextView add_desc;
    public  String selectedTopic;
   public  String selectedCategory;
   public  String selectedTerm;
    Spinner spinner;
    public TermDb term_Db=null;
    public ArrayList<String> TermList=null;
    public CategoryDb cat_Db=null;
    ArrayAdapter<String> Adapter;
    ArrayList<String> CatList=null;
 public    boolean term_flag =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_term);
        getActionBar().hide();
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        selectedCategory =activityThatCalled.getExtras().getString("selectedCategory");
        sel_topic=(EditText)findViewById(R.id.sel_topic_name);
        sel_topic.setText(selectedTopic);
        sel_topic.setTextColor(Color.parseColor("#000000"));
        sel_topic.setEnabled(false);
        CategoryDropDown(selectedTopic);
     /*   sel_cat=(EditText)findViewById(R.id.sel_cat_name);
        sel_cat.setText(selectedCategory);
        sel_cat.setTextColor(Color.parseColor("#000000"));
        sel_cat.setEnabled(false);*/
        backButton_term = (ImageView) findViewById(R.id.new_term_back_icon);
        backButton_term.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button button = (Button) findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                selectedCategory= String.valueOf(spinner.getSelectedItem());
                term_name = (EditText) findViewById(R.id.input_term_name);
                selectedTerm = term_name.getText().toString();
                term_Db=new TermDb(getApplicationContext());
                TermList =  term_Db.RowsAffetedInTerm(selectedTopic,selectedCategory,selectedTerm);

     if (selectedTerm.length()> 0) {
         if(TermList.size()<=0){
               Intent intent = new Intent(v.getContext(), AddDescription.class);
                    final int result = 1;
                    intent.putExtra("selectedTopic",selectedTopic);
                    intent.putExtra("selectedCategory",selectedCategory);
                    intent.putExtra("selectedTerm",selectedTerm);
                    term_name.setText("");
                    startActivityForResult(intent, result);
         }else{
             Toast.makeText(getApplicationContext(), "Term "+selectedTerm+" Exists,Please Enter Unique Term Name",
                     Toast.LENGTH_LONG).show();
         }

     } else{
                    Toast.makeText(getApplicationContext(), "Enter Term Name",
                            Toast.LENGTH_LONG).show();
                }
      }


        });
    }

    @Override
     public void onPause() {
        super.onPause();
       finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_term, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void CategoryDropDown(String selectedTopic) {
        cat_Db = new CategoryDb(getApplicationContext());
        spinner = (Spinner) findViewById(R.id.sel_cat_name);
        CatList = cat_Db.getEditCatList(selectedTopic);
        CatList.removeAll(Collections.singleton(null));
        if (CatList != null) {
            Adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, CatList);
            Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(Adapter);
                   }
    }


    public void AlertWindow(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Quickee");
         // Setting Dialog Message
        alertDialog.setMessage("Term Name should be Unique,Please enter new Term Name");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            dialog.dismiss();
            }
        });




        // Showing Alert Message
        alertDialog.show();
    }
}
