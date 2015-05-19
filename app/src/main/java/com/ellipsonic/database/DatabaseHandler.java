package com.ellipsonic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ellip sonic on 15-05-2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Quickee.db";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    public void onCreate(SQLiteDatabase db){
        // Creating Tables
        String CREATE_NOTES_TABLE = "CREATE TABLE " + NotesTable.TABLE_NOTES + "("
                + NotesTable.KEY_ID + " INTEGER PRIMARY KEY," + NotesTable.KEY_TOPIC_NAME + " TEXT,"
                + NotesTable.KEY_CATEGORY_NAME + " TEXT" + NotesTable.KEY_TERM_NAME +"TEXT"+
                NotesTable.KEY_DESCRIPTION +"TEXT"+NotesTable.KEY_IMAGE +"TEXT"+ NotesTable.KEY_AUDIO
                +"TEXT"+ NotesTable.KEY_VIDEO+"TEXT" +")";
        db.execSQL(CREATE_NOTES_TABLE);
            Log.d("database :","Quickee table is created");
    }

    // Upgrading database

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + NotesTable.TABLE_NOTES);
        // Create tables again
        onCreate(db);

    }



}
