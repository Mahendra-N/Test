package com.ellipsonic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Ellip sonic on 22-05-2015.
 */
public class TermDb {

    private DatabaseHandler dbHelper;

    public TermDb(Context context) {
        dbHelper = new DatabaseHandler(context);
    }


   public void insert_term( NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NotesTable.KEY_TOPIC_NAME,tableinfo.topic_name);
        values.put(NotesTable.KEY_CATEGORY_NAME,tableinfo.category_name);
        values.put(NotesTable.KEY_TERM_NAME,tableinfo.term_name);
        values.put(NotesTable.KEY_DESCRIPTION,tableinfo.description);
        // Inserting Row
        db.insert(NotesTable.TABLE_NOTES, null, values);
        db.close(); // Closing database connection
    }


    public ArrayList<String> getTermList(String selectedTopic,String selectedCategory) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> TermList = new ArrayList<String>();
        String selectQuery =  " SELECT  " +
                " DISTINCT "+NotesTable.KEY_TERM_NAME +
                "  FROM  " + NotesTable.TABLE_NOTES+
                "  WHERE  "+ NotesTable.KEY_TOPIC_NAME +" = '"+selectedTopic+"'"+
                "  AND "+ NotesTable.KEY_CATEGORY_NAME+" = '"+selectedCategory+"'"+
                " AND  " +NotesTable.KEY_TERM_NAME + "  IS NOT NULL ";


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String termname = cursor.getString(cursor.getColumnIndex(NotesTable.KEY_TERM_NAME));
                TermList.add(termname);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return TermList;

    }

}
