package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    private ListView Result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Result = (ListView)findViewById(R.id.ResultLv);
    }

    @Override
    protected void onStart(){
        super.onStart();
        ArrayList<String> TempString = new ArrayList<>();
        TempString.add("first\n");
        TempString.add("second\n");
        Result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickResult = new Intent(view.getContext(), BidStall.class);
                String stall;
                stall = Result.getItemAtPosition(position).toString();
                clickResult.putExtra("stall", stall);
                clickResult.putExtra("id", position);
                startActivity(clickResult);

            }
        });
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,R.layout.own_stalls_with_bids,
                                                                TempString);
        Result.setAdapter(adapter);
    }
    public void clickSearch(View view){
        // TODO: 2/15/2016 Fill in what happens when search is hit again

    }

}
