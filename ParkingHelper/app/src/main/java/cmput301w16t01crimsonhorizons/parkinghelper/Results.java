package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin Lang and Lee Ingram
 *
 * This class is responsible for displaying what the user bidded on
 * task @see "ElasticSearchCtr"
 */

public class Results extends AppCompatActivity implements ViewInterface<Commands> {
    private ListView YourBids;
    private ArrayList<Bid> userBids = new ArrayList<Bid>();
    private AdapterYourBids myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.content_your_bids);
        YourBids = (ListView)findViewById(R.id.YourBidsLv);

        final EditStallSave command = new EditStallSave();
        command.addView(this);

        ElasticSearchCtr.GetBid getYourBids = new ElasticSearchCtr.GetBid();
        getYourBids.execute(new String[] { "bidder", CurrentAccount.getAccount().getEmail() });
        try {
            userBids = getYourBids.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        myAdapter = new AdapterYourBids(this, R.layout.your_bids_layout, userBids);
        YourBids.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart(){
        super.onStart();
/*        YourBids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickBids = new Intent(view.getContext(), EditBids.class);
                String entry;
                entry = YourBids.getItemAtPosition(position).toString();
                clickBids.putExtra("entry", entry);
                clickBids.putExtra("id", position);
                clickBids.putExtra("array", userBids);
                startActivity(clickBids);
                myAdapter.notifyDataSetChanged();
            }
        });*/

    }

    public void adapterClickUserName(View view){
        ClickUserName clickUserName = new ClickUserName();
        Intent newIntent = clickUserName.clickUserName(this, view, new ElasticSearchCtr.GetAccount());
        startActivity(newIntent);
    }

    @Override
    public void updateView(Commands model) {
        ElasticSearchCtr.GetBid getYourBids = new ElasticSearchCtr.GetBid();
        getYourBids.execute(new String[] { "bidder", CurrentAccount.getAccount().getEmail() });
        try {
            userBids = getYourBids.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        myAdapter.notifyDataSetChanged();

    }
}