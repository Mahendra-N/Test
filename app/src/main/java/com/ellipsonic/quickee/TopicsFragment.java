package com.ellipsonic.quickee;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ellipsonic.database.TopicDb;

import java.util.ArrayList;

public class TopicsFragment extends Fragment {
	
	public TopicsFragment(){}
    View rootView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_topics, container, false);

        return rootView;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        TopicListView();
        super.onActivityCreated(savedInstanceState);


    }


    public void TopicListView(){

        TopicDb topicDb =new TopicDb(getActivity());

        ArrayList<String> topicList =  topicDb.getTopicList();
        // String[]  myStringArray={"Air Force","Plane","Auto","Military","Sachin","BMW","AUDI","KING","Lemon","sweet"};

        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1,
                topicList);
        ListView   myList=(ListView)  rootView.findViewById(R.id.topic_listView);
        myList.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent(getActivity(), Category.class);
                startActivity(intent);

            }        });
    }

}
