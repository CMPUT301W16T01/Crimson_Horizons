package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin
 * This is where it will display all the stalls the user owns.
 */
public class AccountActivity extends AppCompatActivity {
    private ListView MyStalls;
    private Account account;
    AdapterEditStall myAdapter;
    String[]temp = new String[2];
    ArrayList<Stalls>StallAry = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        account = CurrentAccount.getAccount();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_account_acitivity);
        account = CurrentAccount.getAccount();
        MyStalls = (ListView)findViewById(R.id.OwnStalls);
        String email = account.getEmail();

        myAdapter = new AdapterEditStall(this, R.layout.account_stalls, StallAry);
        MyStalls.setAdapter(myAdapter);

        ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
        temp[0]=email;
        temp[1]="Owner";
        try {
            //Here it sets up the String[] needed for searching
            getStall.execute(temp);
            ArrayList tmp = getStall.get();
            StallAry.clear();
            StallAry.addAll(tmp);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        MyStalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            EditText lv = (EditText) findViewById(R.id.EmailET);

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickStall = new Intent(view.getContext(), EditStall.class);
                Stalls entry = (Stalls) MyStalls.getItemAtPosition(position);
                clickStall.putExtra("entry", entry);
                clickStall.putExtra("id", position);
                startActivity(clickStall);
                myAdapter.notifyDataSetChanged();
            }
        });
        myAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onStart(){
        super.onStart();
        //Retrieve the email from homepage and use elastic search to get the account
        //After it gets the stalls for that account and store it.
        //It will set the adapter with this list of stalls.
        this.update();

    }

    public void adapterClickUserName(View view){

            /*ElasticSearchCtr.GetAccount executeAccount = new ElasticSearchCtr.GetAccount();
            Account newAccount = null;
            TextView temp = (TextView)view.findViewById(R.id.StallNameEditStallV);
           try {
               newAccount = executeAccount.execute(temp.getText().toString()).get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();

            }finally {*/
                Intent newIntent = new Intent(this, ViewProfile.class);
                //newIntent.putExtra("account", newAccount);
                startActivity(newIntent);
            //}
    }

    /**
     * If the user hits the add button
     * .@param view
     */

    public void addStall(View view){
        Intent intent = new Intent(this,AddStall.class);
        startActivity(intent);
        this.update();
    }


    /**
     * If the user hits the profile button
     * .@param view
     */

    public void profile(View view){
        Intent intent = new Intent(this,Profile.class);
        startActivity(intent);
        this.update();
    }

    public void update(){
        account = CurrentAccount.getAccount();
        String email = account.getEmail();
        ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();

        temp[0]=email;
        temp[1]="Owner";
        try {
            //Here it sets up the String[] needed for searching
            ArrayList<Stalls>tempAry = new ArrayList<>();
            getStall.execute(temp);
            tempAry = getStall.get();
            StallAry.clear();
            StallAry.addAll(tempAry);
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
        myAdapter.notifyDataSetChanged();
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
