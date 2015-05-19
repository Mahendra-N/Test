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
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                NotesTable.KEY_TOPIC_NAME  +
                " FROM " + NotesTable.TABLE_NOTES;


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

}
