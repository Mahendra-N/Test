package com.ellipsonic.quickee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.ellipsonic.database.CategoryDb;

import java.util.ArrayList;
import java.util.Collections;


public class EditCategory extends Activity {
    String selectedTopic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        getActionBar().hide();

        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");

        EDitCatListView(selectedTopic);
        ImageView back_button =(ImageView) findViewById(R.id.edit_cat_back_icon);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    /*    String[]  myStringArray={"Air Force","Plane","Auto","Military"};
        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                myStringArray);
        ListView myList=(ListView)
                findViewById(R.id.edit_cat_listView);
        myList.setAdapter(myAdapter);*/


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
        ArrayList<String> CatList =  cat_Db.getCatList(selectedTopic);
        CatList.removeAll(Collections.singleton(null));
        if(CatList!=null) {
            ArrayAdapter<String> Adapter = new
                    ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    CatList);
            ListView List = (ListView) this.findViewById(R.id.edit_cat_listView);
            List.setAdapter(Adapter);

     /*       List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {

                    Intent intent = new Intent(Category.this, Term.class);
                    startActivity(intent);

                }

            });*/
        }else{
            Log.d("message", "nothing is there in database");
        }

    }

}
