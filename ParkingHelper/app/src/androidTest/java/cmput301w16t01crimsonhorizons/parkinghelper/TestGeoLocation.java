package cmput301w16t01crimsonhorizons.parkinghelper;

import android.app.Activity;
import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;

/**
 * Created by Kevin L on 3/11/2016.
 */
public class TestGeoLocation extends ActivityInstrumentationTestCase2<Activity> {
    public TestGeoLocation(Class<Activity> activityClass) {
        super(activityClass);
    }

    /**
     * US 10.01.01 (added 2016-02-29)
     * Test if the user can actually place a specific geolocation.
     * It will simulate user by specifying location, then calls command to see if location is set.
     */
    public void testSepcifyLocation(){
        Location local = new Location("here");
        //Replace with commands that does the setting.
        CommandForTesting command = new CommandForTesting(local);
        //todo Change when using real one.
        Boolean returnedLocal = command.execute();
        assertEquals("2 location should be same", local, returnedLocal);
    }

    /**
     * US 10.02.01 (added 2016-02-29)
     * Test if user can see locatoin
     */
    public void testViewLocation(){
        //TODO temp button, need to get the real button that will open up the map
        Button temp = new Button(getActivity().getApplication());
        temp.performClick();
        //Wants to here assert if a map is on screen. or a map is opened.
        ViewAsserts.assertOnScreen(new View(getActivity().getApplicationContext()),
                new View(getActivity().getApplicationContext()));
    }
}
