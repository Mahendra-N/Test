package com.ellipsonic.quickee;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DropboxFragment extends Fragment {
	
	public DropboxFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dropbox, container, false);
        setHasOptionsMenu(true);
        return rootView;
    }


}
