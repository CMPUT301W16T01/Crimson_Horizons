package cmput301w16t01.kliangtestcases;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

/**
 * Created by Kevin L on 2/9/2016.
 */
public class UITesting extends ActivityInstrumentationTestCase2{
    public UITesting() {
        super(MainActivity.class);
    }
    public void clickPending(){
        MainActivity ira = (MainActivity) getActivity();
        Button button = (Button) ira.findViewById(R.id.Pending);
        button.performClick();
    }
    public void clickAccount(){
        MainActivity ira = (MainActivity) getActivity();
        Button button = (Button) ira.findViewById(R.id.Account);
        button.performClick();
    }
    public Boolean checkOnlineData(Stalls stall){
        return Boolean.FALSE;
    }
}
