package com.ellipsonic.database;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Ellip sonic on 25-05-2015.
 */
public class ExternalFolders {
    Context context;
    public void Createfolder(Context Context) {
        context=Context;
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

        if(isSDPresent)
        {
            File root_folder = new File(Environment.getExternalStorageDirectory()+"/Quickee/");
            File img_folder = new File(Environment.getExternalStorageDirectory()+"/Quickee/Images");
            File  audio_folder= new File(Environment.getExternalStorageDirectory()+"/Quickee/Audio");
            File  video_folder= new File(Environment.getExternalStorageDirectory()+"/Quickee/Video");
            boolean success = true;
            if (!root_folder.exists()) {
                success= root_folder.mkdir();
                video_folder.mkdirs();
                img_folder.mkdirs();
                audio_folder.mkdirs();

            }
            if (success) {
                Log.i("folder","Folders created");
            } else {
                Log.i("folder","Failed to created folder");
                // Do something else on failure
            }
        }
        else
        {
            // Sorry
            SdCardNotFound();
        }

    }

    public void SdCardNotFound(){
        AlertDialog.Builder alertDialog;
        alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("Quickee");
        // Setting Dialog Message
        alertDialog.setMessage("Failed to create Folders required for  Application..!"+"SD CARD Required");

        // Setting Positive "Yes" Button
        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        // Showing Alert Message
        alertDialog.show();
    }



}
