package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeFragment extends Fragment {
	
	public HomeFragment(){}
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        return rootView;
    }
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        String[]  myStringArray={"Air Force","Plane","Auto","Military"};

        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_list_item_1,
                myStringArray);
        ListView   myList=(ListView)  rootView.findViewById(R.id.topic_listView);
        myList.setAdapter(myAdapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent(getActivity(), Category.class);
                startActivity(intent);

            }        });
    }
}
