package cmput301w16t01crimsonhorizons.parkinghelper;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

/**
 * Created by Kevin L on 3/11/2016.
 */
public class TestBorrowing extends ActivityInstrumentationTestCase2<HomepageActivity> {
    public TestBorrowing() {
        super(HomepageActivity.class);
    }
    /**
     * US 06.01.01
     * Test if the user can see a list of borrowed things.
     */
    public void testBorrowingStall(){
        HomepageActivity activity = getActivity();
        Button borrowed = (Button) activity.findViewById(R.id.BorrowingBtn);
        borrowed.performClick();
        BorrowedStalls borrowedStalls = new BorrowedStalls();
        ListView lvBorrowed = (ListView)borrowedStalls.findViewById(R.id.borrowedStallsList);
        ViewAsserts.assertOnScreen(borrowedStalls.getWindow().getDecorView(), lvBorrowed);
    }

    /**
     * US 06.01.01
     * Just like above but does for owner and clicks on the lending stall button.
     */
    public void testLendingStall(){
        HomepageActivity activity = getActivity();
        Button lending = (Button) activity.findViewById(R.id.LendingBtn);
        lending.performClick();
        LendingStalls lendedStalls = new LendingStalls();
        ListView lvLended = (ListView)lendedStalls.findViewById(R.id.borrowedStallsList);
        ViewAsserts.assertOnScreen(lendedStalls.getWindow().getDecorView(),lvLended);
    }
}
