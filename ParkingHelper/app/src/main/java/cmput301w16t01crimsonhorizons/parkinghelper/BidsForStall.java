package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * This is to check any stalls the user owns that needs bids
 */
public class BidsForStall extends AppCompatActivity {

    private Stalls stall;
    ArrayList<BidsForStallsObject> all = new ArrayList<BidsForStallsObject>();
    ListView eachStallsWithBids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bids_for_stall);
        Intent intent = getIntent();
        stall = (Stalls) intent.getSerializableExtra("stall");
        try{
            ArrayList<BidsForStallsObject> temp = new ArrayList<BidsForStallsObject>();
            for(String newText : stall.getLstBidders().split(","))
                temp.add(new BidsForStallsObject(newText, stall));

            temp.remove(0);


        }catch(NullPointerException e){
            Toast.makeText(getApplicationContext(),"No one bidded on your stall",Toast.LENGTH_SHORT).show();
        }
        eachStallsWithBids = (ListView)findViewById(R.id.BidsForStallsLv);
        CustomLstAdapter myAdapter = new CustomLstAdapter(this, R.layout.bids_for_stalls, all);
        eachStallsWithBids.setAdapter(myAdapter);
    }
    //US 05.07.01
    public void declineBid(String retrieved){
        all.remove(retrieved);
        String LstModified = all.toString();
        stall.setLstBidders(LstModified);
    }

    //US 05.07.01
    public void acceptBid(String retrieved){
        String bidder = retrieved.split(" ")[1];
        stall.setBorrower(bidder);
        stall.setStatus("Borrowed");
    }

    /**
     * click on user name
     * @see AccountActivity
     * @param view
     */
    public void adapterClickUserName(View view){
        ClickUserName clickUserName = new ClickUserName();
        Intent newIntent = clickUserName.clickUserName(this, view, new ElasticSearchCtr.GetAccount());
        startActivity(newIntent);
    }
}
