package cmput301w16t01crimsonhorizons.parkinghelper;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 3/10/2016.
 */
public class TestBidStall extends ActivityInstrumentationTestCase2<Results> {
    public TestBidStall() {
        super(Results.class);
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
    @UiThreadTest
    public void testListView() {
        Results pendingStalls = (Results)getActivity();

        ViewAsserts.assertOnScreen(pendingStalls.getWindow().getDecorView(),
                pendingStalls.findViewById(R.id.YourBidsLv));

        for(Stalls ubid: pendingStalls.getUserBids()) {
            assertNotNull(ubid);
        }


    }

    /**
     * US 05.02.01
     */
    @UiThreadTest
    public void testOpenNextActivity() {
        // register next activity that need to be monitored.
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(EditBids.class.getName(), null, false);  // open current activity.
        final Results myActivity = getActivity();
        final ListView listView = (ListView) myActivity.findViewById(R.id.YourBidsLv);
        Stalls stall = new Stalls();
        stall.setStatus("Bidded");
        stall.setDescription("whatever");
        stall.setOwner("whatever");

        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // click button and open next activity.
                while (myActivity.getUserBids().isEmpty());
                listView.getChildAt(0).performClick();
            }
        });  //Watch for the timeout
        //example values 5000 if in ms, or 5 if it's in seconds.
        EditBids nextActivity = (EditBids)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity.finish();
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
