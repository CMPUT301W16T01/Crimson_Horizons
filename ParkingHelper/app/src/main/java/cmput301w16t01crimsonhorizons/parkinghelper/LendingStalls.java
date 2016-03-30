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

/**
 * Created by schuman on 2/26/16.
 */
public class LendingStalls extends AppCompatActivity {
    private ListView eachLendingStalls;
    private Account account;
    AdapterEditStall myAdapter;
    ArrayList<Stalls>StallAry = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_lending_stalls);
//        Intent intent = getIntent();
//        ArrayList<String> all = intent.getStringArrayListExtra("array");
//        eachLendingStalls = (ListView)findViewById(R.id.lendingStallsList);
//        eachLendingStalls.setAdapter(new CustomLstAdapter(this, R.layout.bids_for_stalls,
//                all));
        account = CurrentAccount.getAccount();
        eachLendingStalls = (ListView)findViewById(R.id.lendingStallsList);
        String email = account.getEmail();
        ElasticSearchCtr.GetLendedStall getLendedStall = new ElasticSearchCtr.GetLendedStall();
        String[]temp = new String[4];
        temp[0]=email;
        temp[1]="Owner";
        temp[2]="Borrowed";
        temp[3]="Status";
        try {
            ArrayList<Stalls>tempAry = new ArrayList<>();
            getLendedStall.execute(temp);
            tempAry = getLendedStall.get();
            StallAry.clear();
            StallAry.addAll(tempAry);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        eachLendingStalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            EditText lv = (EditText)findViewById(R.id.EmailET);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickStall = new Intent(getApplicationContext(), ReturningStallActivity.class);
                Stalls stall = (Stalls)eachLendingStalls.getItemAtPosition(position);
                clickStall.putExtra("stall", stall);
                startActivity(clickStall);

            }
        });
        myAdapter = new AdapterEditStall(this, R.layout.account_stalls, StallAry);
        eachLendingStalls.setAdapter(myAdapter);
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
        ElasticSearchCtr.GetLendedStall getLendedStall = new ElasticSearchCtr.GetLendedStall();
        String[]temp = new String[4];
        temp[0]=email;
        temp[1]="Owner";
        temp[2]="Borrowed";
        temp[3]="Status";
        try {
            ArrayList<Stalls>tempAry = new ArrayList<>();
            getLendedStall.execute(temp);
            tempAry = getLendedStall.get();
            StallAry.clear();
            StallAry.addAll(tempAry);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        eachLendingStalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            EditText lv = (EditText) findViewById(R.id.EmailET);

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickStall = new Intent(view.getContext(), ReturningStallActivity.class);
                Stalls stall = (Stalls) eachLendingStalls.getItemAtPosition(position);
                clickStall.putExtra("stall", stall);
                startActivity(clickStall);
            }
        });
        myAdapter.notifyDataSetChanged();
    }
}
