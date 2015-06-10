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

public class CustomTermListView extends ArrayAdapter<String> {
    private final Activity context;
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

    };
}