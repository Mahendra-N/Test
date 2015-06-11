package com.ellipsonic.quickee;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ellip sonic on 09-06-2015.
 */

public class CustomTermListView extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<TermPopulating> worldpopulationlist = null;
    private ArrayList<TermPopulating> arraylist;
    String SelectedTopic;
    String SelectedCategory;
    public CustomTermListView(Activity context, List<TermPopulating> worldpopulationlist,String selectedTopic,String selectedCategory) {
        // super(context, R.layout.custom_topic_adapter_view, Topic);
        // TODO Auto-generated constructor stub
        this.SelectedTopic=selectedTopic;
        this.SelectedCategory=selectedCategory;
        this.mContext= context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<TermPopulating>();
        this.arraylist.addAll(worldpopulationlist);
    }
    public class ViewHolder {
        TextView term;
        TextView desc;
    }

    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public TermPopulating getItem(int position) {
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
            view = inflater.inflate(R.layout.custom_term_adapter_view, null);
            // Locate the TextViews in listview_item.xml
            holder.term = (TextView) view.findViewById(R.id.term);
            holder.desc = (TextView) view.findViewById(R.id.desc);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.term.setText(worldpopulationlist.get(position).getTerm());
        holder.desc.setText(worldpopulationlist.get(position).getDesc());

        view.setOnClickListener(new AdapterView.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext,Description.class);
                intent.putExtra("selectedTerm",(worldpopulationlist.get(position).getTerm()));
                intent.putExtra("selectedTopic",SelectedTopic);
                intent.putExtra("selectedCategory",SelectedCategory);
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
            for (TermPopulating wp : arraylist)
            {
                if (wp.getTerm().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

/*    private final Activity context;
    private final ArrayList<String> term;
    private final ArrayList<String> desc;

    public CustomTermListView(Activity context, ArrayList<String> term, ArrayList<String> desc) {
        super(context, R.layout.custom_term_adapter_view, term);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.term=term;
        this.desc=desc;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_term_adapter_view, null,true);

        TextView Term = (TextView) rowView.findViewById(R.id.term);
        TextView description = (TextView) rowView.findViewById(R.id.desc);

        Term.setText(term.get(position));
        description.setText(desc.get(position));
        return rowView;

    };*/
}