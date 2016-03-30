package cmput301w16t01crimsonhorizons.parkinghelper;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.ListView;

import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 3/10/2016.
 */
public class TestPendingBidActivity extends ActivityInstrumentationTestCase2<HomepageActivity> {
    private Solo solo;
    public TestPendingBidActivity() {
        super(HomepageActivity.class);
    }
    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();
    }
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    /**
     * This test US 05.02.01
     * Depends on: BidList.class, PendingBids.class
     * It test if calling for the get method can successfully
     * retrieve the updated listIt also checks if the view exists.
     */
    public void testViewPending(){
        solo.unlockScreen();
        solo.clickOnView(solo.getView(R.id.YourBidsBtn));
        solo.assertCurrentActivity("should be in yourbids", Results.class);
        solo.getView(R.id.YourBidsLv);
    }
}
