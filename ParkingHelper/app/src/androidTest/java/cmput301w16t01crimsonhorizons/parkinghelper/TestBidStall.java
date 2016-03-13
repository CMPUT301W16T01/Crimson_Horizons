package cmput301w16t01crimsonhorizons.parkinghelper;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 3/10/2016.
 */
public class TestBidStall extends ActivityInstrumentationTestCase2<AccountActivity> {
    public TestBidStall() {
        super(AccountActivity.class);
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

}
