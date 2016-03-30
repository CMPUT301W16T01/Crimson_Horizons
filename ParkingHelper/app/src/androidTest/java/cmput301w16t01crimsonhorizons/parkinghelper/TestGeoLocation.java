package cmput301w16t01crimsonhorizons.parkinghelper;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 3/11/2016.
 */
public class TestGeoLocation extends ActivityInstrumentationTestCase2<HomepageActivity> {
    public TestGeoLocation() {
        super(HomepageActivity.class);
    }

    /**
     * US 10.01.01 (added 2016-02-29)/US 10.02.1
     * Test if the user can actually place a specific geolocation.
     * At the same time, it also tests if the location is stored properly and retrievable.
     * It will simulate user by specifying location, then calls command to see if location is set.
     */
    public void testSepcifyLocation(){
        Double[] location = new Double[2];
        Stalls stall = new Stalls();
        location[1]=9.99;
        location[0]=9.99;
        stall.setLocation(location);
        stall.setOwner("testing");
        ElasticSearchForTest.MakeStall makeStall = new ElasticSearchForTest.MakeStall();
        makeStall.execute(stall);
        try {
            makeStall.get();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ElasticSearchForTest.GetStall getStall = new ElasticSearchForTest.GetStall();
        String[]query = new String[2];
        query[1]="Owner";
        query[0]="testing";
        getStall.execute(query);
        ArrayList<Stalls> returned = new ArrayList<>();
        try {
            returned = getStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertTrue("Size should be one, otherwise test_database not clean", returned.size() == 1);
        assertEquals("long should be same", location[0], returned.get(0).getLocation()[0]);
        assertEquals("lat should be same", location[1], returned.get(0).getLocation()[1]);
        ElasticSearchForTest.DeleteStall deleteStall = new ElasticSearchForTest.DeleteStall();
        deleteStall.execute(stall);
        Boolean deleted = false;
        try {
            deleted = deleteStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (!deleted){
            assertTrue("NOT CLEANED UP", false);
        }
    }
}
