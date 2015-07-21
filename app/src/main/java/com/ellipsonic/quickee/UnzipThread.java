package com.ellipsonic.quickee;

import android.os.Environment;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Ellip sonic on 20-07-2015.
 */
public class UnzipThread extends  Thread {
    private DropboxAPI<?> dropbox;
    Boolean flag;
    public UnzipThread( DropboxAPI<?> dropbox) {
        this.dropbox = dropbox;
    }
    public void run(){

         String root = Environment.getExternalStorageDirectory().toString();
        File myfile = new File(root + "/quickee.zip");
         if (myfile.exists())
            myfile.delete();
        if(!myfile.exists()){
            try {
                myfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     try {
            FileOutputStream outputStream =  new FileOutputStream(myfile);
            DropboxAPI.DropboxFileInfo info = dropbox.getFile("/quickee.zip", null, outputStream, null);
        }
        catch (DropboxException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File Zipfile =myfile;
        File root_folder = new File(Environment.getExternalStorageDirectory().toString());
        UnZip d = new UnZip(Zipfile.toString(), root_folder.toString());
        d.unzip();

    }

}
