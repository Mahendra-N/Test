package com.ellipsonic.quickee;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.ellipsonic.database.ExternalFolders;
import com.ellipsonic.database.FirstTimeDataInsertion;
import com.ellipsonic.database.TopicDb;
import com.ellipsonic.quickee.model.TopicPopulating;
import com.ellipsonic.quickee.adapter.CustomTopicListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class TopicsFragment extends Fragment {
	
	public TopicsFragment(){}
    View rootView;
    public TopicDb topic_Db = null;
    public ArrayList<String> topicList = null;
   // ArrayAdapter<String> myAdapter;
     CustomTopicListView adapter;
    public Context context = null;
    EditText myFilter;
    ExternalFolders folder=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_topics, container, false);
        this.context = container.getContext();

        myFilter = (EditText) rootView.findViewById(R.id.search);
        folder=new ExternalFolders();
        FirstTimeInstalliation();
        TopicListView(this.context);

        myFilter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        return rootView;

    }

    @Override
    public void onPause() {
        super.onPause();
        topic_Db = null;
        topicList = null;

    }

    @Override
    public void onResume() {
        super.onResume();
        myFilter.clearFocus();
        TopicListView(this.context);
    }

    public void TopicListView(Context context) {
        topic_Db = new TopicDb(this.context);
        topicList = topic_Db.getTopicList();
        topicList.removeAll(Collections.singleton(null));
        ArrayList<TopicPopulating> topic = new ArrayList<TopicPopulating>();
        ArrayList<String> no_cat= new ArrayList<String>();
        if (topicList != null) {
            //myAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.customlist, R.id.Itemname,topicList);


                for (int i=0;i<topicList.size();i++){
                    String[] parts = topicList.get(i).split("\n");
                 //   topic.add(parts[0]); // topic
                  //  no_cat.add(parts[1].concat("  category")); //noofcat
                String topicname =parts[0];
                String no_of_cat =(parts[1].concat("  category"));
                TopicPopulating wp = new TopicPopulating(topicname, no_of_cat);
                    topic.add(wp);
                }
            Context context1=getActivity();

            adapter=new CustomTopicListView((android.app.Activity) context1, topic);
            ListView myList = (ListView) rootView.findViewById(R.id.topic_listView);
            myList.setAdapter(adapter);
            myList.setTextFilterEnabled(true);

            myFilter.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Call back the Adapter with current character to Filter
                    //adapter.getFilter().filter(s.toString());
                    String text = myFilter.getText().toString().toLowerCase(Locale.getDefault());
                    adapter.filter(text);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }else{
            Log.d("message", "nothing is there in database");
                }
    }
public void FirstTimeInstalliation(){
    final String PREFS_NAME = "MyPrefsFile";

    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);

    if (settings.getBoolean("my_first_time", true)) {
        //the app is being launched for first time, do something
        Log.d("Comments", "First time");
        folder.Createfolder(this.context);
        // first time task
        FirstTimeDataInsertion FirstTimeData = new FirstTimeDataInsertion();
        FirstTimeData.InstallationTopic(this.context);
        FirstTimeData.InstallationCategory(this.context);
        FirstTimeData.InstallationTermDefination(this.context);
        // record the fact that the app has been started at least once
        settings.edit().putBoolean("my_first_time", false).commit();
    }
}
}
