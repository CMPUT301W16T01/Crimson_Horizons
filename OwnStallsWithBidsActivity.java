package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class OwnStallsWithBidsActivity extends AppCompatActivity {
    private ListView OwnStalls;
    private Intent intent;
    private Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_stalls_with_bids);
        OwnStalls = (ListView)findViewById(R.id.OwnStalls);
	intent = getIntent();
    }
    @Override
    protected void onStart(){
        super.onStart();
	this.update();
    }

    public void update(){
        account = CurrentAccount.getAccount();
        ArrayList<Stalls>StallAry = new ArrayList<>();
        String email = account.getEmail();
        ElasticSearchCtr.GetBidStall getBidStall = new ElasticSearchCtr.GetBidStall();
        try {
            String[]temp = new String[4];
            temp[0]=email;
            temp[1]="Owner";
            temp[2]="Bidded";
	        temp[3]="Status";
            getBidStall.execute(temp);
            StallAry = getBidStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        OwnStalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            EditText lv = (EditText)findViewById(R.id.EmailET);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickStall = new Intent(view.getContext(), EditStall.class);
                Stalls entry = (Stalls)OwnStalls.getItemAtPosition(position);
                clickStall.putExtra("entry", entry);
                clickStall.putExtra("id", position);
                startActivity(clickStall);
            }
        });
        OwnStalls.setAdapter(new AdapterEditStall(this, R.layout.account_stalls, StallAry));
    }
}
