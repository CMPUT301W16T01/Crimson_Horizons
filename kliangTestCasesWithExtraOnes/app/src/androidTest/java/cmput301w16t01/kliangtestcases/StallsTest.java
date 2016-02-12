package cmput301w16t01.kliangtestcases;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by Kevin L on 2/11/2016.
 */
public class StallsTest extends ActivityInstrumentationTestCase2 {
    public StallsTest() {
        //We make a test with the starting point of app so have access to everything.
        super(Stalls.class);
    }

    //Tests: UC 02.01.01
        //Depends on: Stalls.class
    //It will create a new stalls object and try to set the status
        //It will then retrieve the status to see if it is set.
    public void testStallStatus(){
        Stalls stall1 = new Stalls();
        stall1.setStatus("availabe");
        Stalls stall2 = new Stalls();
        stall2.setStatus("Borrowed");
        assertEquals(stall1.getStatus(),"available");
        assertEquals(stall2.getStatus(),"Borrowed");
    }

    //Tests: UC 07.01.01
    //Depends on: Stalls.class
    //          : StallLst.class
    //It will get an item add it to the borrowed list. It will then use the returnStall method
    // on that item. It will check if the status is changed.
    public void testReturnStall(){
        Stalls returnStall = new Stalls();
        StallList lst = new StallList();
        ArrayList<Stalls>BorrowedLst = lst.getBorrowingStalls();
        returnStall.setStatus("Borrowed");
        BorrowedLst.add(returnStall);
        lst.returnStalls(returnStall);
        assertFalse(BorrowedLst.contains(returnStall));
        //Here I will obtain the availabe stalls list to check my item is in there
        BidList bidlst = new BidList();
        ArrayList<StallsForBid> Available = bidlst.getAvailableStalls();
        assertTrue(Available.contains(returnStall));
    }

    //Tests: UC 08.01.01
    // Depends on: UITesting.class
    // This will test if it can do changes offline, store it and will be able to write it once
    // connection is made.
    public void testOffline(){
        //Will simulate a offline behaviour by not using any method that requires connection like
        // elastic search.
        // It will make a new item, use the offlineSave and onlineSave method to determine if the
        // methods was working and the data are stored.
        Stalls stall1 = new Stalls();
        StallList lst = new StallList();
        ArrayList<Stalls> OwnStalls = lst.getLst();
        OwnStalls.add(stall1);
        Save save = new Save();
        save.offlineSave();
        save.onlineSave();
        UITesting ui = new UITesting();
        //This will check if it was stored properly
        ui.checkOnlineData(stall1);

    }
}
