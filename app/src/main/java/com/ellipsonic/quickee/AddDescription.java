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


/**
 * Created by Ellipsonic PTE ltd on 5/27/2015.
 */

public class AddDescription extends Activity {

    public ImageView backButton = null;
    public EditText description = null;
    public TextView des_save = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_description);
        getActionBar().hide();
        description = (EditText) findViewById(R.id.add_des);
        des_save = (TextView) findViewById(R.id.textView4);
        backButton = (ImageView) findViewById(R.id.backButton);



        des_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String desc =description.getText().toString();
                if (description.getText().length() > 0) {
                    //insert into db
                    Intent intent = new Intent();
                    intent.putExtra("Description",desc);
                    setResult(RESULT_OK, intent);
                    description.setText("");
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Add Defination", Toast.LENGTH_LONG).show();
                }
            }
        });


        backButton.setOnClickListener(new View.OnClickListener(){
            public  void  onClick(View v){
                Intent intent = new Intent();
                intent.putExtra("Description","");
                setResult(RESULT_OK, intent);
              finish();
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