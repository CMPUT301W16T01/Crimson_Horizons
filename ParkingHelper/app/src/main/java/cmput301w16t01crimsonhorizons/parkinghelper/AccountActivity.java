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

/**
 * Created by Kevin
 * This is where it will display all the stalls the user owns.
 */
public class AccountActivity extends AppCompatActivity {
    /**
     * This are the variables that exists in this activity
     * MyStall is the listview for all stalls
     * Intent contains the email
     * Account is the current account for user.
     */
    private ListView MyStalls;
    private Account account;
    AdapterEditStall myAdapter;
    ArrayList<Stalls>StallAry = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        account = CurrentAccount.getAccount();
        setContentView(R.layout.activity_account_acitivity);
        MyStalls = (ListView)findViewById(R.id.OwnStalls);

    }
    @Override
    protected void onStart(){
        super.onStart();
        //Retrieve the email from homepage and use elastic search to get the account
        //After it gets the stalls for that account and store it.
        //It will set the adapter with this list of stalls.
        this.update();

    }

    /**
     * If the user hits the add button
     * @param view
     */
    public void addStall(View view){
        Intent intent = new Intent(this,AddStall.class);
        startActivity(intent);
        this.update();
    }

    /**
     * If the user hits the profile button
     * @param view
     */
    public void profile(View view){
        Intent intent = new Intent(this,Profile.class);
        startActivity(intent);
        this.update();
    }

    /**
     * This is to conform with MVC. It will be called when it needs to update it self.
     */
    public void update(){
        account = CurrentAccount.getAccount();
        String email = account.getEmail();
        ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
        String[]temp = new String[2];
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
