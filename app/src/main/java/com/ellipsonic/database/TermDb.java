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
        values.put(NotesTable.KEY_IMAGE,tableinfo.image);
        values.put(NotesTable.KEY_VIDEO,tableinfo.video);
        values.put(NotesTable.KEY_AUDIO,tableinfo.audio);
        // Inserting Row
        db.insert(NotesTable.TABLE_NOTES, null, values);
        db.close(); // Closing database connection
    }


    public ArrayList<String> getTermList(String selectedTopic,String selectedCategory) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> TermList = new ArrayList<String>();
        String selectQuery =  " SELECT  " +
                " DISTINCT "+" ( "+NotesTable.KEY_TERM_NAME +" || "+" '\n' "+" || "+" " +
                "SUBSTR "+"(" +NotesTable.KEY_DESCRIPTION +" , "+" 0, "+" 35 " +")"+" ) "+ " AS "+
                NotesTable.KEY_TERM_NAME+
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
    public ArrayList<String> getEditTermList(String selectedTopic,String selectedCategory) {
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

    public ArrayList<String> RowsAffetedInTerm(String selectedTopic,String selectedCategory,String selectedTerm) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> TermList = new ArrayList<String>();
        String selectQuery =  " SELECT  " +
                 NotesTable.KEY_TERM_NAME +
                "  FROM  " + NotesTable.TABLE_NOTES+
                "  WHERE  "+ NotesTable.KEY_TOPIC_NAME +" = '"+selectedTopic+"'"+
                "  AND "+ NotesTable.KEY_CATEGORY_NAME+" = '"+selectedCategory+"'"+
                " AND  " +NotesTable.KEY_TERM_NAME + "='"+selectedTerm+"'";
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
    public void delete_term( NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String  topic_name =tableinfo.topic_name;
        String  cat_name=tableinfo.category_name;
        String  term_name=tableinfo.term_name;
        ContentValues values = new ContentValues();
        db.delete(NotesTable.TABLE_NOTES,  NotesTable.KEY_TERM_NAME + "='"+term_name+"'" +" AND "
                +NotesTable.KEY_TOPIC_NAME + "='"+topic_name+"'" +" AND "+
                NotesTable.KEY_CATEGORY_NAME+ "='"+cat_name+"'"  , null);
        db.close(); // Closing database connection

    }
    public void update_term( NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(NotesTable.KEY_TERM_NAME,tableinfo.term_name);
        db.update(NotesTable.TABLE_NOTES, val, NotesTable.KEY_TERM_NAME + "= '" + tableinfo.old_term_name + "'"
                + " AND " + NotesTable.KEY_TOPIC_NAME + "='" + tableinfo.topic_name + "'"
                + " AND " + NotesTable.KEY_CATEGORY_NAME + "='" + tableinfo.category_name + "'", null);
        db.close(); // Closing database connection*/
    }


}
