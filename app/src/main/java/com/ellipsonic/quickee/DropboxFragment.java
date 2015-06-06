package com.ellipsonic.quickee;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DropboxFragment extends Fragment {
	
	public DropboxFragment(){}
	

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        View rootView = inflater.inflate(R.layout.fragment_dropbox, container, false);

               return rootView;
    }



}
