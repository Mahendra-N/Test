package com.ellipsonic.quickee;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author jon
 */
public class UnZip {
    private String _zipFile;
    private String _location;
    static  Boolean unzip_flag=false;
    public UnZip(String zipFile, String location) {
        _zipFile = zipFile;
        _location = location;

        _dirChecker("");
    }
    public UnZip() {

    }
    public void unzip() {
        try  {
            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.v("Decompress", "Unzipping " + ze.getName());

                if(ze.isDirectory()) {
                    _dirChecker(ze.getName());
                } else {
                    FileOutputStream fout = new FileOutputStream(_location + ze.getName());
                    BufferedOutputStream bufout = new BufferedOutputStream(fout);
                    byte[] buffer = new byte[1024];
                    int read = 0;
                    while ((read = zin.read(buffer)) != -1) {
                        bufout.write(buffer, 0, read);
                    }

                    zin.closeEntry();
                    bufout.close();
                    fout.close();
                }

            }
            zin.close();

            unzip_flag=true;

        } catch(Exception e) {
            Log.e("Decompress", "unzip", e);
            unzip_flag=false;
        }

    }

    private void _dirChecker(String dir) {
        File f = new File(_location + dir);

        if(!f.isDirectory()) {
            f.mkdirs();
        }
    }

} 