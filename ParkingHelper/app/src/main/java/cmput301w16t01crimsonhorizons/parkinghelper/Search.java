package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Search extends AppCompatActivity {
    private ListView Result;
    private Button searchBtn;
    private EditText searchBox;
    private ArrayList<Stalls> stalls;
    ArrayAdapter<Stalls> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Result = (ListView)findViewById(R.id.ResultLv);
        searchBtn = (Button)findViewById(R.id.SearchBtn);
        searchBox = (EditText)findViewById(R.id.query);
        stalls = new ArrayList<Stalls>();
    }

    @Override
    protected void onStart(){
        super.onStart();
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

        searchBtn.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ElasticSearchCtr.SearchDataBaseTask searchTask =
                        new ElasticSearchCtr.SearchDataBaseTask();
                String keyWords = searchBox.getText().toString();
                searchTask.execute(keyWords);

                try {
                    stalls = searchTask.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
            }
        });

        adapter = new ArrayAdapter<Stalls>(this, R.layout.own_stalls_with_bids,
                stalls);
        Result.setAdapter(adapter);
    }
    public void clickSearch(View view){
        // TODO: 2/15/2016 Fill in what happens when search is hit again

    }

}