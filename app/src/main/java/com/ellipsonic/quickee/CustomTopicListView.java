package com.ellipsonic.quickee;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ellip sonic on 09-06-2015.
 */
public class CustomTopicListView extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> Topic;
    private final ArrayList<String> Cat;

    public CustomTopicListView(Activity context, ArrayList<String> Topic, ArrayList<String> Cat) {
        super(context, R.layout.custom_topic_adapter_view, Topic);
        // TODO Auto-generated constructor stub

        this.context= context;
        this.Topic=Topic;
        this.Cat=Cat;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_topic_adapter_view, null,true);

        TextView topic = (TextView) rowView.findViewById(R.id.topic);
        TextView no_of_cat = (TextView) rowView.findViewById(R.id.cat);

        topic.setText(Topic.get(position));
        no_of_cat.setText(Cat.get(position));
        return rowView;

    };
}
