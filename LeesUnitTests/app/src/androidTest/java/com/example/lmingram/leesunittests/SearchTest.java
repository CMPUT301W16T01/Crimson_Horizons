package com.example.lmingram.leesunittests;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestSuite;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by lmingram on 2/12/16.
 */
public class SearchTest extends ActivityInstrumentationTestCase2 {
    Instrumentation instrumentation;
    Activity activity;
    EditText searchBar;
    ArrayList<LeeThing> expectedSearchResults;


    public SearchTest() {
        super(SearchActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        searchBar = ((EditText) activity.findViewById(R.id.search_bar));
        expectedSearchResults = new ArrayList<LeeThing>();

    }

    private String makeSearchTags(){
        return "";
    }

    // US 4.01.01
    // us 4.02.01
    @UiThreadTest
    private void testSearch(String tags){
        SearchActivity activity = (SearchActivity)getActivity();
        searchBar.setText(tags);
        Button searchButton = (Button) activity.findViewById(R.id.search_button);
        searchButton.performClick();

        assertEquals(expectedSearchResults, activity.getSearchResults());
    }







}
