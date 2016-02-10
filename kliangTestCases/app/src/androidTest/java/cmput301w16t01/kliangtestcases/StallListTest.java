package cmput301w16t01.kliangtestcases;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

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
    public void testdeleteStall(){
        StallList OwnStalls = new StallList();
        Stalls nstall = new Stalls();
        OwnStalls.addStall(nstall);;
        int sizeBefore=OwnStalls.size();
        OwnStalls.deleteStalls(0);
        int sizeAfter=OwnStalls.size();
        assertEquals(sizeBefore,sizeAfter+1);

    }

    public void testAddStall(){

    }
}
