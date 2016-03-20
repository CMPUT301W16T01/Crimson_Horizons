package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.entity.StringEntityHC4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BidsForStall extends AppCompatActivity {
    private ListView EachStallsWithBids;
    private Stalls stall;
    private CustomLstAdapter myAdapter;
    ArrayList<String>all = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bids_for_stall);
        Intent intent = getIntent();
        stall = (Stalls) intent.getSerializableExtra("stall");
        try{
            ArrayList<String> temp = new ArrayList<String>(Arrays.asList(stall.getLstBidders().split(",")));
            all.addAll(temp);
        }catch(NullPointerException e){
            Toast.makeText(getApplicationContext(),"No one bidded on your stuff",Toast.LENGTH_SHORT).show();
        }
        EachStallsWithBids = (ListView)findViewById(R.id.BidsForStallsLv);
        myAdapter=new CustomLstAdapter(this,R.layout.bids_for_stalls,all);
        EachStallsWithBids.setAdapter(myAdapter);
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

    public void adapterClickUserName(View view){
        ClickUserName clickUserName = new ClickUserName();
        Intent newIntent = clickUserName.clickUserName(this, view, new ElasticSearchCtr.GetAccount());
        startActivity(newIntent);
    }
}
