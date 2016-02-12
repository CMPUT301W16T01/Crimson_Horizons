package cmput301w16t01.kliangtestcases;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by kliang on 2/9/16.
 */
public class TestBids extends ActivityInstrumentationTestCase2 {
    public TestBids() {
        //We make a test with the starting point of app so have access to everything.
        super(BidList.class);
    }
    // This test UC 05.01.01
    //Depends on: StallsForBid.class
    //This use cases assumes that there will be some stalls availabe for bidding
    // It just test whether using the "bid" method can succesfully update the status
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
    // This test assumes that by using the adpater, the UI will be updated
    //It just test if calling for the get method can succesfully retrieve the updated list
    public void testViewPending(){
        BidList bidlst=new BidList();
        ArrayList<StallsForBid>AvailabeStalls=bidlst.getAvailableStalls();
        assertTrue(AvailabeStalls.getClass().equals(ArrayList.class));
        PendingBids pb = new PendingBids();
        ListView lv=(ListView)pb.findViewById(R.id.pendinglst);
        ViewAsserts.assertOnScreen(pb.getWindow().getDecorView(),lv);
    }
    //Tests: UC 05.03.01
    //Depends on: BidList.class
    //          : StallsForBid.class
    //          ; UITesting.class
    // Inside the Bidlist.class there will be a notifyOwner method.
    // This method is called anytime there is a change in bid amount.
    // This method will create a button for notification.
    // This method will see if that button exists, and if it can be clicked.
    public void testNotifyOwner(){
        BidList blst = new BidList();
        StallsForBid sfb = new StallsForBid();
        sfb.setOwner("hi");
        blst.notifyOwner(sfb);
        UITesting ui = new UITesting();
        ui.clickViewBids();
        ui.clickNotification();
        Notification note= new Notification();
        ViewAsserts.assertOnScreen(note.getWindow().getDecorView(),
                note.findViewById(R.id.notificatoinVw));
    }
}
