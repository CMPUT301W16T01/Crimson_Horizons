package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created By Kevin
 *
 * This is where the user is once logined.
 * Can navigate to other activity
 * @see AccountActivity
 * @see Search
 * @see BidStall
 * @see Results
 */

public class HomepageActivity extends AppCompatActivity {
    private String email;
    private ArrayList<Stalls>stallsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        OfflineIO io = new OfflineIO();
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            ElasticSearchCtr.GetAccount getAccount = new ElasticSearchCtr.GetAccount();
            ElasticSearchCtr.GetStall getStall = new ElasticSearchCtr.GetStall();
            String[] query = new String[2];
            query[0]=email;
            query[1]="Owner";
            getStall.execute(query);
            getAccount.execute(email);
            try {
                Account account = getAccount.get();
                ArrayList<Stalls> stallsArrayList = getStall.get();
                new CurrentAccount();
                new CurrentStalls();

                CurrentStalls.setCurrentStalls(stallsArrayList);
                io.StoreStall(stallsArrayList,this);

                CurrentAccount.setAccount(account);
                io.StoreUser(account, this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            CurrentAccount.setAccount(io.LoadUser(this));
            CurrentStalls.setCurrentStalls(io.LoadStalls(this));
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    /**
     * This will create intent with the current account and start that intent
     * Same is done for other button clicks
     *
     * .@param view
     */
    public void clickAccount(View view){
        //Intent intent = new Intent(this, Profile.class);
        Intent intent = new Intent(this,AccountActivity.class);
        intent.putExtra("account", CurrentAccount.getAccount());
        startActivity(intent);
    }

    /**
     * @see this.clickAccount()
     * .@param view
     */
    public void clickSearch(View view){
        Intent intent = new Intent(this, Search.class);
        intent.putExtra("account",CurrentAccount.getAccount());
        startActivity(intent);
    }

    /**
     * @see this.clickAccount()
     * .@param view
     */
    public void clickBidsOnStall(View view){
        Intent intent = new Intent(this, OwnStallsWithBidsActivity.class);
        startActivity(intent);
    }

    /**
     * @see this.clickAccount()
     * .@param view
     */
    public void clickYourBids(View view){
        Intent intent = new Intent(this,Results.class);
        startActivity(intent);
    }

    public void clickLending(View view){
        Intent intent = new Intent(getApplicationContext(),LendingStalls.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }

    public void clickBorrowing(View view){
        Intent intent = new Intent(getApplicationContext(),BorrowStallActivity.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }
    public void clickSignOut(View view){
        OfflineIO io = new OfflineIO();
        io.deleteUserFile(this);
        io.deleteStallFile(this);
        finish();
    }
    public void clickNotifications(View view){
        Intent intent = new Intent(getApplicationContext(),NotificationActivity.class);
        startActivity(intent);
    }
}
