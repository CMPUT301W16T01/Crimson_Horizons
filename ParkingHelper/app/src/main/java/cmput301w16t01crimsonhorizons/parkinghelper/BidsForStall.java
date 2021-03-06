package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * This is to check any stalls the user owns that have been bidded upon
 */
public class BidsForStall extends AppCompatActivity {

    private static Stalls stall;
    private static ArrayList<Bid> bidResults = new ArrayList<Bid>();
    ListView eachStallsWithBids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bids_for_stall);
        Intent intent = getIntent();
        stall = (Stalls) intent.getSerializableExtra("stall");
        ElasticSearchCtr.GetBid getBid = new ElasticSearchCtr.GetBid();
        String[] query = new String[2];
        query[0]="bidStallID";
        query[1]=stall.getStallID();
        getBid.execute(query);
        try {
            bidResults = getBid.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        eachStallsWithBids = (ListView)findViewById(R.id.BidsForStallsLv);
        CustomLstAdapter myAdapter = new CustomLstAdapter(this, R.layout.bids_for_stalls, bidResults);
        eachStallsWithBids.setAdapter(myAdapter);
    }
    //US 05.07.01
    public void declineBid(Bid bid){
        bidResults.remove(bid);
        if (bidResults.size()==0){
            stall.setStatus("Available");
        }
        ElasticSearchCtr.DeleteBid deleteBid = new ElasticSearchCtr.DeleteBid();
        deleteBid.execute(bid);
        EditStallSave edit = new EditStallSave(stall);
        edit.execute();
    }

    //US 05.07.01
    public void acceptBid(Bid bid){
        for (Bid b : bidResults) {
            ElasticSearchCtr.DeleteBid deleteBid = new ElasticSearchCtr.DeleteBid();
            deleteBid.execute(b);
        }
        bidResults.clear();
        stall.setBorrower(bid.Bidder());
        stall.setStatus("Borrowed");
        EditStallSave edit = new EditStallSave(stall);
        edit.execute();
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
