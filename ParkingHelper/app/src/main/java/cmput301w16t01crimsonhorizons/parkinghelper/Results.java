package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Results extends AppCompatActivity implements ViewInterface<Commands> {
    private ListView YourBids;
    private ArrayList<Stalls> userBids = new ArrayList<Stalls>();
    private String[] GetAvailable = new String[2];
    private AdapterEditStall myAdapter;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_your_bids);
        YourBids = (ListView)findViewById(R.id.YourBidsLv);

        final EditStallSave command = new EditStallSave();
        command.addView(this);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        GetAvailable[0] = email;
        GetAvailable[1] = "Bidder";

        ElasticSearchCtr.GetPendingStalls getPendingStalls =
                new ElasticSearchCtr.GetPendingStalls();
        getPendingStalls.execute(email);
        try {
            ArrayList<Stalls> tempAry = new ArrayList<>();
            tempAry = getPendingStalls.get();
            userBids.clear();
            userBids.addAll(tempAry);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        myAdapter = new AdapterEditStall(this, R.layout.account_stalls, userBids);
        YourBids.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart(){
        super.onStart();
        YourBids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });

    }

    @Override
    public void updateView(Commands model) {
        ElasticSearchCtr.GetPendingStalls getPendingStalls =
                new ElasticSearchCtr.GetPendingStalls();
        getPendingStalls.execute(email);
        try {
            ArrayList<Stalls> tempAry = new ArrayList<>();
            tempAry = getPendingStalls.get();
            userBids.clear();
            userBids.addAll(tempAry);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        myAdapter.notifyDataSetChanged();

    }
}