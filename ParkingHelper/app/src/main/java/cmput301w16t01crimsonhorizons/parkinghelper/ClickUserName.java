package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

/**
 * Created by schuman on 3/9/16.
 */
public class ClickUserName extends AppCompatActivity {
    public <activityType extends AppCompatActivity, getType extends AsyncTask<String, Void,Account>> Intent clickUserName (activityType currentActivity, View view, getType executeGet){
        Intent newIntent= null;
        try {
            TextView inputText = (TextView) view;
            Account newAccount = executeGet.execute(inputText.getText().toString()).get();
            newIntent = new Intent(currentActivity, ViewProfile.class);
            newIntent.putExtra("account", newAccount);
            return newIntent;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return newIntent;
    }
}
