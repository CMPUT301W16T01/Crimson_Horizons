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

    @UiThreadTest
    public void testSignup() {
        ElasticSearchCtr.verifyUserName executeVerify = new ElasticSearchCtr.verifyUserName();
        ElasticSearchCtr.GetAccount executeGet = new ElasticSearchCtr.GetAccount();
        ElasticSearchCtr.deleteUser executeDelete = new ElasticSearchCtr.deleteUser();
        ElasticSearchCtr.deleteUser executeDelete2 = new ElasticSearchCtr.deleteUser();

        Intent intent = new Intent();
        setActivityIntent(intent);
        SigninActivity signinActivity = (SigninActivity) getActivity();

        EditText EmailText = (EditText) signinActivity.findViewById(R.id.EmailEditTxt);
        TextView WorkText = (TextView) signinActivity.findViewById(R.id.WorkPhoneText);
        EditText CellText = (EditText) signinActivity.findViewById(R.id.CellPhonEditTxt);

        EmailText.setText("__test1");
        WorkText.setText("test1.1");
        CellText.setText("test1.2");

        Button createButton = (Button) signinActivity.findViewById(R.id.CreateAccountBtn);

        createButton.performClick();

        try {
            assertTrue(executeVerify.execute("__test1").get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        intent = new Intent();
        setActivityIntent(intent);
        signinActivity = (SigninActivity) getActivity();

        EmailText = (EditText) signinActivity.findViewById(R.id.EmailEditTxt);
        WorkText = (EditText) signinActivity.findViewById(R.id.WorkPhoneText);
        CellText = (EditText) signinActivity.findViewById(R.id.CellPhonEditTxt);

        EmailText.setText("__test1");
        WorkText.setText("test2.1");
        CellText.setText("test2.2");

        try {
            assertFalse(executeGet.execute("__test1").get().getCellPhone() == "test2.2");
            executeDelete.execute(executeGet.execute("__test1").get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
