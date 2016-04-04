package cmput301w16t01crimsonhorizons.parkinghelper;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

/**
 * Created by kliang on 4/4/16.
 */
public class TestReview extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public TestReview() {
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

    public void testReview(){
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "robo");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));

        solo.clickOnView(solo.getView(R.id.SearchBtn));
        solo.clickInList(0);
        solo.clickOnView(solo.getView(R.id.ReviewBtn));
        solo.assertCurrentActivity("should be able to see review class", ReviewsActivity.class);
        solo.clickOnView(solo.getView(R.id.addReveiwBtn));
        solo.assertCurrentActivity("Should be in place to add reviews", NewReview.class);
        solo.goBack();
        solo.goBack();
        solo.goBack();
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }
}
