package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import android.test.UiThreadTest;

import android.test.ViewAsserts;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by schuman on 3/10/16.
 */
public class TestAccountActivity extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    ArrayList<Stalls> tempAry;
    Account account1;

    public TestAccountActivity() {
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

    public void testClickUsxername(){
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "123@123");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.AccountBtn));
        ListView lv = (ListView)solo.getView(R.id.OwnStalls);
        View listelement = lv.getChildAt(0);
        TextView username = (TextView) listelement.findViewById(R.id.StallNameEditStallV);
        solo.clickOnView(username);
        solo.assertCurrentActivity("Should be viewProfile", ViewProfile.class);
        solo.goBack();
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }

    public void testStallsList(){
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test1");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));


        solo.clickOnView(solo.getView(R.id.AccountBtn));
        solo.assertCurrentActivity("should be lists of own stalls", AccountActivity.class);

        solo.clickOnView(solo.getView(R.id.AddBtn));
        solo.enterText((EditText) solo.getView(R.id.NamePrompET), "__test1");
        solo.enterText((EditText) solo.getView(R.id.DescriptionET), "Test.");
        solo.clickOnView(solo.getView(R.id.AddInAddBtn));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
        String[]temp = new String[2];
        temp[0]="__test1";
        temp[1]="Owner";
        tempAry = new ArrayList<>();
        try {
            getStall.execute(temp);
            tempAry = getStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertTrue(tempAry.size() == 1);

        ElasticSearchCtr.DeleteStall deleteStall = new ElasticSearchCtr.DeleteStall();
        deleteStall.execute(tempAry.get(0));
        Boolean check = false;
        try {
            check = deleteStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertTrue("didn't delete Stall", check);
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }

    /**
     * US 01.03.01
     * Test that an object is displayed in account.
     */
    public void testDisplayStatus() {
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "123@123");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.AccountBtn));
        solo.clickInList(0);
        solo.assertCurrentActivity("should be editing stalls", EditStall.class);
        solo.goBack();
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }
}
