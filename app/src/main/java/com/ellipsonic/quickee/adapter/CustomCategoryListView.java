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

import com.ellipsonic.quickee.model.CategoryPopulating;
import com.ellipsonic.quickee.R;
import com.ellipsonic.quickee.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ellip sonic on 09-06-2015.
 */
public class CustomCategoryListView extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<CategoryPopulating> worldpopulationlist = null;
    private ArrayList<CategoryPopulating> arraylist;
    String SelectedTopic;
    public CustomCategoryListView(Activity context, List<CategoryPopulating> worldpopulationlist,String selectedTopic) {
        // super(context, R.layout.custom_topic_adapter_view, Topic);
        // TODO Auto-generated constructor stub
        this.SelectedTopic=selectedTopic;
        this.mContext= context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<CategoryPopulating>();
        this.arraylist.addAll(worldpopulationlist);
    }
    public class ViewHolder {
        TextView category;
        TextView term;
    }

    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    @Override
    public CategoryPopulating getItem(int position) {
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
            view = inflater.inflate(R.layout.custom_category_adapter_view, null);
            // Locate the TextViews in listview_item.xml
            holder.category = (TextView) view.findViewById(R.id.category);
            holder.term = (TextView) view.findViewById(R.id.term);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.category.setText(worldpopulationlist.get(position).getCategory());
        holder.term.setText(worldpopulationlist.get(position).getterm());

        view.setOnClickListener(new AdapterView.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext,Term.class);
                intent.putExtra("selectedCategory",(worldpopulationlist.get(position).getCategory()));
                intent.putExtra("selectedTopic",SelectedTopic);
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
            for (CategoryPopulating wp : arraylist)
            {
                if (wp.getCategory().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}