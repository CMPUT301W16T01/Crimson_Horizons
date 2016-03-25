package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class AddStall extends AppCompatActivity {
    protected Stalls stall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stall);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    public void saveStallInformation(View view){
        stall = new Stalls();
        EditText description = (EditText)findViewById(R.id.DescriptionET);
        EditText longitude = (EditText)findViewById(R.id.longitudeAddStallET);
        EditText latitude = (EditText)findViewById(R.id.latitudeAddStallET);
        Double[] location_double = new Double[2];
        try {
            location_double[0] = Double.parseDouble(longitude.getText().toString());
            location_double[1] = Double.parseDouble(latitude.getText().toString());
        }catch (Exception e){
            location_double[0]=0.00;
            location_double[1]=0.00;
        }
        String newDescription = description.getText().toString();
        stall.setOwner(CurrentAccount.getAccount().getEmail());
        stall.setDescription(newDescription);
        stall.setStatus("Available");
        stall.setLocation(location_double);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            AsyncTask<Stalls, Void, Void> s1execute = new ElasticSearchCtr.MakeStall().execute(stall);
            setResult(RESULT_OK);
        } else {
            ArrayList<Stalls> allStalls = CurrentStalls.getCurrentStalls();
            allStalls.add(stall);
            CurrentStalls.setCurrentStalls(allStalls);
            OfflineIO io = new OfflineIO();
            io.StoreStall(allStalls, this);
            ArrayList<Stalls>addStalls = new ArrayList<>();
            addStalls.add(stall);
            io.StoreStallsToAdd(addStalls,this);
        }
        finish();
    }
    public void map(View view){
        String query = "geo:0,0?q=0,0";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(query));
        startActivity(intent);
    }

}
