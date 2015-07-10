package com.ellipsonic.quickee;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ellipsonic.database.CsvFiles;

public class DropboxFragment extends Fragment {
	
	public DropboxFragment(){}
    public Context context = null;
    ProgressBar progressBar;
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
            Bundle savedInstanceState) {
        this.context=container.getContext();
        setHasOptionsMenu(false);
        final View rootView = inflater.inflate(R.layout.fragment_dropbox, container, false);
         progressBar =(ProgressBar)rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        Button createfile= (Button) rootView.findViewById(R.id.createFile);
        createfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CsvFiles file =new CsvFiles();
                file.CreateFile(context);

            }
        });
               return rootView;
    }



}
