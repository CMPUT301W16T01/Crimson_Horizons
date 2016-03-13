package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BidsForStall extends AppCompatActivity {
    private ListView EachStallsWithBids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids_for_stall);
        Intent intent = getIntent();
        ArrayList<String> all = intent.getStringArrayListExtra("array");
        EachStallsWithBids = (ListView)findViewById(R.id.BidsForStallsLv);
        EachStallsWithBids.setAdapter(new CustomLstAdapter(this,R.layout.bids_for_stalls,
                all));
    }

    public void declineBid(View view){

    }

}
