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
import com.ellipsonic.database.TermDb;
import com.ellipsonic.database.NotesTable;


/**
 * Created by Ellipsonic PTE ltd on 5/27/2015.
 */

public class AddDescription extends Activity {
    public ImageView backButton_cat = null;
    public TextView description=null;
    public TextView des_save=null;
    public TextView Description_Name=null;
    public String selectedTerm;
    public String selectedTopic;
    public String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_description);
        getActionBar().hide();
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        selectedCategory = activityThatCalled.getExtras().getString("selectedCategory");
        selectedTerm = activityThatCalled.getExtras().getString("selectedTerm");
        description = (EditText) findViewById(R.id.add_des);
        des_save=(TextView)findViewById(R.id.textView4);

        des_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Description_Name = (EditText) findViewById(R.id.add_des);
                if (Description_Name.getText().toString().length() > 0) {
                    //insert into db
                    TermDb termDb =new TermDb(getApplicationContext());
                    NotesTable tableinfo = new NotesTable();
                    tableinfo.topic_name = selectedTopic;
                    tableinfo.category_name = selectedCategory;
                    tableinfo.term_name = selectedTerm;
                    tableinfo.description = Description_Name.getText().toString();
                    Description_Name.setText("");
                    termDb.insert_term(tableinfo);
                    Toast.makeText(getApplicationContext(), "saved",Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Nothing  to Save",Toast.LENGTH_LONG).show();
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
