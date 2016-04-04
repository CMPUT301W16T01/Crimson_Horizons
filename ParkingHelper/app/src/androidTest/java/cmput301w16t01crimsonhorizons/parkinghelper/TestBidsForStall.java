package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
     * US 05.04.01
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
     * US 05.01.01
     * Test if the user can bid on stall
     * US 05.06.01
     * Test if one can accept the bid
     */
    public void testAcceptBid(){
        //sign in
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test1");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));

        //add stall
        solo.clickOnView(solo.getView(R.id.AccountBtn));
        solo.clickOnView(solo.getView(R.id.AddBtn));
        solo.enterText((EditText) solo.getView(R.id.NamePrompET), "__test1");
        solo.enterText((EditText) solo.getView(R.id.DescriptionET), "__test1oneAAAAA");
        solo.clickOnView(solo.getView(R.id.AddInAddBtn));
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        //sign in make bid
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "robo");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.SearchBtn));
        solo.enterText((EditText) solo.getView(R.id.query), "__test1oneAAAAA");
        solo.clickOnView(solo.getView(R.id.SearchBtn));
        solo.clickInList(0);
        solo.enterText((EditText) solo.getView(R.id.BidStallAmtET), "1.00");
        solo.clickOnView(solo.getView(R.id.BidStallBidBtn));
        solo.goBack();
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        //sign in as 123@123 make bid
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test2");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.SearchBtn));
        solo.enterText((EditText) solo.getView(R.id.query), "__test1oneAAAAA");
        solo.clickOnView(solo.getView(R.id.SearchBtn));
        solo.clickInList(0);
        solo.enterText((EditText) solo.getView(R.id.BidStallAmtET), "2.00");
        solo.clickOnView(solo.getView(R.id.BidStallBidBtn));
        solo.goBack();
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        //accept bid
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test1");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.BidsOwnStallBtn));
        solo.clickInList(0);
        ListView lv = (ListView)solo.getView(R.id.BidsForStallsLv);
        View second = lv.getChildAt(1);
        Button accept = (Button)second.findViewById(R.id.AcceptBtn);
        solo.clickOnView(accept);
        solo.goBack();
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.LendingBtn));
        ListView inLending = (ListView)solo.getView(R.id.lendingStallsList);
        View elementLending = inLending.getChildAt(0);
        TextView borrower = (TextView)elementLending.findViewById(R.id.StallNameEditStallV);
        assertEquals("should be __test2", "__test2", borrower.getText().toString());
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        //sign in as K to see that he no longer have bids on this stall
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "robo");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.YourBidsBtn));
        ListView yourBids = (ListView)solo.getView(R.id.YourBidsLv);
        assertNull(yourBids.getChildAt(0));
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        //sign in as __test2 to see that he is borrowing 1 stall from __test1
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test2");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.BorrowingBtn));
        ListView lvBorrowing = (ListView)solo.getView(R.id.borrowedStallsList);
        View first = lvBorrowing.getChildAt(0);
        TextView Owner = (TextView)first.findViewById(R.id.OwnerBorrowedStall);
        assertEquals("should be __test1", "__test1", Owner.getText().toString());
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        //clean up to delete stall
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test1");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));

        solo.clickOnView(solo.getView(R.id.AccountBtn));
        solo.clickInList(0);
        solo.assertCurrentActivity("should be in edit stall", EditStall.class);
        solo.clickOnView(solo.getView(R.id.EditStallDeleteBtn));
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }
    /**
     * US 05.07.01
     * Test if one can decline a bid.
     */
    public void testDeclineBid(){
        //sign in
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test1");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));

        //add stall
        solo.clickOnView(solo.getView(R.id.AccountBtn));
        solo.clickOnView(solo.getView(R.id.AddBtn));
        solo.enterText((EditText) solo.getView(R.id.NamePrompET), "__test1");
        solo.enterText((EditText) solo.getView(R.id.DescriptionET), "__test1oneAAAAA");
        solo.clickOnView(solo.getView(R.id.AddInAddBtn));
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        //sign in robo make bid
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "robo");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.SearchBtn));
        solo.enterText((EditText) solo.getView(R.id.query), "__test1oneAAAAA");
        solo.clickOnView(solo.getView(R.id.SearchBtn));
        solo.clickInList(0);
        solo.enterText((EditText) solo.getView(R.id.BidStallAmtET), "1.00");
        solo.clickOnView(solo.getView(R.id.BidStallBidBtn));
        solo.goBack();
        solo.goBack();
        //check robo made bid
        solo.clickOnView(solo.getView(R.id.YourBidsBtn));
        ListView roboBidsLv = (ListView)solo.getView(R.id.YourBidsLv);
        View roboBid = roboBidsLv.getChildAt(0);
        TextView Owner_of_roboBid = (TextView)roboBid.findViewById(R.id.StallNameEditStallV);
        assertEquals("Owner should be __test1", "__test1", Owner_of_roboBid.getText().toString());
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        //sign in as __test2 make bid
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test2");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.SearchBtn));
        solo.enterText((EditText) solo.getView(R.id.query), "__test1oneAAAAA");
        solo.clickOnView(solo.getView(R.id.SearchBtn));
        solo.clickInList(0);
        solo.enterText((EditText) solo.getView(R.id.BidStallAmtET), "2.00");
        solo.clickOnView(solo.getView(R.id.BidStallBidBtn));
        solo.goBack();
        solo.goBack();
        //check __test2 have made bid
        solo.clickOnView(solo.getView(R.id.YourBidsBtn));
        ListView __test2BidsLv = (ListView)solo.getView(R.id.YourBidsLv);
        View __test2Bid = __test2BidsLv.getChildAt(0);
        TextView Owner_of_test2Bid = (TextView)__test2Bid.findViewById(R.id.StallNameEditStallV);
        assertEquals("Owner should be __test1","__test1",Owner_of_test2Bid.getText().toString());
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));


        // First bidder == robo
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test1");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.BidsOwnStallBtn));
        solo.clickInList(0);
        ListView lv = (ListView)solo.getView(R.id.BidsForStallsLv);
        View second = lv.getChildAt(0);
        TextView bidder_before = (TextView)second.findViewById(R.id.BidderBFS);
        Button decline = (Button)second.findViewById(R.id.DeclineBtn);

        assertEquals("bidder should be robo","robo",bidder_before.getText().toString());

        //decline bid robo's bid
        solo.clickOnView(decline);
        solo.goBack();
        solo.goBack();
        //check now first bidder == __test2
        solo.clickOnView(solo.getView(R.id.BidsOwnStallBtn));
        solo.clickInList(0);
        ListView lvBidsOnOwn = (ListView)solo.getView(R.id.BidsForStallsLv);
        View first = lvBidsOnOwn.getChildAt(0);
        TextView bidder = (TextView)first.findViewById(R.id.BidderBFS);
        assertEquals("bidder should now be __test2", "__test2", bidder.getText().toString());
        solo.goBack();
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        //sign in as robo see bids have been declined
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "robo");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        //check robo  bid
        solo.clickOnView(solo.getView(R.id.YourBidsBtn));
        ListView roboBidsLv_after = (ListView)solo.getView(R.id.YourBidsLv);
        View roboBid_after = roboBidsLv_after.getChildAt(0);
        assertNull(roboBid_after);
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        //sign in as __test2 see bids have been not been declined
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test2");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        //check __test2  bid
        solo.clickOnView(solo.getView(R.id.YourBidsBtn));
        ListView __test2BidsLv_after = (ListView)solo.getView(R.id.YourBidsLv);
        View __test2Bid_after = __test2BidsLv_after.getChildAt(0);
        TextView Owner_of_test2Bid_after = (TextView)__test2Bid_after.findViewById(R.id.StallNameEditStallV);
        assertEquals("Owner should be __test1","__test1",Owner_of_test2Bid.getText().toString());
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        //clean up to delete stall
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test1");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));

        solo.clickOnView(solo.getView(R.id.AccountBtn));
        solo.clickInList(0);
        solo.assertCurrentActivity("should be in edit stall", EditStall.class);
        solo.clickOnView(solo.getView(R.id.EditStallDeleteBtn));
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }
}
