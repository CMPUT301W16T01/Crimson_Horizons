package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.EditText;

import com.robotium.solo.Solo;

import java.util.concurrent.ExecutionException;

/**
 * Created by schuman on 3/8/16.
 */
public class TestProfileActivity extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    public TestProfileActivity() {
        super(WelcomeActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }


    public void testModifyAccount() {
        ElasticSearchCtr.addUser executeAdd = new ElasticSearchCtr.addUser();
        ElasticSearchCtr.addUser executeAdd2 = new ElasticSearchCtr.addUser();
        ElasticSearchCtr.deleteUser executeDelete = new ElasticSearchCtr.deleteUser();
        ElasticSearchCtr.deleteUser executeDelete2 = new ElasticSearchCtr.deleteUser();

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

        try {
            executeAdd.execute(account1).get();
            executeAdd2.execute(account2).get();

            Thread.sleep(1000);

            solo.clickOnView(solo.getView(R.id.LoginButton));
            solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test1_");
            solo.clickOnView(solo.getView(R.id.email_sign_in_button));
            solo.assertCurrentActivity("should be in homepage", HomepageActivity.class);
            solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

            solo.clickOnView(solo.getView(R.id.LoginButton));
            solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test2");
            solo.clickOnView(solo.getView(R.id.email_sign_in_button));
            solo.assertCurrentActivity("should be in homepage", HomepageActivity.class);
            solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

            solo.clickOnView(solo.getView(R.id.LoginButton));
            solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test1_");
            solo.clickOnView(solo.getView(R.id.email_sign_in_button));
            solo.clickOnView(solo.getView(R.id.AccountBtn));
            solo.clickOnView(solo.getView(R.id.ProfileBtn));
            EditText editText = (EditText)solo.getView(R.id.CellPhoneET);
            assertEquals("values", "1.2", editText.getText().toString());
            solo.enterText((EditText) solo.getView(R.id.CellPhoneET), "123");
            solo.clickOnView(solo.getView(R.id.SaveInProfileBtn));

            solo.clickOnView(solo.getView(R.id.ProfileBtn));
            EditText editText_changed = (EditText)solo.getView(R.id.CellPhoneET);
            assertEquals("values changed", "1.2123", editText_changed.getText().toString());

            solo.goBack();
            solo.goBack();
            solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

            solo.clickOnView(solo.getView(R.id.LoginButton));
            solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test2");
            solo.clickOnView(solo.getView(R.id.email_sign_in_button));
            solo.clickOnView(solo.getView(R.id.AccountBtn));
            solo.clickOnView(solo.getView(R.id.ProfileBtn));
            EditText editText_a2 = (EditText)solo.getView(R.id.CellPhoneET);
            assertEquals("values unchanged", "2.2", editText_a2.getText().toString());

            solo.goBack();
            solo.goBack();
            solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executeDelete.execute(account1);
            executeDelete2.execute(account2);
        }

    }


}
