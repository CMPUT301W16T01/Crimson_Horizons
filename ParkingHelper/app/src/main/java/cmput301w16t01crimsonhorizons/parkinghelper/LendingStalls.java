package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by schuman on 2/26/16.
 */
public class LendingStalls extends AppCompatActivity {
    private ListView eachLendingStalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_lending_stalls);
        Intent intent = getIntent();
        ArrayList<String> all = intent.getStringArrayListExtra("array");
        eachLendingStalls = (ListView)findViewById(R.id.lendingStallsList);
        eachLendingStalls.setAdapter(new CustomLstAdapter(this, R.layout.bids_for_stalls,
                all));
    }
}
