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

public class YourBids extends AppCompatActivity {
    private ListView YourBids;
    private ArrayList<Stalls> userBids = new ArrayList<Stalls>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String email;

        setContentView(R.layout.activity_your_bids);
        YourBids = (ListView)findViewById(R.id.YourBidsLv);

        Intent intent = getIntent();
        email = intent.getStringExtra("account");
        ElasticSearchCtr.GetPendingStalls getPendingStalls =
                new ElasticSearchCtr.GetPendingStalls();
        getPendingStalls.execute(email);

        try {
            userBids = getPendingStalls.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        //ArrayList<String> TempString = new ArrayList<>();
        //TempString.add("first\n");
        //TempString.add("second\n");
        YourBids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickBids = new Intent(view.getContext(), EditBids.class);
                //ArrayList<String> TempString = new ArrayList<>();
                //TempString.add("first\n");
                //TempString.add("second\n");
                String entry;
                entry = YourBids.getItemAtPosition(position).toString();
                clickBids.putExtra("entry", entry);
                clickBids.putExtra("id", position);
                clickBids.putExtra("array", userBids);
                startActivity(clickBids);
            }
        });
        ArrayAdapter<Stalls> adapter = new ArrayAdapter<Stalls>(this,R.layout.own_stalls_with_bids,
                userBids);
        YourBids.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
