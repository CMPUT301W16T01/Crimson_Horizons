package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Kevin L on 3/11/2016.
 */
public class TestBidsForStall  extends ActivityInstrumentationTestCase2<BidsForStall> {
    public TestBidsForStall() {
        super(BidsForStall.class);
    }

    /**
    /**
     * US 05.05.01
     * Test if bids are displayed
     */
    @UiThreadTest
    public void testDisplayBids(){
        Stalls s1 = new Stalls();
        ArrayList<String> lstBidders = new ArrayList<>();
        lstBidders.add("bid1");
        lstBidders.add("bid2");
/*        s1.setLstBidders(lstBidders);*/

        Intent i = new Intent();
        i.putExtra("stall", s1);
        setActivityIntent(i);

        BidsForStall b = new BidsForStall();

        //Should display two bids
        ListView lv = b.eachStallsWithBids;
        assertEquals(lv.getAdapter().getCount(), 2);
    }

    /**
     * US 05.06.01
     * Test if one can accept the bid
     */
    public void testAcceptBid(){
        Stalls s1 = new Stalls();
        s1.setBidAmt(100.00);

        //Replace with the accept bid command
        CommandForTesting command = new CommandForTesting(s1);
        command.execute();
        assertEquals("Status should be borrowed","Borrowed",s1.getStatus());
    }
    /**
     * US 05.07.01
     * Test if one can decline a bid.
     */
    public void testDeclineBid(){
        Stalls stall = new Stalls();
        stall.setBidAmt(100.0);
        BidsForStall bidsForStall = new BidsForStall();
        //TODO replace with the commands for declining stall. Extend from it. Make it modify all
        // Except for calling elastic search.
        CommandForTesting commandForTesting = new CommandForTesting(stall);
        commandForTesting.execute();
        assertEquals("bidAmt should be now 0",0,stall.getBidAmt());
    }
}
