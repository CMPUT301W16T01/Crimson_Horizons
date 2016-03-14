package cmput301w16t01crimsonhorizons.parkinghelper;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import android.test.UiThreadTest;

import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.lang3.ObjectUtils;

/**
 * Created by schuman on 3/10/16.
 */
public class TestSearch extends ActivityInstrumentationTestCase2 {
    Instrumentation instrumentation;
    Activity activity;
    EditText testInput;
    boolean accountMade = false;
    Stalls testStall;
    Account testAccount;


    public TestSearch() {
        super(Search.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
        testInput = ((EditText) activity.findViewById(R.id.query));

    }



    protected  void makeSearch(String text) {
        testInput.setText(text);
        ((Button) activity.findViewById(R.id.SearchBtn)).performClick();
    }

    @UiThreadTest
    public void testSearchKeyWords(){

        Search searchActivity = (Search)getActivity();
        makeSearch("(test 1)");
        while (searchActivity.getResults().isEmpty());
        assertTrue(searchActivity.getAdapter().getCount() == 2);

    }

    @UiThreadTest
    public void testSearchView() {
        Search search = (Search)getActivity();
        while (search.getResults().isEmpty());
        View view1 = search.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(view1, search.findViewById(R.id.ResultLv));
        ViewAsserts.assertOnScreen(view1, search.findViewById(R.id.SearchTitle));
        ViewAsserts.assertOnScreen(view1, search.findViewById(R.id.query));
        ViewAsserts.assertOnScreen(view1, search.findViewById(R.id.SearchBtn));
    }

}
