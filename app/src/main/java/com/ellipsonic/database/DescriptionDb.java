package com.ellipsonic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ellip sonic on 23-05-2015.
 */
public class DescriptionDb {


    private DatabaseHandler dbHelper;

    public DescriptionDb(Context context) {
        dbHelper = new DatabaseHandler(context);
    }
    public void insert_description( NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NotesTable.KEY_TERM_NAME,tableinfo.input_term_name); // Contact Name
        values.put(NotesTable.KEY_DESCRIPTION,tableinfo.add_des);
        // Inserting Row
        db.insert(NotesTable.TABLE_NOTES, null, values);
        db.close(); // Closing database connection
    }

    public String[] getDetails(NotesTable tableinfo){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String  topic_name =tableinfo.topic_name;
        String  cat_name=tableinfo.category_name;
        String term_name=tableinfo.term_name;
        ContentValues values = new ContentValues();
        String selectQuery =  "SELECT  " +
                " DISTINCT "+NotesTable.KEY_DESCRIPTION +" ,"+NotesTable.KEY_TERM_NAME+
                "  FROM  " + NotesTable.TABLE_NOTES+
                "  WHERE  "+ NotesTable.KEY_TOPIC_NAME +"='"+topic_name+"'"+
                "  AND  " + NotesTable.KEY_CATEGORY_NAME +"='"+cat_name+"'"+
                "  AND  " +NotesTable.KEY_TERM_NAME +"='"+term_name+"'"+
                "  AND  " + NotesTable.KEY_DESCRIPTION + "  IS NOT NULL ";

        Cursor cursor = db.rawQuery(selectQuery, null);
String[] details =new String[2];
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                details[0] = cursor.getString(cursor.getColumnIndex(NotesTable.KEY_DESCRIPTION));
                details[1] = cursor.getString(cursor.getColumnIndex(NotesTable.KEY_TERM_NAME));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return details;
    }


    public void delete_description( NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String  topic_name =tableinfo.topic_name;
        String  cat_name=tableinfo.category_name;
        String  term_name=tableinfo.term_name;
        ContentValues values = new ContentValues();
        db.delete(NotesTable.TABLE_NOTES, NotesTable.KEY_TOPIC_NAME + "='"+topic_name+"'" +" AND "+
                NotesTable.KEY_CATEGORY_NAME+ "='"+cat_name+"'" +" AND "+
                NotesTable.KEY_TERM_NAME+ "='"+term_name+"'"  , null);
        db.close(); // Closing database connection

    }
    public void update_description( NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(NotesTable.KEY_DESCRIPTION,tableinfo.description);
        db.update(NotesTable.TABLE_NOTES, val,NotesTable.KEY_TOPIC_NAME+ "='"+tableinfo.topic_name+"'"
                +" AND "+NotesTable.KEY_CATEGORY_NAME + "= '"+tableinfo.category_name+"'"
                +" AND "+NotesTable.KEY_TERM_NAME+ "='"+tableinfo.term_name+"'", null);
        db.close(); // Closing database connection*/

    }

}
