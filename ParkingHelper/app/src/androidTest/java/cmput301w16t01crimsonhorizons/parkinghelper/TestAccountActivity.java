package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import android.test.UiThreadTest;

import android.test.ViewAsserts;
import android.view.View;
import android.widget.ListView;

/**
 * Created by schuman on 3/10/16.
 */
public class TestAccountActivity extends ActivityInstrumentationTestCase2 {
    public TestAccountActivity() {
        super(AccountActivity.class);
    }


    @UiThreadTest
    public void testClickUsername(){
        //TODO:create test users and test stalls. Delete after use.
        Account account1 = new Account();
        account1.setEmail("__test1");
        account1.setWorkPhone("1.1");
        account1.setCellPhone("1.2");

        CurrentAccount.setAccount(account1);

        Intent intent = new Intent();
        setActivityIntent(intent);
        AccountActivity accountActivity = (AccountActivity) getActivity();
        ListView activities = (ListView) accountActivity.findViewById(R.id.OwnStalls);

        View stallElement = activities.findViewById(R.id.StallNameEditStallV);
        //CHANGE
        stallElement.performClick();

        View view = accountActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(view, accountActivity.findViewById(R.id.emailAddress));
    }
}
