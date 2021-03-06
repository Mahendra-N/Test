package com.ellipsonic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Ellip sonic on 16-05-2015.
 */
public class TopicDb {

    private DatabaseHandler dbHelper;

    public TopicDb(Context context) {
                     dbHelper = new DatabaseHandler(context);
    }


    public void insert_topic( NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NotesTable.KEY_TOPIC_NAME,tableinfo.topic_name); // Contact Name

        // Inserting Row
        db.insert(NotesTable.TABLE_NOTES, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<String> getTopicList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery =  "SELECT  " +
                "DISTINCT  "+NotesTable.KEY_TOPIC_NAME  +
                " FROM  " + NotesTable.TABLE_NOTES;


        ArrayList<String> topicList = new ArrayList<String>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {

                String topicname=cursor.getString(cursor.getColumnIndex(NotesTable.KEY_TOPIC_NAME));
               // HashMap<String, String> topicname = new HashMap<String, String>();
                //topicname.put("topic_name", cursor.getString(cursor.getColumnIndex(NotesTable.KEY_TOPIC_NAME)));
                topicList.add(topicname);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return topicList;

    }

    public void delete_topic( NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String  delete_topic_name =tableinfo.topic_name;
        ContentValues values = new ContentValues();
        db.delete(NotesTable.TABLE_NOTES, NotesTable.KEY_TOPIC_NAME + "='"+delete_topic_name+"'", null);
        db.close(); // Closing database connection

    }
    public void update_topic( NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
     /*   String  update_topic_name =tableinfo.topic_name;
         String  old_topic_name=tableinfo.old_topic_name;
             String updateQuery =  "UPDATE  " +
             NotesTable.TABLE_NOTES +
            "  SET  "+ NotesTable.KEY_TOPIC_NAME +" = '"+update_topic_name+"' "+
            "  WHERE  "+NotesTable.KEY_TOPIC_NAME +" = '"+old_topic_name+"' ";
        db.rawQuery(updateQuery, null);

      db.close();*/ // Closing database connection*/
       val.put(NotesTable.KEY_TOPIC_NAME ,tableinfo.topic_name);
        db.update(NotesTable.TABLE_NOTES, val, NotesTable.KEY_TOPIC_NAME + "= '"+tableinfo.old_topic_name+"'", null);
        db.close(); // Closing database connection*/

    }

}
