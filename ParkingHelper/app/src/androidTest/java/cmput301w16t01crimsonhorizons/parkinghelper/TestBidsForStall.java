package cmput301w16t01crimsonhorizons.parkinghelper;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

/**
 * Created by Kevin L on 3/11/2016.
 */
public class TestBidsForStall  extends ActivityInstrumentationTestCase2<BidsForStall> {
    public TestBidsForStall() {
        super(BidsForStall.class);
    }

    /**
     * US 05.03.01
     * Want to see if it is notified.
     */
    public void testNotification(){
        Stalls s1 = new Stalls();
        s1.setOwner("testing");
        Account testing = new Account("testing","","");

        //Replace with commands for notification that adds notification to user, but juest does
        //not actually write to elastic search.
        assertEquals("user testing should have no notifications",0,
                testing.getNotifications().size());
        CommandForTesting commandForTesting = new CommandForTesting(s1);
        commandForTesting.execute();
        assertEquals("user testing should get a notification",1,
                testing.getNotifications().size());
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
