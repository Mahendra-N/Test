package com.ellipsonic.quickee;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.ellipsonic.database.CsvFiles;
import com.ellipsonic.database.ExternalFolders;
import com.ellipsonic.database.NotesTable;
import com.ellipsonic.database.TermDb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DropboxFragment extends Fragment {
    private final static String FILE_DIR = "/";
   	public DropboxFragment(){}
    public Context context = null;

    final static private String APP_KEY = "63mmu1d0e4cmjqc";
    final static private String APP_SECRET = "pd8v94e5cvknfmg";
    // In the class declaration section:
    private DropboxAPI<AndroidAuthSession> mDBApi;
    Button login,export, import_from_dropbox,logout;
    Boolean flag =true;
    String root = Environment.getExternalStorageDirectory().toString();
    private ProgressDialog  mProgressDialog;
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
           Bundle savedInstanceState) {
        this.context=container.getContext();
        setHasOptionsMenu(false);
        final View rootView = inflater.inflate(R.layout.fragment_dropbox, container, false);

        login= (Button) rootView.findViewById(R.id.link);
        export= (Button) rootView.findViewById(R.id.export);
        import_from_dropbox= (Button) rootView.findViewById(R.id.import_from);
        logout= (Button) rootView.findViewById(R.id.unlink);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // And later in some initialization function:
                AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
                AndroidAuthSession session = new AndroidAuthSession(appKeys);
                mDBApi = new DropboxAPI<AndroidAuthSession>(session);
                mDBApi.getSession().startOAuth2Authentication(context);
            }
        });

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (mDBApi != null) {
                    flag=true;
                    mDBApi.getSession().unlink();
                    mDBApi=null;
                } else {
                    Toast.makeText(context, "You have not linked to unlink", Toast.LENGTH_SHORT).show();
                }
            }
        });

        export.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                if (mDBApi != null) {
                   if (mDBApi.getSession().authenticationSuccessful()) {
                          StartExporting();
                    }else{
                        AlertWindow("export");
                    }
                }else{
                    AlertWindow("export");
                }
            }
        });

        import_from_dropbox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (mDBApi != null) {
                    if (mDBApi.getSession().authenticationSuccessful()) {
                        StartImporting();
                    }else{
                        AlertWindow("import");
                    }
                }else{
                    AlertWindow("import");
                }
            }
        });
        return rootView;
    }
    public void StartExporting() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");

        alertDialog.setMessage("Are you sure you want to Export, Existing data in dropbox will be replaced..!");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage(" Uploading files please wait..!");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                UploadFileToDropbox upload = new UploadFileToDropbox(context, mDBApi, FILE_DIR, mProgressDialog );
                upload.execute();

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (mDBApi != null) {
                    flag=true;
                    mDBApi.getSession().unlink();
                    mDBApi=null;
                }
                dialog.dismiss();

            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
    public void StartImporting() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");

        alertDialog.setMessage("Are you sure you want to import, Existing data will be lost..!");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage("importing files please wait..!");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                ExternalFolders folder=new ExternalFolders();
                folder.Createfolder(context);
                ImportFromDropbox import_from_dp = new ImportFromDropbox(context, mDBApi,  mProgressDialog );
                import_from_dp.execute();

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            if (mDBApi != null) {
                    flag=true;
                    mDBApi.getSession().unlink();
                    mDBApi=null;
                }
                dialog.dismiss();

            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
    public void AlertWindow(String val) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");

        alertDialog.setMessage("You need to link to "+val );

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    public void Alert_Window() {
        flag=false;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");

        alertDialog.setMessage("Login successfull");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    public void onResume() {
        super.onResume();
        if (mDBApi != null) {
            if (mDBApi.getSession().authenticationSuccessful()) {
                try {
                    // Required to complete auth, sets the access token on the session
                    mDBApi.getSession().finishAuthentication();
                    String accessToken = mDBApi.getSession().getOAuth2AccessToken();
                    if(flag==true){
                        Alert_Window();
                    }
                } catch (IllegalStateException e) {
                    Log.i("DbAuthLog", "Error authenticating", e);
                }
            }
        }
    }

}
class UploadFileToDropbox extends AsyncTask<Void, Void, Boolean> {


    private DropboxAPI<?> dropbox;
    private String path;
    private Context context;
    ProgressDialog mProgressDialog;
    String root = Environment.getExternalStorageDirectory().toString();
    public UploadFileToDropbox(Context context, DropboxAPI<?> dropbox,
                               String path, ProgressDialog mProgressDialog) {
        this.context = context.getApplicationContext();
        this.dropbox = dropbox;
        this.path = path;
        this.mProgressDialog=mProgressDialog;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            dropbox.delete(path + "quickee.zip");
        } catch (DropboxException e) {
            e.printStackTrace();
        }
        try {
            CsvFiles file = new CsvFiles();
            file.CreateFile(context);
            Zip t1=new Zip();
            t1.setName("Zipping Folder");
            t1.start();
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            File myfile = new File(root + "/quickee.zip/");
            FileInputStream fileInputStream = new FileInputStream(myfile);
                dropbox.putFile(path + "quickee.zip", fileInputStream,
                        myfile.length(), null, null);


            return true;
        } catch (IOException e) {
            e.printStackTrace();

        } catch (DropboxException e) {
            e.printStackTrace();

        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
             mProgressDialog.dismiss();
            Toast.makeText(context, "File uploaded successfully!", Toast.LENGTH_LONG).show();

        } else {
            mProgressDialog.dismiss();
            Toast.makeText(context, "Failed to upload file..!", Toast.LENGTH_LONG).show();
        }
    }

}
class ImportFromDropbox extends AsyncTask<Void, Void, Boolean> {


    private DropboxAPI<?> dropbox;
    private Context context;
    ProgressDialog mProgressDialog;
    String root = Environment.getExternalStorageDirectory().toString();
    public ImportFromDropbox(Context context, DropboxAPI<?> dropbox,
                             ProgressDialog mProgressDialog) {
        this.context = context.getApplicationContext();
        this.dropbox = dropbox;
        this.mProgressDialog=mProgressDialog;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {

            UnzipThread unzipthread = new UnzipThread(dropbox);
            unzipthread.start();
            unzipthread.join();
            UnZip checkBoolean= new UnZip();
            if(checkBoolean.unzip_flag.equals(true)){
                return  true;
            }else{
                return  false;
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            context.deleteDatabase("Quickee.db");
            String dir = Environment.getExternalStorageDirectory()+"/Quickee/Database";
            File dirObj = new File(dir);
            File[] files = dirObj.listFiles();
            BufferedReader br = null;
            String line = "";
            String cvsSplitBy = ",(?=(?:(?:[^\\\"]*+\\\"){2})*+[^\\\"]*+$)";
            TermDb   term_Db=new TermDb(context);
            NotesTable tableinfo = new NotesTable();
            for (int i = 0; i < files.length; i++) {

                try {

                    br = new BufferedReader(new FileReader(files[i]));
                    while ((line = br.readLine()) != null) {
                        // use comma as separator
                        String[] Topic = line.split(cvsSplitBy,-1);
                        tableinfo.topic_name=(Topic[1].equals("null")) ? null: Topic[1];
                        tableinfo.category_name=(Topic[2].equals("null")) ? null : Topic[2];
                        tableinfo.term_name=(Topic[3].equals("null")) ? null : Topic[3];
                        String description=(Topic[4].equals("null")) ? null : Topic[4];
                        if(description!=null){
                         tableinfo.description= description.replaceAll("\"","");
                        }else{
                            tableinfo.description=description;
                        }
                        tableinfo.image=(Topic[5].equals("null")) ? null : Topic[5];
                        tableinfo.audio=(Topic[6].equals("null")) ? null : Topic[6];
                        tableinfo.video=(Topic[7].equals("null")) ? null : Topic[7];
                        term_Db.insert_term(tableinfo);

                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                mProgressDialog.dismiss();
            }

           Toast.makeText(context, "File downloaded successfully!", Toast.LENGTH_LONG).show();

        } else {
            mProgressDialog.dismiss();
            Toast.makeText(context, "Failed to download file..!", Toast.LENGTH_LONG).show();
        }
    }

}

