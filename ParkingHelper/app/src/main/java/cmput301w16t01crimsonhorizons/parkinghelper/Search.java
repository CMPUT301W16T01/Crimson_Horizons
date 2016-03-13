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

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.Get;

public class Search extends AppCompatActivity implements ViewInterface<Commands> {
    private ListView Result;
    private ArrayList<Stalls>StallAry = new ArrayList<>();
    private Button searchBtn;
    private EditText searchBox;
    AdapterEditStall myAdapter;
    String[] GetAvailable = new String[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Result = (ListView)findViewById(R.id.ResultLv);
        searchBtn = (Button)findViewById(R.id.SearchBtn);
        searchBox = (EditText)findViewById(R.id.query);

        final EditStallSave command = new EditStallSave();
        command.addView(this);
        GetAvailable[0] = "Available";
        GetAvailable[1] = "Status";
        StallAry = command.UpdateStall(GetAvailable);

        searchBtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ElasticSearchCtr.SearchDataBaseTask searchTask =
                        new ElasticSearchCtr.SearchDataBaseTask();
                String keyWords = searchBox.getText().toString();
                ArrayList<Stalls> temp = new ArrayList<Stalls>();
                try {
                    if (keyWords.equals("")==false) {
                        searchTask.execute(keyWords);
                        temp = searchTask.get();
                        if (temp.size()!=0){
                            StallAry.clear();
                            StallAry.addAll(temp);
                        }
                    } else {
                        StallAry.clear();
                        temp = command.UpdateStall(GetAvailable);
                        StallAry.addAll(temp);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                myAdapter.notifyDataSetChanged();
            }
        });

        myAdapter = new AdapterEditStall(this, R.layout.account_stalls, StallAry);
        Result.setAdapter(myAdapter);
        this.updateView(command);
/*        adapter = new ArrayAdapter<Stalls>(this, R.layout.own_stalls_with_bids,
                StallAry);
        Result.setAdapter(adapter);*/


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
        ArrayList<Stalls>temp=new ArrayList<>();
        temp = model.UpdateStall(GetAvailable);
        StallAry.clear();
        StallAry.addAll(temp);
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

        myAdapter.notifyDataSetChanged();

    }

}
