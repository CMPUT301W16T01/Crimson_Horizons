package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class OwnStallsWithBidsActivity extends AppCompatActivity {
    private ListView OwnStalls;
    private Account account;
    AdapterEditStall myAdapter;
    ArrayList<Stalls>StallAry = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_own_stalls_with_bids);
        account = CurrentAccount.getAccount();
        OwnStalls = (ListView)findViewById(R.id.OwnStalls);
        String email = account.getEmail();
        ElasticSearchCtr.GetBidStall getBidStall = new ElasticSearchCtr.GetBidStall();
        String[]temp = new String[4];
        temp[0]=email;
        temp[1]="Owner";
        temp[2]="0.0";
        temp[3]="BidAmt";
        try {
            ArrayList<Stalls>tempAry = new ArrayList<>();
            getBidStall.execute(temp);
            tempAry = getBidStall.get();
            StallAry.clear();
            StallAry.addAll(tempAry);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        OwnStalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            EditText lv = (EditText)findViewById(R.id.EmailET);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickStall = new Intent(view.getContext(), BidsForStall.class);
                Stalls entry = (Stalls)OwnStalls.getItemAtPosition(position);
                clickStall.putExtra("entry", entry);
                startActivity(clickStall);

            }
        });
        myAdapter = new AdapterEditStall(this, R.layout.account_stalls, StallAry);
        OwnStalls.setAdapter(myAdapter);
    }
    @Override
    protected void onStart(){
        super.onStart();
        this.update();
    }

    public void adapterClickUserName(View view){
        ClickUserName clickUserName = new ClickUserName();
        Intent newIntent = clickUserName.clickUserName(this, view, new ElasticSearchCtr.GetAccount());
        startActivity(newIntent);
    }

    public void update(){
        account = CurrentAccount.getAccount();
        String email = account.getEmail();
        ElasticSearchCtr.GetBidStall getBidStall = new ElasticSearchCtr.GetBidStall();
        String[]temp = new String[4];
        temp[0]=email;
        temp[1]="Owner";
        temp[2]="0.0";
        temp[3]="BidAmt";
        try {
            ArrayList<Stalls>tempAry = new ArrayList<>();
            getBidStall.execute(temp);
            tempAry = getBidStall.get();
            StallAry.clear();
            StallAry.addAll(tempAry);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        OwnStalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            EditText lv = (EditText) findViewById(R.id.EmailET);

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickStall = new Intent(view.getContext(), BidsForStall.class);
                Stalls entry = (Stalls) OwnStalls.getItemAtPosition(position);
                clickStall.putExtra("entry", entry);
                clickStall.putExtra("id", position);
                startActivity(clickStall);
            }
        });
        myAdapter.notifyDataSetChanged();
    }
}
