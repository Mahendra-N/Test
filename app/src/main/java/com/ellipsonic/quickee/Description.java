package com.ellipsonic.quickee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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


public class Description extends Activity {
    public DescriptionDb desc_Db=null;
    TextView article;
    TextView header;
    ImageView delete;
    ImageView edit;
    String content;
    String selectedTopic;
    String selectedCategory;
    String selectedTerm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        getActionBar().hide();
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
        selectedCategory =activityThatCalled.getExtras().getString("selectedCategory");
        selectedTerm =activityThatCalled.getExtras().getString("selectedTerm");
        article = (TextView) findViewById(R.id.content);
        header=(TextView) findViewById(R.id.details_header);
        Description(selectedTopic,selectedCategory,selectedTerm);
        ImageView back_button =(ImageView) findViewById(R.id.desc_back_icon);
        delete =(ImageView) findViewById(R.id.desc_delete);
        edit =(ImageView) findViewById(R.id.edit_desc);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDescription(selectedTopic,selectedCategory,selectedTerm);

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertWindowTopic(content);
            }
        });

       // ImageView img= (ImageView) findViewById(R.id.img);
       /// img.setImageResource(R.drawable.airplane);
    }
    @Override
    public void onPause() {
        super.onPause();
        desc_Db = null;

    }
    @Override
    public void onResume() {
        super.onResume();
        Description(selectedTopic, selectedCategory, selectedTerm);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
                default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void Description(String selectedTopic,String selectedCategory,String selectedTerm){

        desc_Db=new DescriptionDb(getApplicationContext());
        NotesTable tableinfo = new NotesTable();
        tableinfo.topic_name=selectedTopic;
        tableinfo.category_name=selectedCategory;
        tableinfo.term_name=selectedTerm;
        String[] details=desc_Db.getDetails(tableinfo);
        content=details[0];
        article.setText(content);
        header.setText(details[1]);

    }

 public void  DeleteDescription(String selectedTopic,String selectedCategory,String selectedTerm){
     desc_Db=new DescriptionDb(getApplicationContext());
     NotesTable tableinfo = new NotesTable();
     tableinfo.topic_name=selectedTopic;
     tableinfo.category_name=selectedCategory;
     tableinfo.term_name=selectedTerm;
     desc_Db.delete_description(tableinfo);
     finish();
 }
    public void AlertWindowTopic(final String article){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");
        final EditText input = new EditText(this);
        input.setText(article);
        final String defaultTextValue  =article;

        alertDialog.setView(input);
        // Setting Dialog Message
        alertDialog.setMessage("You want Update Details?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String updateTextValue = String.valueOf(input.getText());
                UpdateDescription(updateTextValue,selectedTopic,selectedCategory,selectedTerm);
                //  Toast.makeText(getApplicationContext(), EditTextValue, Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    public void UpdateDescription(String updateTextValue,String selectedTopic,String selectedCategory,String selectedTerm){
        desc_Db=new DescriptionDb(getApplicationContext());
        NotesTable tableinfo = new NotesTable();
        tableinfo.description=updateTextValue;
        tableinfo.topic_name=selectedTopic;
        tableinfo.category_name=selectedCategory;
        tableinfo.term_name=selectedTerm;
        desc_Db.update_description(tableinfo);
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }
}
