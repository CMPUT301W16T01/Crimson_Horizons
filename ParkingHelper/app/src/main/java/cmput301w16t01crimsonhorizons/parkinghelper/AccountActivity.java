package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AccountActivity extends AppCompatActivity {
    private ListView MyStalls;
    private Intent intent;
    private Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_acitivity);
        MyStalls = (ListView)findViewById(R.id.OwnStalls);
        intent = getIntent();
    }
    @Override
    protected void onStart(){
        super.onStart();
        //Retrieve the email from homepage and use elastic search to get the account
        //After it gets the stalls for that account and store it.
        //It will set the adapter with this list of stalls.
        this.update();

    }

    public void addStall(View view){
        Intent intent = new Intent(this,AddStall.class);
        startActivity(intent);
        this.update();
    }

    public void profile(View view){
        Intent intent = new Intent(this,Profile.class);
        startActivity(intent);
        this.update();
    }

    public void update(){
        account = CurrentAccount.getAccount();
        ArrayList<Stalls>StallAry = new ArrayList<>();
        String email = account.getEmail();
        ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
        try {
            String[]temp = new String[2];
            temp[0]=email;
            temp[1]="Owner";
            getStall.execute(temp);
            StallAry = getStall.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        MyStalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            EditText lv = (EditText)findViewById(R.id.EmailET);
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickStall = new Intent(view.getContext(), EditStall.class);
                Stalls entry = (Stalls)MyStalls.getItemAtPosition(position);
                clickStall.putExtra("entry", entry);
                clickStall.putExtra("id", position);
                startActivity(clickStall);
            }
        });
        MyStalls.setAdapter(new AdapterEditStall(this, R.layout.account_stalls, StallAry));
        Button profileButton = (Button) findViewById(R.id.ProfileBtn);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile();
            }
        });

        Button addButton = (Button) findViewById(R.id.AddBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStall();
            }
        });

    }

    public void addStall(){
        Intent intent = new Intent(this,AddStall.class);
        startActivity(intent);
    }

    public void profile(){
        Intent intent = new Intent(this,Profile.class);
        startActivity(intent);
    }
}
