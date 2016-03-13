package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;

/**
 * Created by Kevin L on 3/10/2016.
 */
public class TestEditStallNew extends ActivityInstrumentationTestCase2<EditStall> {
    public TestEditStallNew() {
        super(EditStall.class);
    }

    //Since the EditStall method only retrieves the information from the text view and passes to the
    //the command object I am testing here if it can pass the information correctly.
    public void testEditStallNew(){
        Stalls s1 = new Stalls();
        s1.setOwner("testing");
        EditStallSaveTesting commands = new EditStallSaveTesting(s1);
        assertEquals("stall information should be changed", "changed", s1.getOwner());
        s1.setOwner("after");
    }
    public void testDeleteStall(){
        Stalls s1 = new Stalls();
        s1.setOwner("before");
        DeleteCommandTesting command = new DeleteCommandTesting(s1);
        Stalls returnedStall = command.testExecute();
        assertEquals("Both should be the same",s1,returnedStall);
    }
}
