package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Results extends AppCompatActivity  implements ViewInterface<Commands>{
    private ArrayList<Stalls> userBids = new ArrayList<Stalls>();
    AdapterEditStall myAdapter;
    private ListView Result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditStallSave command = new EditStallSave();
        String email;

        setContentView(R.layout.activity_your_bids);
        Result = (ListView)findViewById(R.id.YourBidsLv);

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
        Result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickBids = new Intent(view.getContext(), EditBids.class);
                //ArrayList<String> TempString = new ArrayList<>();
                //TempString.add("first\n");
                //TempString.add("second\n");
                String entry;
                entry = Result.getItemAtPosition(position).toString();
                clickBids.putExtra("entry", entry);
                clickBids.putExtra("id", position);
                clickBids.putExtra("array", userBids);
                startActivity(clickBids);
            }
        });
        command.addView(this);
        myAdapter = new AdapterEditStall(this, R.layout.account_stalls, userBids);
        Result.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        this.updateView(command);
    }

    @Override
    public void onStart(){
        super.onStart();
        //ArrayList<String> TempString = new ArrayList<>();
        //TempString.add("first\n");
        //TempString.add("second\n");

        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateView(Commands model) {
        myAdapter.notifyDataSetChanged();
    }
}
