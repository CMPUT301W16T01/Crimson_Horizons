package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Created by schuman on 3/9/16.
 */
public class ClickUserName extends AppCompatActivity {
    public <activityType extends AppCompatActivity> Intent clickUserName (activityType currentActivity, TextView view){
        ElasticSearchCtr.GetAccount executeAccount = new ElasticSearchCtr.GetAccount();
        Intent newIntent= null;
        try {
            Account newAccount = executeAccount.execute(view.getText().toString()).get();
            newIntent = new Intent(currentActivity, ViewProfile.class);
            newIntent.putExtra("account", newAccount);
            return newIntent;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return newIntent;
    }
}
