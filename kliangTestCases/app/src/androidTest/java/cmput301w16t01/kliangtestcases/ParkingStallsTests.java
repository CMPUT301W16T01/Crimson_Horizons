package cmput301w16t01.kliangtestcases;

import android.app.Application;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;

import java.util.ArrayList;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ParkingStallsTests extends ActivityInstrumentationTestCase2 {
    public ParkingStallsTests() {
        //We make a test with the starting point of app so have access to everything.
        super(ParkingStalls.class);
    }
    //Test use cases US 01.04.01
    public void TestEdit(){
        ArrayList<Stalls> OwnStalls= new ArrayList<>();
        StallList stallList = new StallList();
        OwnStalls=stallList.ObtainOwnStalls();
        Stalls stall = OwnStalls.get(0);
        Stalls nStall= new Stalls();
        assertNotSame(stall,nStall);
        stall.editOwnStall(stall, nStall);
        assertEquals(stall,nStall);



    }
    // Test use cases US 01.05.01
    public void TestdeleteStall(){
        ArrayList<Stalls>OwnStalls= new ArrayList<>();
        StallList stalllist = new StallList();
        OwnStalls=stalllist.ObtainOwnStalls();
        Stalls stall =OwnStalls.get(0);
        int sizeBefore=OwnStalls.size();
        stalllist.deleteStalls(stall);
        int sizeAfter=OwnStalls.size();
        assertEquals(sizeBefore,sizeAfter+1);

    }
}
