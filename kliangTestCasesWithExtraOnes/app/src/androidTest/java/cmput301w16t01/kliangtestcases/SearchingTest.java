package cmput301w16t01.kliangtestcases;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;

import java.util.ArrayList;

/**
 * Created by Kevin L on 2/11/2016.
 */
public class SearchingTest extends ActivityInstrumentationTestCase2 {
    public SearchingTest() {
        //We make a test with the starting point of app so have access to everything.
        super(BidList.class);
    }

    // Test: UC 04.01.01
    //Depends on: BidList.class and StallsForBid.class
    //This test will use the searchDescription in BidList returns a list
    // of all items that are available including items with bids on it.
    //It will then traverse the list 1 item at a time and ensure that in the description the
    // Keyword exists.
    public void testSearchDescription(){
        ArrayList<StallsForBid> sfbList = new ArrayList<>();
        BidList bl = new BidList();
        sfbList=bl.SearchDescription("Here");
        int size = sfbList.size();
        while (size-1>=0){
            StallsForBid sfb = sfbList.get(size - 1);
            String Description = sfb.getDescription();
            assertTrue(Description.contains("Here"));
            size = size - 1;
        }
    }

    //Test: UC 04.02.01
    //Depends on: BidList.class
    //This will use the SearchDescription method in BidList which returns a lits of items that
    // are available (with or without bids). It will then assert that a view is present.
    // The view will show all relevant information.
    public void testDisplaySearchedResult(){
        ArrayList<StallsForBid> sfbList = new ArrayList<>();
        BidList bl = new BidList();
        sfbList=bl.SearchDescription("Here");
        SearchResultVw srv = new SearchResultVw();
        ViewAsserts.assertOnScreen(srv.getWindow().getDecorView(),
                srv.findViewById(R.id.SearchResultVw));
    }
}
