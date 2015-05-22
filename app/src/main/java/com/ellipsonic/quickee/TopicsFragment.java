package com.ellipsonic.quickee;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ellipsonic.database.TopicDb;

import java.util.ArrayList;
import java.util.Collections;

public class TopicsFragment extends Fragment {
	
	public TopicsFragment(){}
    View rootView;
    public TopicDb topic_Db = null;
    public ArrayList<String> topicList = null;
    public Context context = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_topics, container, false);
        this.context = container.getContext();
        TopicListView(this.context);
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
        TopicListView(this.context);
    }

    public void TopicListView(Context context) {
        topic_Db = new TopicDb(this.context);
        topicList = topic_Db.getTopicList();
        topicList.removeAll(Collections.singleton(null));
        if (topicList != null) {
            ArrayAdapter<String> myAdapter = new
                    ArrayAdapter<String>(this.getActivity(),
                    android.R.layout.simple_list_item_1,
                    topicList);
            ListView myList = (ListView) rootView.findViewById(R.id.topic_listView);
            myList.setAdapter(myAdapter);
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
        }else{
            Log.d("message", "nothing is there in database");
                }
    }
}
