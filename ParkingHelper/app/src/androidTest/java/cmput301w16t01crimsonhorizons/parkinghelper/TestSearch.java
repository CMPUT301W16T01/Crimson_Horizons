package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import android.test.UiThreadTest;

import android.test.ViewAsserts;
import android.view.View;
import android.widget.ListView;

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
        stalls.setStallID("available");
        stalls.setDescription("A test stall");

        CurrentAccount.setAccount(account2);
    }

    protected void tearDown(){
        ElasticSearchForTest.deleteUser executeDelete = new ElasticSearchForTest.deleteUser();
        ElasticSearchForTest.deleteUser executeDelete2 = new ElasticSearchForTest.deleteUser();
        ElasticSearchForTest.DeleteStall executeDelete3 = new ElasticSearchForTest.DeleteStall();
        ElasticSearchForTest.GetAccount executeGet = new ElasticSearchForTest.GetAccount();
        ElasticSearchForTest.GetAccount executeGet2 = new ElasticSearchForTest.GetAccount();


        executeDelete.execute();
    }

    @UiThreadTest

    public void testClickUsername(){
        //TODO:create test users and test stalls. Delete after use.

        Account account1 = CurrentAccount.getAccount();

        Intent intent = new Intent();
        setActivityIntent(intent);
        Search search = (Search) getActivity();

        ListView stallListView = (ListView) search.findViewById(R.id.ResultLv);

        //TODO: Figure out the alternative to this. Can't click on another view outside of the
        //TODO: main view

        View stallElement = search.findViewById(R.id.StallNameEditStallV);
        //CHANGE
        stallElement.performClick();

        View view = search.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(view, search.findViewById(R.id.emailAddress));
    }
}
