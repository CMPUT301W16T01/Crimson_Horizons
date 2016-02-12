package cmput301w16t01.kliangtestcases;

import android.app.Application;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.test.ViewAsserts;
import android.view.View;

import java.util.ArrayList;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class StallListTest extends ActivityInstrumentationTestCase2 {
    public StallListTest() {
        super(StallList.class);
    }
    //Tests UC 01.01.01
    //Depends on: StallList.class
    //      : Stalls.class
    // This tests if the owner can add a stall to his collections.
    // It will create a list, get the size before adding, create a stall, add the stall
    // using the addStalls method and check that the size of the list has increased by 1.
    public void testAddStall(){
        StallList OwnStalls= new StallList();
        int sizeBeforeAdd = OwnStalls.size();
        Stalls stall = new Stalls();
        stall.setInformation("Hi");
        OwnStalls.addStall(stall);
        int sizeAfter=OwnStalls.size();
        assertEquals(sizeBeforeAdd+1,sizeAfter);
        assertEquals(OwnStalls.get(OwnStalls.size()-1).getInformation(),"Hi");
    }
    //Tests UC 01.02.01
    //Depends on: StallList.class
    //      : Stalls.class
    //      : UITesting.class
    // This will test if Owner can view a lists of their stalls with their status.
    // It will test the method getLst to see if it returns the right list.
    // It will also test the UI by hitting the button and asserting that the view exists.
    public void testViewList(){
        StallList OwnStalls = new StallList();
        OwnStalls.clearLst();
        UITesting ui = new UITesting();
        Stalls stall = new Stalls();
        stall.setInformation("hi");
        OwnStalls.addStall(stall);
        ArrayList<Stalls> ReturnedLst=OwnStalls.getLst();
        assertEquals(ReturnedLst.get(0),OwnStalls.get(0));
        assertEquals(ReturnedLst.get(0).getInformation(), OwnStalls.get(0).getInformation());
        ui.clickAccount();
        OwnStallController osc = new OwnStallController();
        ViewAsserts.assertOnScreen(osc.getWindow().getDecorView(),osc.findViewById(R.id.OwnStallLv));
    }

    // Test use cases UC 01.05.01
    // Depends on: StallList.class
        //       : Stalls.class
    // This tests if Owner can delete things. It will create a stall add to list, get the size of
    // the list and use the deleteStalls method and get the size again.
    // It will assert that the size is 1 less.
    public void testdeleteStall(){
        StallList OwnStalls = new StallList();
        Stalls nstall = new Stalls();
        OwnStalls.addStall(nstall);;
        int sizeBefore=OwnStalls.size();
        OwnStalls.deleteStalls(0);
        int sizeAfter=OwnStalls.size();
        assertEquals(sizeBefore,sizeAfter+1);

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
        assertTrue(ui.checkOnlineData(stall1));

    }

}
