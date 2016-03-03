package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {
    private ListView MyStalls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_acitivity);
        MyStalls = (ListView)findViewById(R.id.OwnStalls);
    }
    @Override
    protected void onStart(){
        super.onStart();
        //Retrieve the email from homepage and use elastic search to get the account
        //After it gets the stalls for that account and store it.
        //It will set the adapter with this list of stalls.
        Intent intent = getIntent();
        Account account = (Account) intent.getSerializableExtra("account");
        ArrayList<Stalls>StallAry = account.getOwnStalls();
        MyStalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new ListViewWithUserName(view, position, id, MyStalls, (TextView)findViewById(R.id.EmailET));
                Intent clickStall = new Intent(view.getContext(), EditStall.class);
                String entry = MyStalls.getItemAtPosition(position).toString();
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
