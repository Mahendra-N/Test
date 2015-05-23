package com.ellipsonic.quickee;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ellipsonic.database.NotesTable;
import com.ellipsonic.database.TermDb;


public class AddNewTerm extends Activity {
    public ImageView backButton_term;
    public TextView term_save;
    public TextView term_name;
    public EditText sel_topic;
    public EditText sel_cat;
    public EditText description;
    String selectedTopic;
    String selectedCategory;
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
        sel_cat=(EditText)findViewById(R.id.sel_cat_name);
        sel_cat.setText(selectedCategory);
        sel_cat.setTextColor(Color.parseColor("#000000"));
        sel_cat.setEnabled(false);
        backButton_term = (ImageView) findViewById(R.id.new_term_back_icon);
        backButton_term.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });


        term_save=(TextView)findViewById(R.id.term_save);
        term_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                term_name =(EditText)findViewById(R.id.input_term_name);
                description =(EditText)findViewById(R.id.desc);
                if(term_name.getText().toString().length()>0 && description.getText().toString().length()>0 ){
                    //insert into db
                 TermDb termDb =new TermDb(getApplicationContext());
                    NotesTable tableinfo = new NotesTable();
                    tableinfo.topic_name=selectedTopic;
                    tableinfo.category_name=selectedCategory;
                    tableinfo.term_name =term_name.getText().toString();
                    tableinfo.description=description.getText().toString();
                    termDb.insert_term(tableinfo);
                     term_name.setText("");
                     description.setText("");
                     finish();

                }else{
                    Toast.makeText(getApplicationContext(), "Nothing  to Save",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
