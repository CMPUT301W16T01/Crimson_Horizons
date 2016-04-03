package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by jpeard on 3/13/16.
 */

public class TestOwnStallsWithBids extends ActivityInstrumentationTestCase2 {
    ArrayList<Stalls> tempAry;
    Account account1;
    private Solo solo;

    public TestOwnStallsWithBids() { super(WelcomeActivity.class); }
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
     * test to see if can get own stalls with bids. Has UI component
     * US 05.04.01
     */
    public void testStallsList(){
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test1");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        Stalls s1 = new Stalls();
        s1.setStatus("Bidded");
        Bid b1 = new Bid("123@123", 100.00, s1.getStallID());
        s1.setOwner("__test1");
        ElasticSearchCtr.MakeStall makeStall = new ElasticSearchCtr.MakeStall();
        makeStall.execute(s1);
        try {
            makeStall.get();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        solo.clickOnView(solo.getView(R.id.BidsOwnStallBtn));
        solo.assertCurrentActivity("should be in own stalls",OwnStallsWithBidsActivity.class);
        ListView lv = (ListView)solo.getView(R.id.OwnStalls);
        View listelement = lv.getChildAt(0);
        assertNotNull(listelement);


        ElasticSearchCtr.DeleteStall deleteStall = new ElasticSearchCtr.DeleteStall();
        deleteStall.execute(s1);
        Boolean check = false;
        try {
            check = deleteStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertTrue("didn't delete Stall", check);
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }

    /*@Override
    protected void tearDown() throws Exception {
        ElasticSearchCtr.DeleteStall deleteStall = new ElasticSearchCtr.DeleteStall();
        deleteStall.execute(tempAry.get(0));

        ElasticSearchCtr.deleteUser deleteUser = new ElasticSearchCtr.deleteUser();
        deleteUser.execute(account1);
    }*/

}

