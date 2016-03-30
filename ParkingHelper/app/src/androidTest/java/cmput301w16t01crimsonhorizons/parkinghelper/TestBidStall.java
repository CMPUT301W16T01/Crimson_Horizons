package cmput301w16t01crimsonhorizons.parkinghelper;

import android.app.Instrumentation;
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
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 3/10/2016.
 */
public class TestBidStall extends ActivityInstrumentationTestCase2<WelcomeActivity> {
    private Solo solo;
    public TestBidStall() {
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
    public void createStall(Stalls s1){
        ElasticSearchForTest.MakeStall makeStall = new ElasticSearchForTest.MakeStall();
        makeStall.execute(s1);
        try {
            makeStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public void resetDatabase(Stalls s1){
        ElasticSearchForTest.DeleteStall deleteStall = new ElasticSearchForTest.DeleteStall();
        deleteStall.execute(s1);
        try {
            deleteStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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
    public Boolean bidSimulate(Stalls s1){
        ElasticSearchForTest.updateStallES updateStallES=new ElasticSearchForTest.updateStallES();
        updateStallES.execute(s1);
        Boolean check = false;
        try {
            check = updateStallES.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return check;
    }

    /**
     * US 05.01.01
     * Test bidding on an available stall.
     * It basically makes a stall set with some bids. It then update the bids, just like what would
     * happen if the user hit the bid button after entering amount.
     * It then tries to update this information and assert it is done.
     */
    public void testBidOnAvailable(){
        Stalls stall = new Stalls();
        stall.setBidAmt(100.00);
        stall.setOwner("testing");
        this.createStall(stall);
        ArrayList<Stalls>returned = new ArrayList<>();
        String[] temp = new String[2];
        temp[1] = "_id";
        temp[0] = stall.getStallID();
        while (returned.size()<1) {
            returned = this.retrieveStall(temp);
        }
        assertEquals("It should have bid of 100.00", 100.00, returned.get(0).getBidAmt());

        stall.setBidAmt(200.00);
        Boolean check = this.bidSimulate(stall);
        assertTrue(check);
        returned.clear();
        returned = this.retrieveStall(temp);
        while (returned.get(0).getBidAmt()!=200){
            returned = this.retrieveStall(temp);
        }
        this.resetDatabase(stall);

    }

    /**
     * US 05.02.01
     * Test to see if list view appears and that stalls
     */
    public void testListView() {
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "123@123");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.unlockScreen();
        solo.clickOnView(solo.getView(R.id.YourBidsBtn));
        solo.getView(R.id.YourBidsLv);
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));


    }

    /**
     * US 05.02.01
     */
    public void testOpenNextActivity() {
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "123@123");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.YourBidsBtn));
/*        solo.clickInList(0);
        solo.assertCurrentActivity("Expected Edit Bid activity", EditBids.class);*/
        solo.assertCurrentActivity("Expect to have the list of bids", Results.class);
        ListView lv = (ListView)solo.getView(R.id.YourBidsLv);
        assertNotNull(lv);
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }

    /**US 05.03.01
     * Tests when user makes the bid successfully, will the notificaiont be made and
     * will it be stored properly.
     */
    public void testNotification(){
        NotificationObject notification = new NotificationObject();
        notification.setOwner("testing");
        notification.setBidder("bidder");
        notification.setBidAmt("9.99");
        notification.setDate("Where the date is");
        NotificationESForTest.AddNotification addNotification = new NotificationESForTest.AddNotification();
        addNotification.execute(notification);
        try {
            addNotification.get();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        NotificationESForTest.GetNotifications getNotifications = new NotificationESForTest.GetNotifications();
        String[] query = new String[2];
        query[1]="Owner";
        query[0]="testing";
        ArrayList<NotificationObject>returned = new ArrayList<>();
        getNotifications.execute(query);
        try {
            returned=getNotifications.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertEquals("Size should be only one",1,returned.size());
        assertEquals("Owner should be same",notification.getOwner(),returned.get(0).getOwner());
        assertEquals("Bidder should be same",notification.getBidder(),returned.get(0).getBidder());
        assertEquals("Date should be same",notification.getDate(),returned.get(0).getDate());
        NotificationESForTest.DeleteNotification reset = new NotificationESForTest.DeleteNotification();
        Boolean check = false;
        try {
            Thread.sleep(2000);
            reset.execute(notification);
            check = reset.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (!check){
            assertTrue("Didn't clean up",false);
        }


    }
}
