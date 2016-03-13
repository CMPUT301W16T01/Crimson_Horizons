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
    private Stalls stall;
    private CustomLstAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids_for_stall);
        Intent intent = getIntent();
        stall= (Stalls) intent.getSerializableExtra("entry");
        ArrayList<String>all = stall.getLstBidders();
        EachStallsWithBids = (ListView)findViewById(R.id.BidsForStallsLv);
        myAdapter=new CustomLstAdapter(this,R.layout.bids_for_stalls,all);
        EachStallsWithBids.setAdapter(myAdapter);
    }
    //US 05.07.01
    public void declineBid(String retrieved){
        stall.getLstBidders().remove(retrieved);
    }

    //US 05.07.01
    public void acceptBid(String retrieved){
        String bidder = retrieved.split(" ")[1];
        stall.setBorrower(bidder);
        stall.setStatus("Borrowed");
    }
}
