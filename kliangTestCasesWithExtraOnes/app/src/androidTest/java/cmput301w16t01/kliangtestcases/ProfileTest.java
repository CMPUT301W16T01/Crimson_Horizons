package cmput301w16t01.kliangtestcases;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kevin L on 2/11/2016.
 */
public class ProfileTest extends ActivityInstrumentationTestCase2 {
    public ProfileTest() {
        //We make a test with the starting point of app so have access to everything.
        super(Profile.class);
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
        assertEquals(profile.getCellphone(),cp);
        assertEquals(profile.getEmail(),Email);
        assertEquals(profile.getUsername(),un);
        assertEquals(profile.getWorkphone(),wp);
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
        ViewAsserts.assertOnScreen(bv.getWindow().getDecorView(),bv.findViewById(R.id.BorrowedVw));
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
        ViewAsserts.assertOnScreen(lv.getWindow().getDecorView(),lv.findViewById(R.id.LendedVw));
    }
}
