package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by jpeard on 3/13/16.
 */

public class TestOwnStallsWithBids extends ActivityInstrumentationTestCase2 {
    ArrayList<Stalls> tempAry;
    Account account1;

    public TestOwnStallsWithBids() { super(OwnStallsWithBidsActivity.class); }

    @UiThreadTest
    public void testStallsList(){
        account1 = new Account();
        account1.setEmail("__test1");
        account1.setWorkPhone("1.1");
        account1.setCellPhone("1.2");
        ElasticSearchCtr.addUser adduser= new ElasticSearchCtr.addUser();
        adduser.execute(account1);
        Boolean check=false;
        try {
            check = adduser.get();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        setActivityIntent(intent);
        AddStall addStall = new AddStall();
        TextView activities1 = (TextView) addStall.findViewById(R.id.NamePrompET);
        TextView activities2 = (TextView) addStall.findViewById(R.id.DescriptionET);

        activities1.setText("__test1");
        activities2.setText("Test.");

        View addButton = addStall.findViewById(R.id.AddInAddBtn);
        addButton.performClick();

        ElasticSearchCtr.GetBidStall getBidStall = new ElasticSearchCtr.GetBidStall();
        String[]temp = new String[4];
        temp[0]="__test1";
        temp[1]="Owner";
        temp[2]="0.0";
        temp[3]="BidAmt";

        tempAry = new ArrayList<>();
        try {
            getBidStall.execute(temp);
            tempAry = getBidStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        OwnStallsWithBidsActivity stallsWithBidsActivity = (OwnStallsWithBidsActivity) getActivity();
        stallsWithBidsActivity.update();

        View view = stallsWithBidsActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(view, stallsWithBidsActivity.findViewById(R.id.StallsWithBidsTitle));
    }

    @Override
    protected void tearDown() throws Exception {
        ElasticSearchCtr.DeleteStall deleteStall = new ElasticSearchCtr.DeleteStall();
        deleteStall.execute(tempAry.get(0));

        ElasticSearchCtr.deleteUser deleteUser = new ElasticSearchCtr.deleteUser();
        deleteUser.execute(account1);
    }

}

