package com.crimson_horizons.crimsonhorizonsunittests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    public static final String ADD_PARKING_STALL = "STALL";
    public static final String TEXT_TO_TRANSFORM_KEY = "TEXT";
    public static final String STALL_STATUS_KEY = "STATUS";

    public static final int DATA1 = 1;
    public static final int DATA2 = 2;

    public static final int AVAILABLE = 1;
    public static final int BIDDED = 2;
    public static final int BORROWED = 3;

    private int mode;
    private int mode2;
    private String text;
    private String text2;
    private ParkingStall parkingStall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        Intent intent = getIntent();
        mode = intent.getIntExtra(ADD_PARKING_STALL, DATA1);

        text = intent.getStringExtra(TEXT_TO_TRANSFORM_KEY);

        mode2 = intent.getIntExtra(STALL_STATUS_KEY, AVAILABLE);

        switch(mode2){
            case AVAILABLE:
                text2 = "AVAILABLE";
                break;
            case BIDDED:
                text2 = "BIDDED";
                break;
            case BORROWED:
                text2 = "BORROWED";
            default:
                break;
        }

            //set which field in a switch-case here
        ((TextView)findViewById(R.id.tempText)).setText(text);

        ((TextView)findViewById(R.id.tempText)).setText(text2);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void saveParkingStall(ArrayList<ParkingStall> inputData){

    }

    public static ArrayList<ParkingStall> loadParkingStall(){
        ArrayList<ParkingStall> returnParkingStallArray = new ArrayList<ParkingStall>();
        ParkingStall parkingStall = new ParkingStall();
        parkingStall.setAvailability(new RandomString(15).nextString());
        returnParkingStallArray.add(parkingStall);
        return  returnParkingStallArray;
    }

}
