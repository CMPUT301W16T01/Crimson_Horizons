package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.media.Image;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class TestEditStall extends ActivityInstrumentationTestCase2<EditStall> {
    public TestEditStall() {
        super(EditStall.class);
    }

    public void createStall(Stalls s1){
        ElasticSearchForTest.MakeStall makeStall = new ElasticSearchForTest.MakeStall();
        makeStall.execute(s1);
        try {
            makeStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public void resetDatabase(Stalls s1){
        ElasticSearchForTest.DeleteStall deleteStall = new ElasticSearchForTest.DeleteStall();
        deleteStall.execute(s1);
        try {
            deleteStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
    public ArrayList<Stalls> retrieveStall(String[] temp){
        ArrayList<Stalls>tempreturn = new ArrayList<Stalls>();
        ElasticSearchForTest.GetStall getStall = new ElasticSearchForTest.GetStall();
        getStall.execute(temp);
        try {
            tempreturn = getStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return tempreturn;
    }

    /**
     * US 01.04.01
     * test editing a stall
     */
    public void testEditStall(){
       //Test that hit on list view the edit Stall view pops up.
/*        AccountActivity activity = getActivity();
        ListView myListView = (ListView)activity.findViewById(R.id.OwnStalls);
        myListView.performItemClick(
                myListView.getAdapter().getView(1, null, null),
                1,
                myListView.getAdapter().getItemId(1));
        EditStall editStall = new EditStall();
        TextView title = (TextView)editStall.findViewById(R.id.EditStallTitle);
        ViewAsserts.assertOnScreen(editStall.getWindow().getDecorView(),title);*/
        String[] temp = new String[2];
        temp[0]="before";
        temp[1]="Owner";
        Stalls s1 = new Stalls();
        s1.setOwner("before");
        this.createStall(s1);
        ArrayList<Stalls> returned;
        returned=this.retrieveStall(temp);
        while (returned.size()<1){
            returned=this.retrieveStall(temp);
        }
        assertTrue(returned.size() == 1);
        assertEquals(returned.get(0).getOwner(), "before");
        s1.setOwner("after");
        ElasticSearchForTest.updateStallES updateStallES = new ElasticSearchForTest.updateStallES();
        updateStallES.execute(s1);
        Boolean check = false;
        try {
             check = updateStallES.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (!check){
            assertFalse(Boolean.TRUE);
        }
        temp[0]="after";
        returned.clear();
        while(returned.size()<1){
            returned=this.retrieveStall(temp);
        }
        assertTrue(returned.size() == 1);
        assertEquals(returned.get(0).getOwner(), "after");
        this.resetDatabase(s1);
    }

    /**
     * test to se
     * US 01.05.01
     */
    public void testDeleteStall(){
        Stalls s1 = new Stalls();
        s1.setOwner("testing");
        String[] temp = new String[2];
        temp[0]="testing";
        temp[1]="Owner";
        this.createStall(s1);
        ArrayList<Stalls> returned;
        returned=this.retrieveStall(temp);
        while (returned.size()<1){
            returned=this.retrieveStall(temp);
        }
        assertTrue(returned.size() == 1);
        this.resetDatabase(s1);
        returned.clear();
        while(returned.size()!=0) {
            returned = this.retrieveStall(temp);
        }
    }
    /**
     * US 02.01.01
     * Test that a stall has a status. Since the status is a string, there is little
     * way to ensure that it is always one of three values, other than testing
     * all possible scenarios. However, the code is such that only the three values
     * are used.
     * Here it only assert that the values returned is never null.
     */
    public void testStallHasStatus() {
        Stalls s = new Stalls();
        assertNotNull(s.getStatus());
    }

    /**
     *     US 09.01.01 (added 2016-02-29)
     Test adding an image through intent injection.
     */
    public void testPhotographsAdded(){
        EditStall activity = getActivity();
        ImageView iv = (ImageView) activity.findViewById(R.id.ImageEditStall);
        Stalls stall = new Stalls();
        Intent intent = new Intent();
        setActivityIntent(intent);
        activity.takePicture(activity.findViewById(R.id.PictureEditStallBtn));
        //Hit the take picture button and assert that the screen pops up
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), iv);

        //Ensures that I can save to Elastic search and retrieve it.
        //// TODO: 3/10/2016 ACTUALLY GET AN IMAGE
        String temp = null;
        stall.setImage(temp);
        this.createStall(stall);
        this.resetDatabase(stall);

    }
    /**
     * US 09.02.01 (added 2016-02-29)
     * Todo is to get the picture stored properly.
     */

    public void testPhotographDelete(){
        Stalls stall = new Stalls();

        String temp = null;

        String[] search = new String[2];
        search[0]="Testing";
        search[1]="Owner";
        stall.setOwner("Testing");

        stall.setImage(temp);

        this.createStall(stall);
        ArrayList<Stalls> rStall = new ArrayList<>();
        rStall = this.retrieveStall(search);
        assertEquals("size be 1",1,rStall.size());
        this.resetDatabase(stall);
        rStall.clear();
        rStall=this.retrieveStall(search);
        assertEquals("size be 0", 0, rStall.size());
    }
    /**
     * US 09.03.01 (added 2016-02-29)
     * As an owner or borrower, I want to view any attached photograph for a thing.
     */
    public void testPhotographHaveView(){
        EditStall activity = getActivity();
        ImageView iv = (ImageView) activity.findViewById(R.id.ImageEditStall);
        Stalls stall = new Stalls();
        Intent intent = new Intent();
        setActivityIntent(intent);

        //Checks that if I start the activity with the stall in place, will the view for image
        //exist.
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), iv);
    }
    /**
     * US 09.04.01 (added 2016-02-29)
     * As a sys admin, I want each photograph to be under 65536 bytes in size.
     * Needs to get the image properly
     */
    public void testPhotographSize(){
        //Obtain image of size 1000000bytes (greater)
        String image = getImage();

        EditStall activity = getActivity();
        ImageView iv = (ImageView) activity.findViewById(R.id.ImageEditStall);
        Stalls stall = new Stalls();

        stall.setImage(image);

        Intent intent = new Intent();
        intent.putExtra("stall",stall);
        setActivityIntent(intent);
        try {
            activity.takePicture(activity.findViewById(R.id.PictureEditStallBtn));
            //Expect error of too big to be throwned.
            assertFalse(Boolean.TRUE);
        }catch (Exception e){
            assertTrue(Boolean.TRUE);
        }
        //Hit the take picture button and assert that the screen pops up
        ViewAsserts.assertOnScreen(activity.getWindow().getDecorView(), iv);
    }
    public String  getImage(){
        String image = null;
        return image;
    }


}