package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.TestMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
                Intent clickStall = new Intent(view.getContext(), EditStall.class);
                String entry = MyStalls.getItemAtPosition(position).toString();
                clickStall.putExtra("entry", entry);
                clickStall.putExtra("id", position);
                startActivity(clickStall);
            }
        });
        ArrayAdapter<Stalls> adapter = new ArrayAdapter<Stalls>(this,
                R.layout.account_stalls,StallAry);
        MyStalls.setAdapter(adapter);

    }

    public void addStall(View view){
        Intent intent = new Intent(this,AddStall.class);
        startActivity(intent);
    }

    public void profile(View view){
        Intent intent = new Intent(this,Profile.class);
        startActivity(intent);
    }

}
