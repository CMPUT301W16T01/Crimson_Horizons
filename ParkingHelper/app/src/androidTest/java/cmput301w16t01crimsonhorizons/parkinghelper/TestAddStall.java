package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by jpeard on 3/13/16.
 * US 01.01.01: It tests to see if owner can add things and store them
 * US 01.02.01: It tests to see if the owner can retrieve everything they own;;
 */

public class TestAddStall extends ActivityInstrumentationTestCase2 {
    ArrayList<Stalls> tempAry;
    Account account1;
    private Solo solo;

    public TestAddStall() {
        super(AddStall.class);
    }

    @UiThreadTest
    public void testAddStall(){
        account1 = new Account();
        account1.setEmail("testing");
        account1.setWorkPhone("1.1");
        account1.setCellPhone("1.2");
        ElasticSearchCtr.addUser adduser= new ElasticSearchCtr.addUser();
        CurrentAccount.setAccount(account1);
        adduser.execute(account1);
        Boolean check = false;
        try {
            check = adduser.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertTrue("adduser didn't execute",check);
        Intent intent = new Intent();
        setActivityIntent(intent);
        AddStall addStall = (AddStall) getActivity();
        TextView activities1 = (TextView) addStall.findViewById(R.id.NamePrompET);
        TextView activities2 = (TextView) addStall.findViewById(R.id.DescriptionET);

        activities1.setText("testing");
        activities2.setText("Test.");

        View addButton = addStall.findViewById(R.id.AddInAddBtn);
        addButton.performClick();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
        String[]temp = new String[2];
        temp[0]="testing";
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
        assertTrue(tempAry.size()==1);
        assertTrue(tempAry.get(0).getOwner().equals("testing"));
        assertTrue(tempAry.get(0).getDescription().equals("Test."));

    }

    @Override
    protected void tearDown() throws Exception {
        ElasticSearchCtr.DeleteStall deleteStall = new ElasticSearchCtr.DeleteStall();
        deleteStall.execute(tempAry.get(0));
        Boolean check = false;
        check = deleteStall.get();
        assertTrue("didn't delete Stall", check);
        Thread.sleep(1000);
        /*ElasticSearchCtr.deleteUser deleteUser = new ElasticSearchCtr.deleteUser();
        deleteUser.execute(account1);
        Boolean check2 = false;
        check2 = deleteUser.get();
        assertTrue("didn't delete user",check2);*/
    }

}
