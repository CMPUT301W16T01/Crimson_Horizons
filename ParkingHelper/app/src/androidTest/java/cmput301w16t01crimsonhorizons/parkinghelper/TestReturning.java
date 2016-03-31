package cmput301w16t01crimsonhorizons.parkinghelper;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Kevin L on 3/11/2016.
 */
public class TestReturning extends ActivityInstrumentationTestCase2<LendingStalls> {
    public TestReturning() {
        super(LendingStalls.class);
    }

    /**
     * US 07.01.01
     * Test if when user hit returned button, status changes
     */
/*    public void testReturn(){
        Stalls s1 = new Stalls();
        s1.setStatus("Borrowed");
        //Replace with commands that extends the command that actually does the return.
        //Everything is same except for the fact that it does not write to file.
        CommandForTesting command = new CommandForTesting(s1);
        command.execute();
        assertEquals("status should be available","Available",s1.getStatus());
    }*/
}
