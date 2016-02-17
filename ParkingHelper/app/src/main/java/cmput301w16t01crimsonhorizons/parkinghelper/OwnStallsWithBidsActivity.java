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

public class OwnStallsWithBidsActivity extends AppCompatActivity {
    private ListView YourBids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_stalls_with_bids);
        YourBids = (ListView)findViewById(R.id.YourBidsLv);
    }
    @Override
    protected void onStart(){
        super.onStart();
        ArrayList<String>TempString = new ArrayList<>();
        TempString.add("first\n");
        TempString.add("second\n");
        YourBids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickBids = new Intent(view.getContext(), BidsForStall.class);
                ArrayList<String>TempString = new ArrayList<>();
                TempString.add("first\n");
                TempString.add("second\n");
                String entry;
                entry = YourBids.getItemAtPosition(position).toString();
                clickBids.putExtra("entry", entry);
                clickBids.putExtra("id", position);
                clickBids.putExtra("array",TempString);
                startActivity(clickBids);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.own_stalls_with_bids,
                                                                TempString);
        YourBids.setAdapter(adapter);
    }
}
