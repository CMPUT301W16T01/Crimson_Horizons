package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by schuman on 2/26/16.
 */
public class LendingStalls extends AppCompatActivity {
    private ListView lendingStalls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_lending_stalls);
        lendingStalls = (ListView)findViewById(R.id.lendingStallsList);
    }
    @Override
    protected void onStart(){
        super.onStart();
        ArrayList<String> TempString = new ArrayList<>();
        TempString.add("first\n");
        TempString.add("second\n");
        lendingStalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickBids = new Intent(view.getContext(), BidsForStall.class);
                ArrayList<String> TempString = new ArrayList<>();
                TempString.add("first\n");
                TempString.add("second\n");
                String entry;
                entry = lendingStalls.getItemAtPosition(position).toString();
                clickBids.putExtra("entry", entry);
                clickBids.putExtra("id", position);
                clickBids.putExtra("array", TempString);
                startActivity(clickBids);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.content_lending_stalls,
                TempString);
        lendingStalls.setAdapter(adapter);
    }
}
