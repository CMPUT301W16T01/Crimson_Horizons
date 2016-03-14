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
 * Created by jpeard on 3/13/16.
 */

public class TestAddStall extends ActivityInstrumentationTestCase2 {
    public TestAddStall() {
        super(AddStall.class);
    }

    @UiThreadTest
    public void testAddStall(){
        Account account1 = new Account();
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

        ElasticSearchCtr.GetBidStall getBidStall = new ElasticSearchCtr.GetBidStall();
        String[]temp = new String[4];
        temp[0]="__test1";
        temp[1]="Owner";
        temp[2]="Test.";
        temp[3]="Description";

        ArrayList<Stalls>tempAry = new ArrayList<>();
        try {
            getBidStall.execute(temp);
            tempAry = getBidStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertTrue(tempAry.get(0).getOwner() == "__test1");
        assertTrue(tempAry.get(0).getDescription() == "Test.");

        ElasticSearchCtr.DeleteStall deleteStall = new ElasticSearchCtr.DeleteStall();
        deleteStall.doInBackground(tempAry.get(0));

    }

}
