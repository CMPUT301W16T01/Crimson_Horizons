package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Created by schuman on 3/10/16.
 */
public class TestSearch extends ActivityInstrumentationTestCase2 {
    public TestSearch() {
        super(Search.class);
    }


    protected void setUp(){
        ElasticSearchForTest.addUser executeAdd = new ElasticSearchForTest.addUser();

        Account account1 = new Account();
        account1.setEmail("__test1");
        account1.setWorkPhone("1.1");
        account1.setCellPhone("1.2");

        Account account2 = new Account();
        account2.setEmail("__test2");
        account2.setWorkPhone("2.1");
        account2.setCellPhone("2.2");

        Stalls stalls = new Stalls();
        stalls.setOwner(account1.getEmail());
        stalls.setStatus("available");
        stalls.setDescription("A test stall");

        CurrentAccount.setAccount(account2);
    }

    protected void tearDown(){
        ElasticSearchForTest.deleteUser executeDelete = new ElasticSearchForTest.deleteUser();
        ElasticSearchForTest.deleteUser executeDelete2 = new ElasticSearchForTest.deleteUser();
        ElasticSearchForTest.DeleteStall executeDelete3 = new ElasticSearchForTest.DeleteStall();
        ElasticSearchForTest.GetAccount executeGet = new ElasticSearchForTest.GetAccount();
        ElasticSearchForTest.GetAccount executeGet2 = new ElasticSearchForTest.GetAccount();
        ElasticSearchForTest.GetStall executeGet3 = new ElasticSearchForTest.GetStall();

        Account account1;
        Account account2;
        Stalls stalls;
        String[] search = new String[2];
         search[0] =  "Description";
        search[1] = "A stest stall";

        try {
            account1 = executeGet.execute("__test1").get();
            account2 = executeGet2.execute("__test2").get();
            //stalls = executeGet3.execute(search).get().get(0);


            executeDelete.execute(account1);
            executeDelete2.execute(account2);
            //executeDelete3.execute(stalls);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @UiThreadTest
    public void testClickUserName() {
        final Search search = (Search) getActivity();
        TextView textView = (TextView) search.findViewById(R.id.StallNameEditStallV);
        textView.setText("__test1");
        ClickUserName clickUserName = new ClickUserName();
        Intent intent = clickUserName.clickUserName(search, textView, new ElasticSearchForTest.GetAccount());
        String x = intent.getComponent().getClassName();
        ViewProfile viewProfile = (ViewProfile) new ViewProfile();
        String y = viewProfile.getClass().toString();
        assertTrue("The ViewProfile intent should have been returned", viewProfile.getClass().toString().contains(intent.getComponent().getClassName()));
    }
}
