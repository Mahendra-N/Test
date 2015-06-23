package com.ellipsonic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Ellip sonic on 19-05-2015.
 */
public class CategoryDb {

   private DatabaseHandler dbHelper;

    public CategoryDb(Context context) {
        dbHelper = new DatabaseHandler(context);
    }


    public void insert_category( NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NotesTable.KEY_TOPIC_NAME,tableinfo.topic_name); // Contact Name
        values.put(NotesTable.KEY_CATEGORY_NAME,tableinfo.category_name);
        // Inserting Row
        db.insert(NotesTable.TABLE_NOTES, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<String> getCatList(String selectedTopic) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                " DISTINCT  "+ " ( " +NotesTable.KEY_CATEGORY_NAME  + " || " +" '\n' "+" || "+
                " COUNT " + "( " + " DISTINCT " + " ( " +NotesTable.KEY_TERM_NAME+")"+ " )" + " )"+ " AS "+
                NotesTable.KEY_CATEGORY_NAME+
                " FROM  " + NotesTable.TABLE_NOTES +
                "  WHERE  "+ NotesTable.KEY_TOPIC_NAME +"='"+selectedTopic+"'"+
                " AND  " +NotesTable.KEY_CATEGORY_NAME + "  IS NOT NULL "+
               " GROUP BY "+NotesTable.KEY_CATEGORY_NAME;

        ArrayList<String> CatList = new ArrayList<String>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String catname = cursor.getString(cursor.getColumnIndex(NotesTable.KEY_CATEGORY_NAME));
                CatList.add(catname);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return CatList;

    }
    public ArrayList<String> getEditCatList(String selectedTopic) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                " DISTINCT  " +NotesTable.KEY_CATEGORY_NAME  +
                " FROM  " + NotesTable.TABLE_NOTES +
                "  WHERE  "+ NotesTable.KEY_TOPIC_NAME +"='"+selectedTopic+"'"+
                " AND  " +NotesTable.KEY_CATEGORY_NAME + "  IS NOT NULL ";


        ArrayList<String> CatList = new ArrayList<String>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String catname = cursor.getString(cursor.getColumnIndex(NotesTable.KEY_CATEGORY_NAME));
                CatList.add(catname);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return CatList;

    }

    public ArrayList<String> RowsAffetedInCategory( String selectedTopic, String Categoryname) {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                  NotesTable.KEY_CATEGORY_NAME +
                "  FROM  " + NotesTable.TABLE_NOTES+
                "  WHERE  "+ NotesTable.KEY_TOPIC_NAME +"='"+selectedTopic+"'"+
                "  AND  " + NotesTable.KEY_CATEGORY_NAME +"='"+Categoryname+"'";

        ArrayList<String> CatList = new ArrayList<String>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                String catname = cursor.getString(cursor.getColumnIndex(NotesTable.KEY_CATEGORY_NAME));
                CatList.add(catname);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return CatList;

    }

    public void delete_category( NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String  topic_name =tableinfo.topic_name;
        String  cat_name=tableinfo.category_name;
        ContentValues values = new ContentValues();
        db.delete(NotesTable.TABLE_NOTES, NotesTable.KEY_TOPIC_NAME + "='"+topic_name+"'" +" AND "+
                NotesTable.KEY_CATEGORY_NAME+ "='"+cat_name+"'"  , null);
        db.close(); // Closing database connection

    }

    public void update_category( NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(NotesTable.KEY_CATEGORY_NAME,tableinfo.category_name);
        db.update(NotesTable.TABLE_NOTES, val, NotesTable.KEY_CATEGORY_NAME + "= '"+tableinfo.old_cat_name+"'"
                +" AND "+NotesTable.KEY_TOPIC_NAME+ "='"+tableinfo.topic_name+"'", null);
        db.close(); // Closing database connection*/

    }
    public void move_category( NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(NotesTable.KEY_TOPIC_NAME,tableinfo.old_topic_name);
        db.update(NotesTable.TABLE_NOTES, val, NotesTable.KEY_TOPIC_NAME + "= '"+tableinfo.topic_name+"'"
                +" AND "+NotesTable.KEY_CATEGORY_NAME+ "='"+tableinfo.category_name+"'", null);
        db.close(); // Closing database connection*/

    }
}
