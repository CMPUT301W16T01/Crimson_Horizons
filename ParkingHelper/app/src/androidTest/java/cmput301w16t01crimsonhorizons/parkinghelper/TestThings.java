package cmput301w16t01crimsonhorizons.parkinghelper;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ApplicationTestCase;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class TestThings extends ActivityInstrumentationTestCase2<EditStall> {
    public TestThings() {
        super(EditStall.class);
    }
    //Navigate to Account
    //Test
    public void testEditStall(){
        //Set initial stall and inject this intent

        Intent intent = new Intent();
        Stalls stall = new Stalls();
        stall.setBidder("before");
        stall.setStatus("before");
        stall.setBidAmt(0.00);
        stall.setOwner("before");
        stall.setDescription("before");
        intent.putExtra("entry", stall);
        setActivityIntent(intent);
        EditStall editStall = (EditStall) getActivity();

        EditText title = (EditText)editStall.findViewById(R.id.NamePrompEditStall);
        EditText description = (EditText)editStall.findViewById(R.id.DescriptionPrompEditStall);
        EditText status = (EditText)editStall.findViewById(R.id.StatusEditStallEv);
        Button save = (Button)editStall.findViewById(R.id.SaveEdit);
        AsyncTask<Stalls, Void, Void> execute = new ElasticSearchForTest.MakeStall().execute(stall);
        String[]temp = new String[2];
        temp[0]="before";
        temp[1]="Owner";
        ElasticSearchForTest.GetStall getStall = new ElasticSearchForTest.GetStall();
        getStall.execute(temp);
        ArrayList<Stalls> before_stall = new ArrayList<>();
        try {
             before_stall = getStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertTrue(before_stall.size() == 1);
        assertEquals(before_stall.get(0).getOwner(), "before");
        title.setText("after");
        description.setText("after");
        status.setText("after");
        save.performClick();

        ArrayList<Stalls>after = new ArrayList<>();
        temp[0]="after";
        getStall.execute(temp);
        try {
            after = getStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertTrue(after.size() == 1);
        assertEquals(after.get(0).getOwner(), "after");
    }

}