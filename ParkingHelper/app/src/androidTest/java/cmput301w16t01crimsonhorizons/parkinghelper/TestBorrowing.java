package cmput301w16t01crimsonhorizons.parkinghelper;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ListView;
import java.util.List;
import com.robotium.solo.Solo;

/**
 * Created by Kevin L on 3/11/2016.
 */
public class TestBorrowing extends ActivityInstrumentationTestCase2<HomepageActivity> {
    private Solo solo;
    public TestBorrowing() {
        super(HomepageActivity.class);
    }
    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();
    }
    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
    /**
     * US 06.01.01
     * Test if the user can see a list of borrowed things.
     */
    public void testBorrowingStall(){
        solo.unlockScreen();
        solo.clickOnView(solo.getView(R.id.BorrowingBtn));
        solo.assertCurrentActivity("Expected borrwiong stall activity",BorrowStallActivity.class);
    }

/*    *
     * US 06.01.01
     * Just like above but does for owner and clicks on the lending stall button.
     */
    public void testLendingStall(){
        solo.unlockScreen();
        solo.clickOnView(solo.getView(R.id.LendingBtn));
        solo.assertCurrentActivity("Expected borrwiong stall activity",LendingStalls.class);
    }
}
