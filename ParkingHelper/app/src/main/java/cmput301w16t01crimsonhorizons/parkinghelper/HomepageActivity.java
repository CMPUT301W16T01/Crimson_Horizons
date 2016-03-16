package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }
    @Override
    protected void onStart(){
        super.onStart();
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        //TODO: Get the following lines refactored into login activity
        ElasticSearchCtr.GetAccount getAccount = new ElasticSearchCtr.GetAccount();
        try {
            getAccount.execute(email);
            CurrentAccount.setAccount(getAccount.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
        intent.putExtra("account",CurrentAccount.getAccount());
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
        intent.putExtra("email", email);
        startActivity(intent);
    }

    public void clickBorrowing(View view){
        Intent intent = new Intent(getApplicationContext(),BorrowStallActivity.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }

}
