package com.ellipsonic.database;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.widget.ProgressBar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ellip sonic on 03-07-2015.
 */
public class CsvFiles  {
    public TopicDb topic_Db = null;
    public ArrayList<String> topicList = null;
    public TopicDb topic_db = null;
    public ArrayList<ArrayList<String>> topic_list = null;
    Context context;
    ProgressBar progressBar;
    int i;

    public void CreateFile(Context Context) {

       context = Context;
        File database_folder = new File(Environment.getExternalStorageDirectory() + "/Quickee/Database");
        if (database_folder.exists()) {
            topic_Db = new TopicDb(this.context);
            topicList = topic_Db.getTopicList();
            topicList.removeAll(Collections.singleton(null));
            if (topicList != null) {

                for (i = 0; i < topicList.size(); i++) {
                    String[] parts = topicList.get(i).split("\n");
                    String topicname = parts[0];
                    CreateCsvFile(topicname);
                }
            }
        } else {
            FolderDoesNotExists();
        }

    }


    public void FolderDoesNotExists() {
        AlertDialog.Builder alertDialog;
        alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("Quickee");
        // Setting Dialog Message
        alertDialog.setMessage("Unable to Export");

        // Setting Positive "Yes" Button
        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void CreateCsvFile(String topic_name) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myfile = new File(root + "/Quickee/Database/" + topic_name + ".csv");
        if (myfile.exists()) {
            myfile.delete();
              }  if(!myfile.exists()) {
            try {
                myfile.createNewFile();
                LoadData(topic_name,myfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void LoadData(String topic_name, File myfile) {
        topic_db = new TopicDb(this.context);
        topic_list = topic_Db.CsvData(topic_name);
      //  topicList.removeAll(Collections.singleton(null));
           if (topic_list != null) {
               String rowdata = null;
               for (int i = 0; i < topic_list.size(); i++) {
                   rowdata = String.valueOf(topic_list.get(i));
                   rowdata=rowdata.replace("[", "");
                   rowdata=rowdata.replace("]", "");
                   rowdata=rowdata.replaceAll(", ", ",");
                   rowdata= rowdata.replaceAll(",\n,", "\n");
                   rowdata=rowdata.replaceAll("--,", "\"");
                   rowdata=rowdata.replaceAll(",-", "\"");
                  rowdata=rowdata.substring(0,rowdata.length()-2);
                   // now pass the String wherever You want
                   String text = rowdata;
                   BufferedWriter output = null;
                   try {
                       File file = new File(String.valueOf(myfile));
                       output = new BufferedWriter(new FileWriter(file));
                       output.write(text);
                   } catch (IOException e) {
                       e.printStackTrace();
                   } finally {
                       if (output != null) try {
                           output.close();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
               }

           }
    }

}