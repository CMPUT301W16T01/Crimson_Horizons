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

    @UiThreadTest
    public void testClickUsername(){
        //TODO:create test users and test stalls. Delete after use.
        Account account1 = new Account();
        account1.setEmail("__test1");
        account1.setWorkPhone("1.1");
        account1.setCellPhone("1.2");

        Intent intent = new Intent();
        setActivityIntent(intent);
        Search search = (Search) getActivity();

        ListView stallListView = (ListView) search.findViewById(R.id.ResultLv);

        //TODO: Figure out the alternative to this. Can't click on another view outside of the
        //TODO: main view

        View stallElement = stallListView.getChildAt(0);
        View nameElement = stallElement.findViewById(R.id.StallNameEditStallV);
        nameElement.performClick();

        View view = search.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(view, search.findViewById(R.id.emailAddress));
    }
}
