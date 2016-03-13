package cmput301w16t01crimsonhorizons.parkinghelper;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 3/10/2016.
 */
public class TestPendingBidActivity extends ActivityInstrumentationTestCase2<HomepageActivity> {
    public TestPendingBidActivity() {
        super(HomepageActivity.class);
    }
    public ArrayList<Stalls> retrieveStall(String[] temp){
        ArrayList<Stalls>tempreturn = new ArrayList<Stalls>();
        ElasticSearchForTest.GetStall getStall = new ElasticSearchForTest.GetStall();
        getStall.execute(temp);
        try {
            tempreturn = getStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return tempreturn;
    }

    /**
     * This test US 05.02.01
     * Depends on: BidList.class, PendingBids.class
     * It test if calling for the get method can successfully
     * retrieve the updated listIt also checks if the view exists.
     */
    public void testViewPending(){
        String[] query = new String[2];
        query[0]="Available";
        query[1]="Status";
        ArrayList<Stalls> AvailabeStalls=this.retrieveStall(query);
        assertTrue(AvailabeStalls.getClass().equals(ArrayList.class));
        HomepageActivity activity = (HomepageActivity)getActivity();
        Button YourBids = (Button)activity.findViewById(R.id.YourBidsBtn);
        YourBids.performClick();
        Results yourBids = new Results();
        ListView lv=(ListView)yourBids.findViewById(R.id.YourBidsLv);
        ViewAsserts.assertOnScreen(yourBids.getWindow().getDecorView(), lv);
    }
}
