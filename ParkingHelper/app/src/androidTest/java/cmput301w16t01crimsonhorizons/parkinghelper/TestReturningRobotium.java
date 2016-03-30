package cmput301w16t01crimsonhorizons.parkinghelper;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 3/30/2016.
 */
public class TestReturningRobotium extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public TestReturningRobotium() {
        super(WelcomeActivity.class);
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

    public void testReturningButton(){
        Stalls s1 = new Stalls();
        s1.setOwner("__test1");
        s1.setBorrower("K");
        s1.setStatus("Borrowed");
        s1.setDescription("Kborrow__test1");
        ElasticSearchCtr.MakeStall makeStall = new ElasticSearchCtr.MakeStall();
        makeStall.execute(s1);
        try {
            makeStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "K");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.BorrowingBtn));
        ListView lv = (ListView) solo.getView(R.id.borrowedStallsList);
        View element = lv.getChildAt(0);
        TextView description = (TextView)element.findViewById(R.id.DescriptionBorrowedStall);
        assertEquals("description should be same","Kborrow__test1",description.getText().toString());
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test1");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));

        solo.clickOnView(solo.getView(R.id.LendingBtn));
        solo.assertCurrentActivity("should be in lending class", LendingStalls.class);
        solo.clickInList(0);
        solo.assertCurrentActivity("should be in returning stalls", ReturningStallActivity.class);
        solo.clickOnView(solo.getView(R.id.returnStallBtn));
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "K");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.BorrowingBtn));
        ListView lv2 = (ListView) solo.getView(R.id.borrowedStallsList);
        View element2 = lv2.getChildAt(0);
        assertNull(element2);
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test1");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));

        solo.clickOnView(solo.getView(R.id.AccountBtn));
        solo.clickInList(0);
        solo.assertCurrentActivity("should be in edit stall", EditStall.class);
        solo.clickOnView(solo.getView(R.id.EditStallDeleteBtn));

        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }

}
