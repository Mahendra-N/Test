package com.ellipsonic.database;

/**
 * Created by Ellip sonic on 16-05-2015.
 */
public class NotesTable {


    // Notes table name
    public static final String TABLE_NOTES = "NOTES";

    // NOTES Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_TOPIC_NAME = "topic_name";
    public static final String KEY_CATEGORY_NAME = "category_name";
    public static final String KEY_TERM_NAME = "term_name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_AUDIO = "audio";
    public static final String KEY_VIDEO = "video";
    public static  String KEY_TOPIC_CAT="topic_cat";


    public int id;
    public String topic_name;
    public String category_name;
    public String term_name;
    public String description;
    public String image;
    public String audio;
    public String video;
    public String old_topic_name;
    public String old_cat_name;
    public String old_term_name;
    public String old_img_path;
    public String old_video_path;
    public String old_audio_path;
    public  String input_term_name;
    public String add_des;
}
