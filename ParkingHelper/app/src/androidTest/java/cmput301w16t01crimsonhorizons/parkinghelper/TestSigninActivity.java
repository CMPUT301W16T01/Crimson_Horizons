package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

/**
 * Created by schuman on 3/8/16.
 */
public class TestSigninActivity extends ActivityInstrumentationTestCase2<SigninActivity>{
    public TestSigninActivity() {
        super(SigninActivity.class);
    }


    public void testModifyAccount(){
        ElasticSearchCtr.addUser executeAdd = new ElasticSearchCtr.addUser();
        ElasticSearchCtr.deleteUser executeDelete = new ElasticSearchCtr.deleteUser();

        Account account1 = new Account();
        account1.setEmail("__test1");
        account1.setWorkPhone("1.1");
        account1.setCellPhone("1.2");

        Account account2 = new Account();
        account2.setEmail("__test2");
        account2.setWorkPhone("2.1");
        account2.setCellPhone("2.2");

        Intent intent = new Intent();
        setActivityIntent(intent);

        EditText newEmail;
        //change its values via the profile activity
        //test
        //try to change it to a pre-existing account
        //test

    }
}
