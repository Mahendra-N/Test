package com.ellipsonic.quickee;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class GoogleDriveFragment extends Fragment {
	
	public GoogleDriveFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_google_drive, container, false);

        return rootView;
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items

        menu.findItem(R.id.edit_topic).setVisible(false);
        menu.findItem(R.id.add_topic).setVisible(false);
         super.onPrepareOptionsMenu(menu);
    }



}
