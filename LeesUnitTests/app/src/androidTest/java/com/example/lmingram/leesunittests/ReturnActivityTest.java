package com.example.lmingram.leesunittests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

/**
 * Created by lmingram on 2/12/16.
 */
public class ReturnActivityTest extends ActivityInstrumentationTestCase2 {
    ReturnActivity activity;
    ListView view;

    public ReturnActivityTest(Class activityClass) {
        super(ReturnActivity.class);
    }

    protected void setUp(){
        activity = (ReturnActivity)getActivity();
        view = (ListView)activity.findViewById(R.id.returned_things);
    }

    public void testReturn(){
        int before = activity.getNumItems();
        view.performClick();
        int after = activity.getNumItems();

        assertEquals(after + 1, before);
    }
}
