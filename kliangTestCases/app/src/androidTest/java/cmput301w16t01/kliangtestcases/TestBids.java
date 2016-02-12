package cmput301w16t01.kliangtestcases;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by kliang on 2/9/16.
 */
public class TestBids extends ActivityInstrumentationTestCase2 {
    public TestBids() {
        super(BidList.class);
    }
    // This test UC 05.01.01
    //Depends on: StallsForBid.class
    //This use cases assumes that there will be some stalls available for bidding
    // It just test whether using the "bid" method can successfully update the status.
    public void testBidding(){

        StallsForBid sfb = new StallsForBid();
        sfb.bid(100.00);
        assertEquals(sfb.getbidAmt(), 100);
        sfb.bid(200.0);
        assertEquals(sfb.getbidAmt(),200);
    }
    //This test UC 05.02.01
    // Depends on: BidList.class
        //       : PendingBids.class
    //It test if calling for the get method can successfully retrieve the updated list
    // It also checks if the view exists.
    public void testViewPending(){
        BidList bidlst=new BidList();
        ArrayList<StallsForBid>AvailabeStalls=bidlst.getAvailableStalls();
        assertTrue(AvailabeStalls.getClass().equals(ArrayList.class));
        UITesting ui = new UITesting();
        ui.clickPending();
        PendingBids pb = new PendingBids();
        ListView lv=(ListView)pb.findViewById(R.id.pendinglst);
        ViewAsserts.assertOnScreen(pb.getWindow().getDecorView(),lv);
    }
}
