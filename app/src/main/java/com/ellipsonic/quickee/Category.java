package com.ellipsonic.quickee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ellipsonic.database.CategoryDb;

import java.util.ArrayList;
import java.util.Collections;


public class Category extends Activity {
    String selectedTopic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent activityThatCalled = getIntent();
        selectedTopic = activityThatCalled.getExtras().getString("selectedTopic");
      //  Toast.makeText(this, selectedTopic, Toast.LENGTH_LONG).show();
       CatListView(selectedTopic);
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
               case R.id.add_category:
               Intent intent = new Intent(this, AddNewCat.class);
            //   this.startActivity(intent);
               final int result = 1;
               intent.putExtra("selectedTopic",selectedTopic);
               startActivityForResult(intent, result);
                break;
            case R.id.edit_category:
               Intent edit_intent = new Intent(this, EditCategory.class);
               // this.startActivity(edit_intent);
                final int resulteditcat = 1;
                edit_intent.putExtra("selectedTopic",selectedTopic);
                startActivityForResult(edit_intent, resulteditcat);
                break;
                default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }



   public void CatListView( String selectedTopic){
       CategoryDb cat_Db=new CategoryDb(getApplicationContext());
        ArrayList<String> CatList =  cat_Db.getCatList(selectedTopic);
        CatList.removeAll(Collections.singleton(null));
            if(CatList!=null) {
                ArrayAdapter<String> Adapter = new
                   ArrayAdapter<String>(this,
                   android.R.layout.simple_list_item_1,
                   CatList);
           ListView List = (ListView) this.findViewById(R.id.cat_listView);
                  List.setAdapter(Adapter);

           List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position,
                                       long id) {

                   Intent intent = new Intent(Category.this, Term.class);
                   startActivity(intent);

               }

           });
       }else{
           Log.d("message","nothing is there in database");
       }

    }


}
