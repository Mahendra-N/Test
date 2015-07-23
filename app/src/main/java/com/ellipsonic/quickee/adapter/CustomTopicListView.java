package com.ellipsonic.quickee.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ellipsonic.quickee.Category;
import com.ellipsonic.quickee.R;
import com.ellipsonic.quickee.model.TopicPopulating;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ellip sonic on 09-06-2015.
 */
public class CustomTopicListView extends BaseAdapter {

 Context mContext;
 LayoutInflater inflater;
 private List<TopicPopulating> worldpopulationlist = null;
    private ArrayList<TopicPopulating> arraylist;
    public CustomTopicListView(Activity context, List<TopicPopulating> worldpopulationlist) {
       // super(context, R.layout.custom_topic_adapter_view, Topic);
        // TODO Auto-generated constructor stub

        this.mContext= context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<TopicPopulating>();
        this.arraylist.addAll(worldpopulationlist);
    }
    public class ViewHolder {
        TextView topic;
        TextView category;
          }

    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public TopicPopulating getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.custom_topic_adapter_view, null);
            // Locate the TextViews in listview_item.xml
            holder.topic = (TextView) view.findViewById(R.id.topic);
            holder.category = (TextView) view.findViewById(R.id.cat);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.topic.setText(worldpopulationlist.get(position).getTopic());
        holder.category.setText(worldpopulationlist.get(position).getCategory());

        view.setOnClickListener(new AdapterView.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext,Category.class);
                intent.putExtra("selectedTopic",(worldpopulationlist.get(position).getTopic()));
                mContext.startActivity(intent);
            }
        });
           return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        worldpopulationlist.clear();
        if (charText.length() == 0) {
            worldpopulationlist.addAll(arraylist);
        }
        else
        {
            for (TopicPopulating wp : arraylist)
            {
                if (wp.getTopic().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}
