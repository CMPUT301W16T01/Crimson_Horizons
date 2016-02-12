package cmput301w16t01.kliangtestcases;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kevin L on 2/11/2016.
 */
public class AllTestCases extends ActivityInstrumentationTestCase2 {
    public AllTestCases() {
        //We make a test with the starting point of app so have access to everything.
        super(MainActivity.class);
    }
    private static final char[] array= ("a b c d e f g h i j " +
            "k l m n o p q r s t u v w x y z 1 2 3 4 5 6 7 8 9 0").toCharArray();
    private String RandString(){
        Random rand = new Random();
        StringBuilder builder = new StringBuilder();
        int i=0;
        while (i<5){
            builder.append(Character.toChars(rand.nextInt(array.length)));
            i=i+1;
        }
        return builder.toString();
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
        assertEquals(OwnStalls.get(OwnStalls.size() - 1).getInformation(), "Hi");
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
        assertEquals(ReturnedLst.get(0), OwnStalls.get(0));
        assertEquals(ReturnedLst.get(0).getInformation(), OwnStalls.get(0).getInformation());
        OwnStallController osc = new OwnStallController();
        ViewAsserts.assertOnScreen(osc.getWindow().getDecorView(), osc.findViewById(R.id.OwnStallLv));
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
        assertEquals(stall, nStall);

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
        assertEquals(sizeBefore, sizeAfter + 1);

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
        assertEquals(stall1.getStatus(), "available");
        assertEquals(stall2.getStatus(), "Borrowed");
    }
    //Test: UC 03.01.01
    //Depends on: Profile.class
    //This tests whether it can generate a profile with proper username,and contact information.
    public void testProfileInformation(){
        Profile profile=new Profile();
        String cp = RandString();
        String wp = RandString();
        String Email = RandString();
        String un = RandString();
        profile.setCellphone(cp);
        profile.setWorkphone(wp);
        profile.setEmail(Email);
        profile.setUsername(un);
        assertEquals(profile.getCellphone(), cp);
        assertEquals(profile.getEmail(), Email);
        assertEquals(profile.getUsername(), un);
        assertEquals(profile.getWorkphone(), wp);
    }

    //Tests: 03.02.01
    //Depends on: Profile.class
    //It will create one user profile randomly, assert it is correctly made, change that profile
    //and will check that it is changed.
    public void testEditProfileInformation(){
        Profile profile = new Profile();
        String cp = RandString();
        String wp = RandString();
        String Email = RandString();
        String un = RandString();
        profile.setCellphone(cp);
        profile.setWorkphone(wp);
        profile.setEmail(Email);
        profile.setUsername(un);
        assertEquals(profile.getCellphone(),cp);
        assertEquals(profile.getEmail(),Email);
        assertEquals(profile.getUsername(),un);
        assertEquals(profile.getWorkphone(),wp);

        String ncp = RandString();
        String nwp = RandString();
        String nEmail = RandString();
        String nun = RandString();
        profile.setCellphone(ncp);
        profile.setWorkphone(nwp);
        profile.setEmail(nEmail);
        profile.setUsername(nun);
        assertEquals(profile.getCellphone(), ncp);
        assertEquals(profile.getEmail(),nEmail);
        assertEquals(profile.getUsername(),nun);
        assertEquals(profile.getWorkphone(),nwp);

        assertNotSame(profile.getCellphone(), cp);
        assertNotSame(profile.getWorkphone(),wp);
        assertNotSame(profile.getEmail(),Email);
        assertNotSame(profile.getUsername(),un);
    }

    //Tests: UC 03.03.01
    //Denpends on: Profile.class, UITesting.class
    //It will simulate a button press on a user name item, will assert that a new view is shown.
    // It will also check that the information is retrieved.
    public void testShowInformation(){
        UITesting ui = new UITesting();
        //This will clikc the search item button. Inside contains item information with username
        //Displayed
        ui.clickSearchItem();
        //This will clikc the username and will assert that a view is made
        ui.clickUsername();
        // This checks that the view that display all the user information is on screen
        ProfileView pv = new ProfileView();
        ViewAsserts.assertOnScreen(pv.getWindow().getDecorView(),
                pv.findViewById(R.id.profileInformation));
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
    // This test UC 05.01.01
    //Depends on: StallsForBid.class
    //This use cases assumes that there will be some stalls availabe for bidding
    // It just test whether using the "bid" method can succesfully update the status
    public void testBidding(){

        StallsForBid sfb = new StallsForBid();
        sfb.bid(100.00);
        assertEquals(sfb.getbidAmt(), 100);
        sfb.bid(200.0);
        assertEquals(sfb.getbidAmt(), 200);
    }
    //This test UC 05.02.01
    // Depends on: BidList.class
    //       : PendingBids.class
    // This test assumes that by using the adpater, the UI will be updated
    //It just test if calling for the get method can succesfully retrieve the updated list
    public void testViewPending(){
        BidList bidlst=new BidList();
        ArrayList<StallsForBid>AvailabeStalls=bidlst.getAvailableStalls();
        assertTrue(AvailabeStalls.getClass().equals(ArrayList.class));
        PendingBids pb = new PendingBids();
        ListView lv=(ListView)pb.findViewById(R.id.pendinglst);
        ViewAsserts.assertOnScreen(pb.getWindow().getDecorView(), lv);
    }
    //Tests: UC 05.03.01
    //Depends on: BidList.class
    //          : StallsForBid.class
    //          ; UITesting.class
    // Inside the Bidlist.class there will be a notifyOwner method.
    // This method is called anytime there is a change in bid amount.
    // This method will create a button for notification.
    // This method will see if that button exists, and if it can be clicked.
    public void testNotifyOwner(){
        BidList blst = new BidList();
        StallsForBid sfb = new StallsForBid();
        sfb.setOwner("hi");
        blst.notifyOwner(sfb);
        UITesting ui = new UITesting();
        ui.clickViewBids();
        ui.clickNotification();
        Notification note= new Notification();
        ViewAsserts.assertOnScreen(note.getWindow().getDecorView(),
                note.findViewById(R.id.notificatoinVw));
    }
    //Tests: UC US 06.01.01
    //Depends on: BorrowingVw.class
    //          : UITesting.class
    //          : StallList.class
    //          : Stalls.class
    //This tests will test whether or not the function get borrowed list returns the a list of item
    // It also tests if the view exists when the "Items you are borrowing" button
    public void testBorrowing(){
        StallList stalllst = new StallList();
        UITesting ui = new UITesting();
        ArrayList<Stalls> ReturnedBorrowed = stalllst.getBorrowingStalls();
        assertTrue(ReturnedBorrowed.getClass().equals(ArrayList.class));
        int size = ReturnedBorrowed.size();
        while (size-1>=0){
            assertTrue(ReturnedBorrowed.get(size-1).getOwner()!=null);
            size=size-1;
        }
        ui.clickBorrowLst();
        BorrowVw bv = new BorrowVw();
        ViewAsserts.assertOnScreen(bv.getWindow().getDecorView(), bv.findViewById(R.id.BorrowedVw));
    }
    //Tests: UC UC 06.02.01
    //Depends on: LenededVw.class
    //          :UITesting.class
    //          :Stalls.class
    public void testLended(){
        StallList stalllst = new StallList();
        UITesting ui = new UITesting();
        ArrayList<Stalls> ReturnedLended = stalllst.getLendedStalls();
        int size = ReturnedLended.size();
        while (size-1>=0){
            assertTrue(ReturnedLended.get(size-1).getBorrower()!=null);
            size=size-1;
        }
        assertTrue(ReturnedLended.getClass().equals(ArrayList.class));
        ui.clickLended();
        LendedVw lv = new LendedVw();
        ViewAsserts.assertOnScreen(lv.getWindow().getDecorView(), lv.findViewById(R.id.LendedVw));
    }


    //Tests: UC 07.01.01
    //Depends on: Stalls.class
    //          : StallLst.class
    //It will get an item add it to the borrowed list. It will then use the returnStall method
    // on that item. It will check if the status is changed.
    public void testReturnStall(){
        Stalls returnStall = new Stalls();
        StallList lst = new StallList();
        ArrayList<Stalls> BorrowedLst = lst.getBorrowingStalls();
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
