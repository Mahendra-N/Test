package com.ellipsonic.database;

import android.content.Context;

/**
 * Created by Ellip sonic on 20-06-2015.
 */
public class FirstTimeDataInsertion {
    String[]  topicnames= {"Sample Topic 1", "Sample Topic 2"};
    String[]  catnames= {"Category 1", "Category 2" ,"Category 3"};
    String[] termname={"Term 1","Term 2","Term 3"};
    String[] description={"You can add definaton, attach picture and take picture,attach video and record video and attach audiofile.",
            "The human activity that surrounds aircraft is called aviation. Crewed aircraft are flown by an onboard pilot, but unmanned aerial vehicles may be remotely controlled or self-controlled by onboard computers. Aircraft may be classified by different criteria, such as lift type, aircraft propulsion, usage and others",
            "You can add definaton, attach picture and take picture,attach video and record video and attach audiofile."};
     public void InstallationTopic(Context context){
        NotesTable tableinfo = new NotesTable();
        TopicDb topicDb =new TopicDb(context);

        for(int i=0;i<topicnames.length;i++) {
            tableinfo.topic_name =topicnames[i];
                    topicDb.insert_topic(tableinfo);
        }
    }
    public void InstallationCategory(Context context){
        CategoryDb categoryDb = new CategoryDb(context);
        NotesTable tableinfo = new NotesTable();
            for(int i=0;i<catnames.length;i++) {
            tableinfo.topic_name = "Sample Topic 1";
            tableinfo.category_name = catnames[i];
            categoryDb.insert_category(tableinfo);

        }
    }
    public void InstallationTermDefination(Context context){
        TermDb   term_Db=new TermDb(context);
        NotesTable tableinfo = new NotesTable();
        for(int i=0;i<termname.length;i++) {

            tableinfo.topic_name = "Sample Topic 1";
            tableinfo.category_name = "Category 1";
            tableinfo.term_name = termname[i];
            tableinfo.description = description[i];
             term_Db.insert_term(tableinfo);
        }
    }
}
