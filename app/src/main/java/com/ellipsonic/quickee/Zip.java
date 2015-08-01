package com.ellipsonic.quickee;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Ellip sonic on 18-07-2015.
 */
public class Zip extends Thread{

    public void run(){
        String root = Environment.getExternalStorageDirectory().toString();
        File myfile = new File(root +"/quickee.zip/");
        if (myfile.exists()) {
            myfile.delete();
        }  if(!myfile.exists()) {
            try {
                myfile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       String dir = Environment.getExternalStorageDirectory()+"/Quickee/";

        String zipFileName= String.valueOf(myfile);
        try {
            zipDir(zipFileName, dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipDir(String zipFileName, String dir) throws Exception {

        File dirObj = new File(dir);

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        System.out.println("Creating : " + zipFileName);
        addDir(dirObj, out);
        out.close();
    }

    static void addDir(File dirObj, ZipOutputStream out) throws IOException {
        File[] files = dirObj.listFiles();
        byte[] tmpBuf = new byte[1024];

        for (int i = 0; i < files.length; i++) {

            if (files[i].isDirectory()) {

                addDir(files[i], out);
                continue;
            }
            FileInputStream in = new FileInputStream(files[i].getAbsolutePath());
            System.out.println(" Adding: " + files[i].getAbsolutePath());
          //  out.putNextEntry(new ZipEntry(files[i].getAbsolutePath()));
            String pathAfterOmittingtheRootFolder=files[i].getAbsolutePath().replaceFirst(String.valueOf(Environment.getExternalStorageDirectory()), "");
            out.putNextEntry(new ZipEntry(pathAfterOmittingtheRootFolder));
            int len;
            while ((len = in.read(tmpBuf)) > 0) {
                out.write(tmpBuf, 0, len);
            }
            out.closeEntry();
            in.close();
        }

    }


  }

