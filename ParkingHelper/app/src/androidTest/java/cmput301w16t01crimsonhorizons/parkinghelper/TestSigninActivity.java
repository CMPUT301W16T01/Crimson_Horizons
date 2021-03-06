package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Created by schuman on 3/10/16.
 */
public class TestSigninActivity extends ActivityInstrumentationTestCase2 {
    public TestSigninActivity() {
        super(SigninActivity.class);
    }

    protected void setUp(){

    }

    protected  void tearDown(){
        ElasticSearchCtr.GetAccount executeGet = new ElasticSearchCtr.GetAccount();
        ElasticSearchCtr.deleteUser executeDelete = new ElasticSearchCtr.deleteUser();
        try {
            executeDelete.execute(executeGet.execute("__test1").get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * test for unique username
     * US 03.01.01
     */
    @UiThreadTest
    public void testSignup() {
        ElasticSearchCtr.verifyUserName executeVerify = new ElasticSearchCtr.verifyUserName();
        ElasticSearchCtr.GetAccount executeGet = new ElasticSearchCtr.GetAccount();
        ElasticSearchCtr.GetAccount executeGet2 = new ElasticSearchCtr.GetAccount();
        ElasticSearchCtr.deleteUser executeDelete2 = new ElasticSearchCtr.deleteUser();

        Intent intent = new Intent();
        setActivityIntent(intent);
        SigninActivity signinActivity = (SigninActivity) getActivity();

        EditText EmailText = (EditText) signinActivity.findViewById(R.id.EmailEditTxt);
        EditText WorkText = (EditText) signinActivity.findViewById(R.id.HomePhonEditTxt);

        EditText CellText = (EditText) signinActivity.findViewById(R.id.CellPhonEditTxt);

        EmailText.setText("__test1");
        WorkText.setText("test1.1");
        CellText.setText("test1.2");

        Button createButton = (Button) signinActivity.findViewById(R.id.CreateAccountBtn);

        createButton.performClick();

        try {
            Thread.sleep(1000);
            assertTrue("A new user should have been created",executeVerify.execute("__test1").get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        intent = new Intent();
        setActivityIntent(intent);
        signinActivity = (SigninActivity) getActivity();

        EmailText = (EditText) signinActivity.findViewById(R.id.EmailEditTxt);

        WorkText = (EditText) signinActivity.findViewById(R.id.HomePhonEditTxt);

        CellText = (EditText) signinActivity.findViewById(R.id.CellPhonEditTxt);
        EmailText.setText("__test1");
        WorkText.setText("test2.1");
        CellText.setText("test2.2");

        try {
            assertFalse("The new account should be rejected and the old account should remain", executeGet.execute("__test1").get().getCellPhone().equals("test2.2"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            try {
                Account a = executeGet2.execute("__test1").get();
                executeDelete2.execute(a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
