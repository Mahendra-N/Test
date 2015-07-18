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
import android.widget.ProgressBar;
import android.widget.Toast;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.ellipsonic.database.CsvFiles;
import com.ellipsonic.database.TopicDb;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class DropboxFragment extends Fragment {
    private final static String FILE_DIR = "/Database/";
   	public DropboxFragment(){}
    public Context context = null;
    ProgressBar progressBar;
    final static private String APP_KEY = "63mmu1d0e4cmjqc";
    final static private String APP_SECRET = "pd8v94e5cvknfmg";
    // In the class declaration section:
    private DropboxAPI<AndroidAuthSession> mDBApi;
    Button login;
    Button export;
    Button import_from_dropbox;
    Button logout;
    ProgressDialog  mProgressDialog;
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
                        CsvFiles file = new CsvFiles();
                        file.CreateFile(context);
                        mProgressDialog = new ProgressDialog(getActivity());
                        mProgressDialog.setMessage("Uploading files please wait..!");
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        mProgressDialog.setCancelable(false);
                        mProgressDialog.show();
                        UploadFileToDropbox upload = new UploadFileToDropbox(context, mDBApi, FILE_DIR, mProgressDialog );
                        upload.execute();
                    }else{
                        AlertWindow();
                    }
                }else{
                    AlertWindow();
                }
            }
        });
        import_from_dropbox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (mDBApi != null) {
                    if (mDBApi.getSession().authenticationSuccessful()) {

                    }else{
                        AlertWindow();
                    }
                }else{
                    AlertWindow();
                }
            }
        });
        return rootView;
    }
    public void AlertWindow() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Title
        alertDialog.setTitle("Quickee");

        alertDialog.setMessage("You need to login to export");

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
                    Alert_Window();
                    String accessToken = mDBApi.getSession().getOAuth2AccessToken();
                } catch (IllegalStateException e) {
                    Log.i("DbAuthLog", "Error authenticating", e);
                }
            }
        }
    }

}
class UploadFileToDropbox extends AsyncTask<Void, Void, Boolean> {

    String topicname;
    private DropboxAPI<?> dropbox;
    private String path;
    private Context context;
    ProgressDialog mProgressDialog;
    public TopicDb topic_Db = null;
    public ArrayList<String> topicList = null;
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
            topic_Db = new TopicDb(this.context);
            topicList = topic_Db.getTopicList();
            topicList.removeAll(Collections.singleton(null));
            if (topicList != null) {
                for (int i = 0; i < topicList.size(); i++) {
                    String[] parts = topicList.get(i).split("\n");
                     topicname = parts[0];

            String root = Environment.getExternalStorageDirectory().toString();
            File myfile = new File(root + "/Quickee/Database/" + topicname + ".csv");
            if (myfile.exists()) {

               FileInputStream fileInputStream = new FileInputStream(myfile);
                dropbox.putFile(path +  topicname + ".csv", fileInputStream,
                        myfile.length(), null, null);

                 }
            }
        }
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
            Toast.makeText(context, "Failed to upload file", Toast.LENGTH_LONG).show();
        }
    }
}