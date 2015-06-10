package com.ellipsonic.quickee;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.ellipsonic.database.ExternalFolders;
import com.ellipsonic.database.TopicDb;

import java.util.ArrayList;
import java.util.Collections;

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
        TopicListView(this.context);
        folder=new ExternalFolders();
        folder.Createfolder(this.context);
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
        ArrayList<String> topic = new ArrayList<String>();
        ArrayList<String> no_cat= new ArrayList<String>();
        if (topicList != null) {
            //myAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.customlist, R.id.Itemname,topicList);


                for (int i=0;i<topicList.size();i++){
                    String[] parts = topicList.get(i).split("\n");
                    topic.add(parts[0]); // topic
                    no_cat.add(parts[1].concat("  category")); //noofcat
                }
            Context context1=getActivity();
            adapter=new CustomTopicListView((android.app.Activity) context1, topic, no_cat);
            ListView myList = (ListView) rootView.findViewById(R.id.topic_listView);
            myList.setAdapter( adapter);
            myList.setTextFilterEnabled(true);
            myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    String clickedItem  = String.valueOf(parent.getItemAtPosition(position));
                    Intent intent = new Intent(getActivity(),Category.class);
                    final int result = 1;
                    intent.putExtra("selectedTopic",clickedItem);
                    startActivityForResult(intent, result);
                }
            });


            myFilter.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Call back the Adapter with current character to Filter
                    adapter.getFilter().filter(s.toString());
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

}
