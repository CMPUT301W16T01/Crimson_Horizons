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

/**
 * Developed by Kevin Lang and Lee Ingram
 *
 * This class is responsible for displaying the results from
 * the SearchDataBaseTask @see "ElasticSearchCtr".
 */

public class Search extends AppCompatActivity implements ViewInterface<Commands> {
    private ListView Result;
    private ArrayList<Stalls>StallAry = new ArrayList<>();
    private Button searchBtn;
    private EditText searchBox;
    private AdapterEditStall myAdapter;
    private String[] GetAvailable = new String[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_search);
        Result = (ListView)findViewById(R.id.ResultLv);
        searchBtn = (Button)findViewById(R.id.SearchBtn);
        searchBox = (EditText)findViewById(R.id.query);

        final EditStallSave command = new EditStallSave();
        command.addView(this);
        GetAvailable[0] = "Status";
        GetAvailable[1] = "Available";
        GetAvailable[2] = "Bidded";
        final ElasticSearchCtr.GetAvailableStall getAvailableStall = new ElasticSearchCtr.GetAvailableStall();
        getAvailableStall.execute(GetAvailable);
        try {
            StallAry = getAvailableStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


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
                        ElasticSearchCtr.GetAvailableStall getAvailableStall2 = new ElasticSearchCtr.GetAvailableStall();
                        getAvailableStall2.execute(GetAvailable);
                        temp = getAvailableStall2.get();
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
        this.updateView(new EditStallSave());
    }

    @Override
    protected void onStart(){
        super.onStart();
        updateView(new EditStallSave());
    }

    @Override
    protected void onResume(){
        super.onResume();
        updateView(new EditStallSave());

    }

    public void adapterClickUserName(View view){
        ClickUserName clickUserName = new ClickUserName();
        Intent newIntent = clickUserName.clickUserName(this, view);
        startActivity(newIntent);
    }

    @Override
    public void updateView(Commands model) {
        ArrayList<Stalls>temp=new ArrayList<>();
        ElasticSearchCtr.GetAvailableStall getAvailableStall = new ElasticSearchCtr.GetAvailableStall();
        getAvailableStall.execute(GetAvailable);
        try {
            temp = getAvailableStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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

    AdapterEditStall getAdapter(){
        return myAdapter;
    }

    ArrayList<Stalls> getResults() {
        return StallAry;
    }

}
