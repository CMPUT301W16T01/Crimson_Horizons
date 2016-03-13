package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
<<<<<<< Updated upstream
import android.test.UiThreadTest;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
=======
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
>>>>>>> Stashed changes

import java.util.concurrent.ExecutionException;

/**
 * Created by schuman on 3/8/16.
 */
public class TestProfileActivity extends ActivityInstrumentationTestCase2{
    public TestProfileActivity() {
        super(Profile.class);
    }
<<<<<<< Updated upstream
    @UiThreadTest
    public void testModifyAccount(){
        ElasticSearchForTest.addUser executeAdd = new ElasticSearchForTest.addUser();
=======


    public void testModifyAccount(){
        ElasticSearchCtr.addUser executeAdd = new ElasticSearchCtr.addUser();
>>>>>>> Stashed changes
        ElasticSearchCtr.addUser executeAdd2 = new ElasticSearchCtr.addUser();
        ElasticSearchCtr.deleteUser executeDelete = new ElasticSearchCtr.deleteUser();
        ElasticSearchCtr.deleteUser executeDelete2 = new ElasticSearchCtr.deleteUser();
        ElasticSearchCtr.verifyUserName executeVerify = new ElasticSearchCtr.verifyUserName();
        ElasticSearchCtr.GetAccount executeGet = new ElasticSearchCtr.GetAccount();

        Account account1 = new Account();
<<<<<<< Updated upstream

        account1.setEmail("__test1_");
        account1.setWorkPhone("1.1");
        account1.setCellPhone("1.2");
        account1.setId(null);
=======
        account1.setEmail("__test1");
        account1.setWorkPhone("1.1");
        account1.setCellPhone("1.2");
>>>>>>> Stashed changes

        Account account2 = new Account();
        account2.setEmail("__test2");
        account2.setWorkPhone("2.1");
        account2.setCellPhone("2.2");

<<<<<<< Updated upstream
        CurrentAccount.setAccount(account1);

        try{
        executeAdd.execute(account1).get();
        executeAdd2.execute(account2).get();

        while(account1.getId() == null){}
        assertTrue(executeVerify.execute("__test1_").get());
=======
        try{
        executeAdd.execute(account1).get();
        executeAdd2.execute(account2).get();
        assertTrue(executeVerify.execute("__test1").get());

        CurrentAccount.setAccount(account1);
        account1.setEmail("__test1.1");
>>>>>>> Stashed changes

        Intent intent = new Intent();
        setActivityIntent(intent);
        Profile profile = (Profile) getActivity();
<<<<<<< Updated upstream
        EditText newEmail = (EditText) profile.findViewById(R.id.EmailET);
        newEmail.setText("__test1.1");


        profile.findViewById(R.id.SaveInProfileBtn).performClick();

        while(account1.getEmail() != "__test1.1"){ }
        assertFalse(executeVerify.execute("__test1_").get());
        assertTrue(executeVerify.execute("__test1.1").get());

       newEmail.setText("__test2");

        profile.findViewById(R.id.SaveInProfileBtn).performClick();

=======

        profile.findViewById(R.id.SaveInProfileBtn).performClick();

        assertFalse(executeVerify.execute("__test1").get());
        assertTrue(executeVerify.execute("__test1.1").get());

        account1.setEmail("__test2");

        intent = new Intent();
        setActivityIntent(intent);
        profile = (Profile) getActivity();

        profile.findViewById(R.id.SaveInProfileBtn).performClick();

        assertFalse(account1.getEmail() == "__test2");
        assertTrue(executeVerify.execute("__test2").get());
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    @UiThreadTest
=======
>>>>>>> Stashed changes
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
