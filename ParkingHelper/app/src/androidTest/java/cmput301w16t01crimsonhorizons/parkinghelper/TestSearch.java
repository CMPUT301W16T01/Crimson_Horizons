package cmput301w16t01crimsonhorizons.parkinghelper;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.concurrent.ExecutionException;

/**
 * Created by schuman on 3/10/16.
 */
public class TestSearch extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public TestSearch() {
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

    /**
     * US 04.01.01
     * US 04.02.01
     */
    public void testSearchView() {
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test1");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));

        solo.clickOnView(solo.getView(R.id.AccountBtn));
        solo.clickOnView(solo.getView(R.id.AddBtn));
        solo.enterText((EditText) solo.getView(R.id.NamePrompET), "__test1");
        solo.enterText((EditText) solo.getView(R.id.DescriptionET), "__test1oneAAAAA");

        solo.clickOnView(solo.getView(R.id.AddInAddBtn));
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "123@123");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));

        solo.clickOnView(solo.getView(R.id.SearchBtn));
        ListView lvbefore = (ListView)solo.getView(R.id.ResultLv);
        View lvelementbefore = lvbefore.getChildAt(0);
        assertNotNull(lvelementbefore);
        solo.enterText((EditText) solo.getView(R.id.query), "__test1oneAAAAA");
        solo.clickOnView(solo.getView(R.id.SearchBtn));

        ListView listView = (ListView)solo.getView(R.id.ResultLv);
        View listelement = listView.getChildAt(0);
        TextView owner = (TextView)listelement.findViewById(R.id.StallNameEditStallV);
        TextView description = (TextView)listelement.findViewById(R.id.DescriptionEditStallV);
        assertEquals("description should match","__test1oneAAAAA",description.getText().toString());
        assertEquals("owner should match","__test1",owner.getText().toString());

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
