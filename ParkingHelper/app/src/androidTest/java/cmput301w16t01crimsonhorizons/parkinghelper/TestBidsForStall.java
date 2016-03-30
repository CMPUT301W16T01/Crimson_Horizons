package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

import java.util.ArrayList;

/**
 * Created by Kevin L on 3/11/2016.
 */
public class TestBidsForStall  extends ActivityInstrumentationTestCase2<WelcomeActivity> {
    private Solo solo;
    public TestBidsForStall() {
        super(WelcomeActivity.class);
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
    /**
     * US 05.05.01
     * Test if bids are displayed
     */
    public void testDisplayBids(){
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "123@123");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.BidsOwnStallBtn));
        solo.getView(R.id.OwnStalls);
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
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
