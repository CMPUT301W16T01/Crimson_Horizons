package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

/**
 * Created by schuman on 3/8/16.
 */
public class TestProfileActivity extends ActivityInstrumentationTestCase2{
    public TestProfileActivity() {
        super(Profile.class);
    }

    protected void setUp(){

    }

    protected void tearDown(){
        ElasticSearchForTest.deleteUser executeDelete = new ElasticSearchForTest.deleteUser();
        ElasticSearchForTest.deleteUser executeDelete2 = new ElasticSearchForTest.deleteUser();
        ElasticSearchForTest.GetAccount executeGet = new ElasticSearchForTest.GetAccount();
        ElasticSearchForTest.GetAccount executeGet2 = new ElasticSearchForTest.GetAccount();

        Account account1 = null;
        Account account2 = null;

        try {
            account1 = executeGet.execute("__test1_").get();
            account2 = executeGet.execute("__test2").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executeDelete.execute(account1);
        executeDelete2.execute(account2);
    }


    @UiThreadTest
    public void testModifyAccount(){
        ElasticSearchForTest.addUser executeAdd = new ElasticSearchForTest.addUser();

        ElasticSearchForTest.addUser executeAdd2 = new ElasticSearchForTest.addUser();
        ElasticSearchForTest.deleteUser executeDelete = new ElasticSearchForTest.deleteUser();
        ElasticSearchForTest.deleteUser executeDelete2 = new ElasticSearchForTest.deleteUser();
        ElasticSearchForTest.verifyUserName executeVerify = new ElasticSearchForTest.verifyUserName();
        ElasticSearchForTest.verifyUserName executeVerify2 = new ElasticSearchForTest.verifyUserName();
        ElasticSearchForTest.GetAccount executeGet = new ElasticSearchForTest.GetAccount();

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

        //netowrk syncronization
        while(account1.getId().equals(null)){}
        assertTrue("Account 1 should of been created",executeVerify.execute("__test1_").get());


        Intent intent = new Intent();
        setActivityIntent(intent);
        Profile profile = (Profile) getActivity();

        EditText newEmail = (EditText) profile.findViewById(R.id.EmailET);
        newEmail.setText("__test1.1");


        profile.save(new ElasticSearchForTest.verifyUserName(), new ElasticSearchForTest.updateUser());
        String temp = account1.getEmail();
        Boolean status;

            while (!account1.getEmail().matches("__test1.1")) {
                wait(100);
            }

       newEmail.setText("__test2");

        profile.findViewById(R.id.SaveInProfileBtn).performClick();


        assertTrue("Account 1 should not have changed email addresses",executeVerify.execute("__test1.1").get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executeDelete.execute(account1);
            executeDelete2.execute(account2);
        }


    }


   /* @UiThreadTest

    public void testNotificationsButton(){
        Intent intent = new Intent();
        setActivityIntent(intent);
        Profile profile = (Profile) getActivity();

        Button notificationsButton = (Button) profile.findViewById(R.id.NotificationBtn);

        notificationsButton.performClick();

        View view = profile.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(view, profile.findViewById(R.id.NotificationsListView));

    }*/
}
