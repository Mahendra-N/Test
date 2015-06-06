package com.ellipsonic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Ellip sonic on 03-06-2015.
 */
public class MediaDb {

    private DatabaseHandler dbHelper;

    public MediaDb(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    public void delete_image(NotesTable tableinfo) {
        String image_path = tableinfo.image;
        String emptypath = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(NotesTable.KEY_IMAGE, emptypath);
        db.update(NotesTable.TABLE_NOTES, val, NotesTable.KEY_IMAGE + "='" + image_path + "'"
                , null);
        db.close(); // Closing database connection*/
    }

    public void delete_video(NotesTable tableinfo) {
        String video_path = tableinfo.video;
        String emptypath = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(NotesTable.KEY_VIDEO, emptypath);
        db.update(NotesTable.TABLE_NOTES, val, NotesTable.KEY_VIDEO + "='" + video_path + "'", null);
        db.close(); // Closing database connection*/
    }

    public void delete_audio(NotesTable tableinfo) {
        String audio_path = tableinfo.audio;
        String emptypath = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(NotesTable.KEY_AUDIO, emptypath);
        db.update(NotesTable.TABLE_NOTES, val, NotesTable.KEY_AUDIO + "='" + audio_path + "'", null);
        db.close(); // Closing database connection*/
    }

    public void Update_Image(NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(NotesTable.KEY_IMAGE, tableinfo.image);
        db.update(NotesTable.TABLE_NOTES, val,
                NotesTable.KEY_TOPIC_NAME + "='" + tableinfo.topic_name + "'"
                        + " AND " + NotesTable.KEY_CATEGORY_NAME + "='" + tableinfo.category_name + "'"
                        + " AND " + NotesTable.KEY_TERM_NAME + "='" + tableinfo.term_name + "' " + " AND  " + " ( " +
                        NotesTable.KEY_IMAGE + " = '" + tableinfo.old_img_path + "'" + " OR " +
                        NotesTable.KEY_IMAGE + " = ' ' " + " OR " + NotesTable.KEY_IMAGE + " IS NULL " + ")", null);
        db.close(); // Closing database connection*/
    }

    public void Update_Video(NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(NotesTable.KEY_VIDEO, tableinfo.video);
        db.update(NotesTable.TABLE_NOTES, val,
                NotesTable.KEY_TOPIC_NAME + "='" + tableinfo.topic_name + "'"
                        + " AND " + NotesTable.KEY_CATEGORY_NAME + "='" + tableinfo.category_name + "'"
                        + " AND " + NotesTable.KEY_TERM_NAME + "='" + tableinfo.term_name + "' " + " AND  " + " ( " +
                        NotesTable.KEY_VIDEO + " = '" + tableinfo.old_video_path + "'" + " OR " +
                        NotesTable.KEY_VIDEO + " = ' ' " + " OR " + NotesTable.KEY_VIDEO + " IS NULL " + ")", null);
        db.close(); // Closing database connection*/
    }

    public void Update_Audio(NotesTable tableinfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(NotesTable.KEY_AUDIO, tableinfo.audio);
        db.update(NotesTable.TABLE_NOTES, val,
                NotesTable.KEY_TOPIC_NAME + "='" + tableinfo.topic_name + "'"
                        + " AND " + NotesTable.KEY_CATEGORY_NAME + "='" + tableinfo.category_name + "'"
                        + " AND " + NotesTable.KEY_TERM_NAME + "='" + tableinfo.term_name + "' " + " AND  " + " ( " +
                        NotesTable.KEY_AUDIO + " = '" + tableinfo.old_audio_path + "'" + " OR " +
                        NotesTable.KEY_AUDIO + " = ' ' " + " OR " + NotesTable.KEY_AUDIO + " IS NULL " + ")", null);
        db.close(); // Closing database connection*/

    }
}
