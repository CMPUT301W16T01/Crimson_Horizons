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
        //We make a test with the starting point of app so have access to everything.
        super(StallList.class);
    }
    //Test use cases UC 01.04.01
    //Depends on: StallList.class
            //  : Stalls.class
    public void testEdit(){
        ArrayList<Stalls> OwnStalls= new ArrayList<>();
        Stalls stall_add = new Stalls();
        OwnStalls.add(stall_add);
        Stalls stall = OwnStalls.get(0);
        Stalls nStall= new Stalls();
        assertNotSame(stall, nStall);
        stall.editOwnStall(stall, nStall);
        assertEquals(stall,nStall);

    }
    // Test use cases UC 01.05.01
    // Depends on: StallList.class
        //       : Stalls.class
    public void testdeleteStall(){
        StallList OwnStalls = new StallList();
        Stalls nstall = new Stalls();
        OwnStalls.addStall(nstall);;
        int sizeBefore=OwnStalls.size();
        OwnStalls.deleteStalls(0);
        int sizeAfter=OwnStalls.size();
        assertEquals(sizeBefore,sizeAfter+1);

    }
    //Tests UC 01.01.01
    //Depends on: StallList.class
        //      : Stalls.class
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
    public void testViewList(){
        StallList OwnStalls = new StallList();
        OwnStalls.clearLst();
        Stalls stall = new Stalls();
        stall.setInformation("hi");
        OwnStalls.addStall(stall);
        ArrayList<Stalls> ReturnedLst=OwnStalls.getLst();
        assertEquals(ReturnedLst.get(0),OwnStalls.get(0));
        assertEquals(ReturnedLst.get(0).getInformation(), OwnStalls.get(0).getInformation());
        OwnStallController osc = new OwnStallController();
        ViewAsserts.assertOnScreen(osc.getWindow().getDecorView(),osc.findViewById(R.id.OwnStallLv));
    }
    //Tests UC 01.03.01
    //Depends on: UITesting.class
        //      : EachStallVw.class
    public void testDisplayOneItem(){
        UITesting ui = new UITesting();
        //Here will open the list of owned stalls and will hit one of the stalls specify
        ui.clickOwnStallButton();
        //ui clicks one stall and returns the intent.
            //This is similar to the onClick that will be implemented in the controller class for
            // for the listview
        //It will click some stall and let it open up a view.
        //I will check if that view is actually present.
       ui.clickOneStall(0);
        //This is the list that contains all the stalls owned
        EachStallVw esv = new EachStallVw();
        ViewAsserts.assertOnScreen(esv.getWindow().getDecorView(),
                esv.findViewById(R.id.eachStallLv));

    }
}
