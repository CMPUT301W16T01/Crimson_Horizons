package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin L on 3/30/2016.
 */
public class TestOffline extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    public TestOffline() {
        super(WelcomeActivity.class);
    }

    /**
     * US 08.01.01
     * Tests if the changese offline can be stored properly.
     * The updating part when connection is just like adding normally which was already tested
     * @see TestAddStall
     */
    public void testOffline(){
        OfflineIO io = new OfflineIO();
        Stalls s1 = new Stalls();
        s1.setOwner("__test1");
        s1.setDescription("__test1AddedOffline");
        Stalls s2 = new Stalls();
        s2.setOwner("__test1");
        s2.setDescription("__test1AddedOffline2");
        ArrayList<Stalls> addList = new ArrayList<>();
        addList.add(s1);
        addList.add(s2);
        io.StoreStallsToAdd(addList, getActivity().getApplicationContext());
        ArrayList<Stalls> returnedAdd = io.LoadStallsToAdd(getActivity().getApplicationContext());
        assertEquals("returned size same as put in",addList.size(),returnedAdd.size());
        //Loops through both list and see if they are the same
        int idx = 0;
        while(idx<addList.size()){
            assertEquals("Owner same",addList.get(idx).getOwner(),returnedAdd.get(idx).getOwner());
            assertEquals("description same",addList.get(idx).getDescription(),returnedAdd.get(idx).getDescription());
            idx=idx+1;
        }
        //clean up for adding
        io.deleteAddFile(getActivity().getApplicationContext());
    }
}
