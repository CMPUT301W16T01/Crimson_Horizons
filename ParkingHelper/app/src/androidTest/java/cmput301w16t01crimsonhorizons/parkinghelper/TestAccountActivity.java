package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import android.test.UiThreadTest;

import android.test.ViewAsserts;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by schuman on 3/10/16.
 */
public class TestAccountActivity extends ActivityInstrumentationTestCase2 {
    ArrayList<Stalls> tempAry;
    Account account1;

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

    @UiThreadTest
    public void testStallsList(){
        account1 = new Account();
        account1.setEmail("__test1");
        account1.setWorkPhone("1.1");
        account1.setCellPhone("1.2");
        ElasticSearchCtr.addUser adduser= new ElasticSearchCtr.addUser();
        adduser.doInBackground(account1);

        Intent intent = new Intent();
        setActivityIntent(intent);
        AddStall addStall = (AddStall) getActivity();
        TextView activities1 = (TextView) addStall.findViewById(R.id.NamePrompET);
        TextView activities2 = (TextView) addStall.findViewById(R.id.DescriptionET);

        activities1.setText("__test1");
        activities2.setText("Test.");

        View addButton = addStall.findViewById(R.id.AddInAddBtn);
        addButton.performClick();

        ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
        String[]temp = new String[4];
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

        AccountActivity accountActivity = (AccountActivity) getActivity();
        accountActivity.update();

        View view = accountActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(view, accountActivity.findViewById(R.id.OwnStalls));
    }

    /**
     * US 01.03.01
     * Test that an object is displayed in account.
     */
    public void testDisplayStatus() {
        AccountActivity accountActivity = (AccountActivity) getActivity();
        accountActivity.update();

        View view = accountActivity.getWindow().getDecorView();
        ViewAsserts.assertOnScreen(view, accountActivity.findViewById(R.id.OwnStalls));
        ListView lv = (ListView)accountActivity.findViewById(R.id.OwnStalls);
        assertTrue(lv.getAdapter().getCount() != 0);
    }

    @Override
    protected void tearDown() throws Exception {
        ElasticSearchCtr.DeleteStall deleteStall = new ElasticSearchCtr.DeleteStall();
        deleteStall.execute(tempAry.get(0));
        Boolean check = false;
        check = deleteStall.get();
        assertTrue("didn't delete Stall", check);
        Thread.sleep(1000);
        ElasticSearchCtr.deleteUser deleteUser = new ElasticSearchCtr.deleteUser();
        deleteUser.execute(account1);
        Boolean check2 = false;
        check2 = deleteUser.get();
        assertTrue("didn't delete user", check2);
    }
}
