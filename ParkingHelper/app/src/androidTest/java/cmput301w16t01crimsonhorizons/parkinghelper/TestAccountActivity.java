package cmput301w16t01crimsonhorizons.parkinghelper;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by schuman on 3/10/16.
 */
public class TestAccountActivity extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    ArrayList<Stalls> tempAry;
    Account account1;

    public TestAccountActivity() {
        super(WelcomeActivity.class);
    }
    @Override
    public void setUp() throws Exception {
        ElasticSearchForTest.addUser addUser = new ElasticSearchForTest.addUser();
        Account account = new Account();
        account.setEmail("__test1");
        addUser.execute(account);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        solo = new Solo(getInstrumentation());
        getActivity();
    }
    @Override
    public void tearDown() throws Exception {
        ElasticSearchForTest.GetAccount getAccount = new ElasticSearchForTest.GetAccount();
        ElasticSearchForTest.deleteUser deleteUser = new ElasticSearchForTest.deleteUser();
        Account account = getAccount.execute("__test1").get();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        deleteUser.execute(account);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        solo.finishOpenedActivities();
    }

    /**
     * test clicks user name
     * US 03.03.01
     */
    public void testClickUsername(){
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "123@123");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.AccountBtn));
        ListView lv = (ListView)solo.getView(R.id.OwnStalls);
        View listelement = lv.getChildAt(0);
        TextView username = (TextView) listelement.findViewById(R.id.StallNameEditStallV);
        solo.clickOnView(username);
        solo.assertCurrentActivity("Should be viewProfile", ViewProfile.class);
        solo.goBack();
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }

    /**
     * test user can add stall/delete stall
     * US 01.04.01
     * US 01.05.01
     */
    public void testStallsList(){
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "__test1");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));


        solo.clickOnView(solo.getView(R.id.AccountBtn));
        solo.assertCurrentActivity("should be lists of own stalls", AccountActivity.class);

        solo.clickOnView(solo.getView(R.id.AddBtn));
        solo.enterText((EditText) solo.getView(R.id.NamePrompET), "__test1");
        solo.enterText((EditText) solo.getView(R.id.DescriptionET), "Test.");
        solo.clickOnView(solo.getView(R.id.AddInAddBtn));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
        String[]temp = new String[2];
        temp[0]="__test1";
        temp[1]="Owner";
        tempAry = new ArrayList<>();
        try {
            getStall.execute(temp);
            tempAry = getStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertTrue(tempAry.size() == 1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ElasticSearchCtr.DeleteStall deleteStall = new ElasticSearchCtr.DeleteStall();
        deleteStall.execute(tempAry.get(0));
        Boolean check = false;
        try {
            check = deleteStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertTrue("didn't delete Stall", check);
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }

    /**
     * Test adding/deleting a picture to/from a stall
     * US 09.01.01
     * US 09.02.01
     */
    public void testPictureStalls(){
        Bitmap bitmap = Bitmap.createBitmap(new int[]{1, 2, 5, 4}, 2, 2, Bitmap.Config.ARGB_8888);

        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "robo");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));


        solo.clickOnView(solo.getView(R.id.AccountBtn));

        solo.clickOnView(solo.getView(R.id.AddBtn));
        solo.enterText((EditText) solo.getView(R.id.NamePrompET), "robo");
        solo.enterText((EditText) solo.getView(R.id.DescriptionET), "Test.");
        solo.clickOnView(solo.getView(R.id.AddInAddBtn));
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.AccountBtn));
        ListView lv = (ListView)solo.getView(R.id.OwnStalls);
        View element = lv.getChildAt(0);
        assertNotNull(element);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
        String[]temp = new String[2];
        temp[0]="robo";
        temp[1]="Owner";
        tempAry = new ArrayList<>();
        try {
            getStall.execute(temp);
            tempAry = getStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertTrue(tempAry.size() == 1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tempAry.get(0).setThumbnail(bitmap);

        ElasticSearchForTest.updateStallES updateStallES = new ElasticSearchForTest.updateStallES();

        updateStallES.execute(tempAry.get(0));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("Should be the created bitmap", bitmap, tempAry.get(0).getThumbnail());

        ListView lv2 = (ListView)solo.getView(R.id.OwnStalls);
        View listelement = lv2.getChildAt(0);
        TextView description = (TextView) listelement.findViewById(R.id.DescriptionEditStallV);
        solo.clickOnView(description);

        solo.clickOnView(solo.getView(R.id.DelPicEditStallBtn));
        solo.clickOnView(solo.getView(R.id.SaveEdit));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getStall = new ElasticSearchCtr.GetStall();

        try {
            getStall.execute(temp);
            tempAry = getStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals("Should be a null bitmap", null, tempAry.get(0).getThumbnail());
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));

        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "robo");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));

        solo.clickOnView(solo.getView(R.id.AccountBtn));
        solo.clickInList(0);
        solo.assertCurrentActivity("should be in edit stall", EditStall.class);
        solo.clickOnView(solo.getView(R.id.EditStallDeleteBtn));
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }


    /**
     * US 01.03.01
     * Test that an object is displayed in account.
     */
    public void testDisplayStatus() {
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "123@123");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.AccountBtn));
        solo.clickInList(0);
        solo.assertCurrentActivity("should be editing stalls", EditStall.class);
        solo.goBack();
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }

    /**
     * US 09.03.01
     * Test that a picture is displayed in an account
     */
    public void testDisplayPicture(){
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "123@123");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.AccountBtn));
        ListView lv = (ListView)solo.getView(R.id.OwnStalls);
        View listelement = lv.getChildAt(0);
        ImageView image = (ImageView)listelement.findViewById(R.id.PictureEditStallV);
        assertTrue("view not visible",((BitmapDrawable)(((ImageView)listelement.findViewById(R.id.PictureEditStallV)).getDrawable())).getBitmap() != null);
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }

    /**
     * US 09.04.01
     * Test that the picture takes up less than 655636 bytes
     */
    public void testPictureSize(){
        solo.clickOnView(solo.getView(R.id.LoginButton));
        solo.enterText((EditText) solo.getView(R.id.emailAddress), "123@123");
        solo.clickOnView(solo.getView(R.id.email_sign_in_button));
        solo.clickOnView(solo.getView(R.id.AccountBtn));
        ListView lv = (ListView)solo.getView(R.id.OwnStalls);
        View listelement = lv.getChildAt(0);
        assertTrue("should take up less than 65536 bytes", ((BitmapDrawable)(((ImageView)listelement.findViewById(R.id.PictureEditStallV)).getDrawable())).getBitmap().getByteCount() < 65536);
        solo.goBack();
        solo.clickOnView(solo.getView(R.id.SignoutBtnHomePg));
    }

}
