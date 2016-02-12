package com.example.lmingram.leesunittests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by lmingram on 2/12/16.
 */
public class BorrowedTest extends ActivityInstrumentationTestCase2 {
    BorrowingActivity activity;
    Button borrowedButton;
    Button ownedButton;
    ArrayList<LeeThing> expectedBorrowed;
    ArrayList<LeeThing> expectedOwned;

    public BorrowedTest(Class activityClass) {
        super(BorrowingActivity.class);
     }

    protected void setUp(){
        activity = (BorrowingActivity)getActivity();
        borrowedButton = (Button) activity.findViewById(R.id.borrowed_button);
        ownedButton = (Button) activity.findViewById(R.id.owned_button);
    }

    protected void addThings(){}

    //US 6.01.01
    //depends on
    public void testBorrowed(){
        addThings();
        borrowedButton.performClick();

        assertEquals(expectedBorrowed, activity.getThingsOnScreen());

    }

    //US 6.02.01
    public void testOwned(){
        addThings();
        ownedButton.performClick();

        assertEquals(expectedOwned, activity.getThingsOnScreen());
    }
}
