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
public class CustomCategoryListView extends ArrayAdapter<String> {
    private final Activity context;
    private final ArrayList<String> cat;
    private final ArrayList<String> term;

    public CustomCategoryListView(Activity context, ArrayList<String> cat, ArrayList<String> term) {
        super(context, R.layout.custom_category_adapter_view, cat);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.cat=cat;
        this.term=term;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.custom_category_adapter_view, null,true);

        TextView topic = (TextView) rowView.findViewById(R.id.category);
        TextView no_of_cat = (TextView) rowView.findViewById(R.id.term);

        topic.setText(cat.get(position));
        no_of_cat.setText(term.get(position));
        return rowView;

    };
}