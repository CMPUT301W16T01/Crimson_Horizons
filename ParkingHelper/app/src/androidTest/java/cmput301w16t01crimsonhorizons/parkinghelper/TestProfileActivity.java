package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

/**
 * Created by schuman on 3/8/16.
 */
public class TestProfileActivity extends ActivityInstrumentationTestCase2{
    public TestProfileActivity() {
        super(Profile.class);
    }

    @UiThreadTest
    public void testModifyAccount(){
        ElasticSearchForTest.addUser executeAdd = new ElasticSearchForTest.addUser();
        ElasticSearchCtr.addUser executeAdd2 = new ElasticSearchCtr.addUser();
        ElasticSearchCtr.deleteUser executeDelete = new ElasticSearchCtr.deleteUser();
        ElasticSearchCtr.deleteUser executeDelete2 = new ElasticSearchCtr.deleteUser();
        ElasticSearchCtr.verifyUserName executeVerify = new ElasticSearchCtr.verifyUserName();
        ElasticSearchCtr.GetAccount executeGet = new ElasticSearchCtr.GetAccount();

        Account account1 = new Account();
        account1.setEmail("__test1_");
        account1.setWorkPhone("1.1");
        account1.setCellPhone("1.2");
        account1.setId(null);

        Account account2 = new Account();
        account2.setEmail("__test2");
        account2.setWorkPhone("2.1");
        account2.setCellPhone("2.2");

        CurrentAccount.setAccount(account1);

        try{
        executeAdd.execute(account1).get();
        executeAdd2.execute(account2).get();

        while(account1.getId() == null){}
        assertTrue(executeVerify.execute("__test1_").get());

        Intent intent = new Intent();
        setActivityIntent(intent);
        Profile profile = (Profile) getActivity();
        EditText newEmail = (EditText) profile.findViewById(R.id.EmailET);
        newEmail.setText("__test1.1");


        profile.findViewById(R.id.SaveInProfileBtn).performClick();

        while(account1.getEmail() != "__test1.1"){ }
        assertFalse(executeVerify.execute("__test1_").get());
        assertTrue(executeVerify.execute("__test1.1").get());

       newEmail.setText("__test2");

        profile.findViewById(R.id.SaveInProfileBtn).performClick();

        assertTrue(executeVerify.execute("__test1.1").get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executeDelete.execute(account1);
            executeDelete2.execute(account2);
        }


    }

    @UiThreadTest
    public void testNotificationsButton(){
        Intent intent = new Intent();
        setActivityIntent(intent);
        Profile profile = (Profile) getActivity();

        Button notificationsButton = (Button) profile.findViewById(R.id.NotificationBtn);

        notificationsButton.performClick();

        View view = profile.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(view, profile.findViewById(R.id.NotificationsListView));

    }
}
