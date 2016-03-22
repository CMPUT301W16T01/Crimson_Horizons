package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
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
        AsyncTask<Stalls, Void, Void> s1execute = new ElasticSearchCtr.MakeStall().execute(stall);
        setResult(RESULT_OK);
        finish();
    }

}
