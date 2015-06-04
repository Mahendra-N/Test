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

import com.ellipsonic.database.DescriptionDb;
import com.ellipsonic.database.NotesTable;

public class EditDescription extends Activity {
    public DescriptionDb desc_Db=null;
    String selecteddetails;
    EditText details;
    TextView save;
    EditText updateTextValue;
    ImageView backbutton;
    String selectedTopic;
    String selectedCategory;
    String selectedTerm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_description);
        getActionBar().hide();
        Intent activityThatCalled = getIntent();
        selecteddetails = activityThatCalled.getExtras().getString("content");
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        selectedCategory =activityThatCalled.getExtras().getString("selectedCategory");
        selectedTerm =activityThatCalled.getExtras().getString("selectedTerm");
        details= (EditText)findViewById(R.id.editdetails);
        details.setText(selecteddetails);
        save=(TextView) findViewById(R.id.edit_details_save);
        updateTextValue=(EditText)findViewById(R.id.editdetails);


        backbutton=(ImageView)findViewById(R.id.edit_details_back_icon);
        backbutton.setOnClickListener(new View.OnClickListener() {
            public  void onClick(View view){
                finish();
            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UpdateDescription(updateTextValue.getText().toString(),selectedTopic,selectedCategory,selectedTerm);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_description, menu);
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

    public void UpdateDescription(String updateTextValue,String selectedTopic,String selectedCategory,String selectedTerm){
        desc_Db=new DescriptionDb(getApplicationContext());
        NotesTable tableinfo = new NotesTable();
        tableinfo.description=updateTextValue;
        tableinfo.topic_name=selectedTopic;
        tableinfo.category_name=selectedCategory;
        tableinfo.term_name=selectedTerm;
        desc_Db.update_description(tableinfo);
        finish();


    }
}
