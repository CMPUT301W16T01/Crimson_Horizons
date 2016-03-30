package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ReturningStallActivity extends AppCompatActivity {
    protected Stalls stall;
    private Intent intent;
    private Bitmap thumbnail;
    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returning_stall);

        intent = getIntent();
        stall = (Stalls)intent.getSerializableExtra("stall");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView title = (TextView)findViewById(R.id.RetStallNameDisp);
        TextView description = (TextView)findViewById(R.id.RetStallDescriptionDisp);
        TextView longitude = (TextView)findViewById(R.id.longitudeRetStallTV);
        TextView latitude = (TextView)findViewById(R.id.latitudeRetStallTV);
        title.setText(stall.getOwner());
        description.setText(stall.getDescription());
        longitude.setText(stall.getLocation()[0].toString());
        latitude.setText(stall.getLocation()[1].toString());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void returnStall(View view){
        stall.setStatus("Available");
        stall.setBorrower("");
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            Commands command = new EditStallSave(stall);
            Boolean check = command.execute();
            if (check) {
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "didn't save", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

}
