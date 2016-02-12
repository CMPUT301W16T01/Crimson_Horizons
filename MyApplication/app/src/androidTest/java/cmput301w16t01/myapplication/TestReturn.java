package cmput301w16t01.myapplication;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.test.ViewAsserts;
import android.widget.Button;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class TestReturn extends ActivityInstrumentationTestCase2 {
    public TestReturn() {
        super(ReturnActivity.class);
    }

    public void testReturnButton(){
        ReturnActivity ra = (ReturnActivity) getActivity();
        Button button = (Button)ra.findViewById(R.id.ViewReturned);
        button.performClick();
        ViewAsserts.assertOnScreen(ra.getWindow().getDecorView(),
                ra.findViewById(R.id.ReturnedItemVw));

    }
}