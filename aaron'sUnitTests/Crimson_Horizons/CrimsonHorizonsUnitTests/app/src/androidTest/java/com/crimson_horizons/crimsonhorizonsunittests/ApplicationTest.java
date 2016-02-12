package com.crimson_horizons.crimsonhorizonsunittests;


import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2 {
    public ApplicationTest() {
        super(MainActivity.class);
    }

    //2.01.01
    public void testChangeStatus(){
        Intent intent = new Intent();

        ParkingStall parkingStall = generateParkingStalls();
        intent.putExtra(MainActivity.ADD_PARKING_STALL,parkingStall.getAvailability());
        intent.putExtra(MainActivity.STALL_STATUS_KEY, MainActivity.AVAILABLE);

        setActivityIntent(intent);
        MainActivity ira = (MainActivity) getActivity();
        TextView textView = (TextView) ira.findViewById(R.id.tempText);

        assertEquals("The parking stall status be displayed!", "AVAILABLE", textView.getText().toString()) ;

        Button button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        View view = ira.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(view, ira.findViewById(R.id.tempText));

    }

    //3.01.01
    public void testSignUp(){
        Intent intent = new Intent();
        intent.putExtra(SignupPage.TEXT_TO_NEW_USER_KEY, "NEW_USER");
        intent.putExtra(SignupPage.MEANS_OF_INPUT_KEY, SignupPage.USER_NAME);

        setActivityIntent(intent);
        MainActivity ira = (MainActivity) getActivity();

        TextView textView = (TextView) ira.findViewById(R.id.tempText);
        assertEquals("The user name should be displayed!", "NEW_USER", textView.getText().toString());

        intent.putExtra(SignupPage.MEANS_OF_INPUT_KEY, SignupPage.PASSWORD);
        intent.putExtra(SignupPage.TEXT_TO_NEW_USER_KEY, "NEW_PASSWORD");

        setActivityIntent(intent);
        ira = (MainActivity) getActivity();

        textView = (TextView) ira.findViewById(R.id.tempText);
        assertEquals("The password should be displayed!", "NEW_PASSWORD", textView.getText().toString());

        Button button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        View view = ira.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(view, ira.findViewById(R.id.tempText));
    }

    //3.02.01
    public void testEditContactInformation(){
        Intent intent = new Intent();
        intent.putExtra(SignupPage.MEANS_OF_INPUT_KEY, SignupPage.CONTACT_INFO);
        intent.putExtra(SignupPage.TEXT_TO_NEW_USER_KEY, "OLD_INFO");

        setActivityIntent(intent);
        MainActivity ira = (MainActivity) getActivity();

        TextView textView = (TextView) ira.findViewById(R.id.tempText);
        assertEquals("The contact info should be displayed!", "OLD_INFO", textView.getText().toString());


        //save profile
        Button button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        //set ira to other places

        //go to profile page
        button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        //click edit profile button
        button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        intent.putExtra(SignupPage.MEANS_OF_INPUT_KEY, EditContactInformationPage.CONTACT_INFO);
        intent.putExtra(SignupPage.TEXT_TO_NEW_USER_KEY, "NEW_INFO");

        //save profile
        button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        //go to profile pagetempB

        button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        setActivityIntent(intent);
        ira = (MainActivity) getActivity();

        textView = (TextView) ira.findViewById(R.id.tempText);
        assertEquals("The user name should be displayed!", "NEW_INFO", textView.getText().toString());
    }

    //3.03.01
    public void testShowUserInfo(){
        Intent intent = new Intent();

        ParkingStall parkingStall = generateParkingStalls();
        intent.putExtra(MainActivity.ADD_PARKING_STALL,parkingStall.getAvailability());

        setActivityIntent(intent);

        intent.putExtra(SignupPage.MEANS_OF_INPUT_KEY, SignupPage.CONTACT_INFO);
        intent.putExtra(SignupPage.TEXT_TO_NEW_USER_KEY, "INFO");

        setActivityIntent(intent);
        MainActivity ira = (MainActivity) getActivity();

        //save profile
        Button button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        //go to parking lot page
        button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        TextView textView = (TextView) ira.findViewById(R.id.tempText);
        assertEquals("The contact info should be displayed!", "INFO", textView.getText().toString());
    }

    //5.03.01
    public void testNotifyOwner(){
        Intent intent = new Intent();

        ParkingStall parkingStall = generateParkingStalls();
        intent.putExtra(MainActivity.ADD_PARKING_STALL,parkingStall.getAvailability());

        setActivityIntent(intent);

        intent.putExtra(SignupPage.MEANS_OF_INPUT_KEY, SignupPage.CONTACT_INFO);
        intent.putExtra(SignupPage.TEXT_TO_NEW_USER_KEY, "INFO");

        setActivityIntent(intent);
        MainActivity ira = (MainActivity) getActivity();

        Button button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        View view = ira.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(view, ira.findViewById(R.id.tempButton));

    }

    //01.03.01
    public void testDisplayParkingStallInfo() {
        Intent intent = new Intent();

        ParkingStall parkingStall = generateParkingStalls();
        intent.putExtra(MainActivity.ADD_PARKING_STALL,parkingStall.getAvailability());

        setActivityIntent(intent);
        MainActivity ira = (MainActivity) getActivity();
        TextView textView = (TextView) ira.findViewById(R.id.tempText);

        assertEquals("The parking stall should be displayed!", parkingStall.getAvailability(), textView.getText().toString()) ;

        Button button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        View view = ira.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(view, ira.findViewById(R.id.tempText));
    }

    //1.04.01
    public void testAddNewItem(){
        Intent intent = new Intent();

        ParkingStall parkingStall = generateParkingStalls();
        intent.putExtra(MainActivity.ADD_PARKING_STALL,parkingStall.getAvailability());

        setActivityIntent(intent);
        MainActivity ira = (MainActivity) getActivity();

        Button button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        button = (Button) ira.findViewById(R.id.tempButton);
        button.performClick();

        View view = ira.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(view, ira.findViewById(R.id.tempText));
    }


    private ParkingStall generateParkingStalls(){
        ParkingStall returnParkingStall = new ParkingStall();
        returnParkingStall.setAvailability(new RandomString(15).nextString());
        return returnParkingStall;
    }





}



