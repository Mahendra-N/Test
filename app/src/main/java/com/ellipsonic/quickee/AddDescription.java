package com.ellipsonic.quickee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ellipsonic.database.NotesTable;
import com.ellipsonic.database.TermDb;

public class AddDescription extends Activity {
    String selectedTopic;
    String selectedCategory;
    String selectedTerm;
    EditText details;
    TextView save_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_description);
        getActionBar().hide();
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        selectedCategory =activityThatCalled.getExtras().getString("selectedCategory");
        selectedTerm =activityThatCalled.getExtras().getString("selectedTerm");
        details=(EditText) findViewById(R.id.desc);
        save_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(details.getText().toString().length()>0){

                 TermDb termDb =new TermDb(getApplicationContext());
                    NotesTable tableinfo = new NotesTable();
                    tableinfo.topic_name=selectedTopic;
                    tableinfo.category_name=selectedCategory;
                    tableinfo.term_name=selectedTerm;
                    tableinfo.description=details.getText().toString();
                    termDb.insert_term(tableinfo);
                     details.setText("");
                     finish();
               }else{
                   Toast.makeText(getApplicationContext(),"Enter description ",Toast.LENGTH_LONG).show();
               }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_description, menu);
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
