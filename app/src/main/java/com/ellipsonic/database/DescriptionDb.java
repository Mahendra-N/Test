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


    public String[] getDetails(NotesTable tableinfo){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String  topic_name =tableinfo.topic_name;
        String  cat_name=tableinfo.category_name;
        String term_name=tableinfo.term_name;
        ContentValues values = new ContentValues();
        String selectQuery =  "SELECT  " +
                " DISTINCT "+NotesTable.KEY_DESCRIPTION +" ,"+NotesTable.KEY_TERM_NAME
                +" ,"+NotesTable.KEY_IMAGE+" ,"+NotesTable.KEY_VIDEO+" ,"+NotesTable.KEY_AUDIO+
                "  FROM  " + NotesTable.TABLE_NOTES+
                "  WHERE  "+ NotesTable.KEY_TOPIC_NAME +"='"+topic_name+"'"+
                "  AND  " + NotesTable.KEY_CATEGORY_NAME +"='"+cat_name+"'"+
                "  AND  " +NotesTable.KEY_TERM_NAME +"='"+term_name+"'"+
                "  AND  " + NotesTable.KEY_DESCRIPTION + "  IS NOT NULL ";

        Cursor cursor = db.rawQuery(selectQuery, null);
String[] details =new String[5];
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                details[0]= cursor.getString(cursor.getColumnIndex(NotesTable.KEY_DESCRIPTION));
                details[1]= cursor.getString(cursor.getColumnIndex(NotesTable.KEY_TERM_NAME));
                details[2]=cursor.getString(cursor.getColumnIndex(NotesTable.KEY_IMAGE));
                details[3]=cursor.getString(cursor.getColumnIndex(NotesTable.KEY_VIDEO));
                details[4]=cursor.getString(cursor.getColumnIndex(NotesTable.KEY_AUDIO));
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
