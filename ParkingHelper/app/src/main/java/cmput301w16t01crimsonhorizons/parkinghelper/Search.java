package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * This is where the user can search and bid for stalls.
 * On start it automatically populates the list with all stalls that are available
 */
public class Search extends AppCompatActivity implements ViewInterface<Commands> {
    private ListView Result;
    private ArrayList<Stalls>StallAry = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Result = (ListView)findViewById(R.id.ResultLv);
        String[] GetAvailable= new String[2];
        EditStallSave command = new EditStallSave();
        command.addView(this);
        updateView(command);
    }

    @Override
    protected void onStart(){
        super.onStart();
        EditStallSave command = new EditStallSave();
        updateView(command);
    }

    @Override
    protected void onResume(){
        super.onResume();
        EditStallSave command = new EditStallSave();
        updateView(command);

    }
    public void clickSearch(View view){
        // TODO: 2/15/2016 Fill in what happens when search is hit again

    }


    @Override
    public void updateView(Commands model) {
        String[] GetAvailable= new String[2];
        GetAvailable[0] = "Available";
        GetAvailable[1] = "Status";
        StallAry = model.UpdateStall(GetAvailable);
        Result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickResult = new Intent(view.getContext(), BidStall.class);
                Stalls stall;
                stall = (Stalls) Result.getItemAtPosition(position);
                clickResult.putExtra("stall", stall);
                clickResult.putExtra("id", position);
                startActivity(clickResult);

            }
        });
        Result.setAdapter(new AdapterEditStall(this, R.layout.account_stalls, StallAry));
    }
}
