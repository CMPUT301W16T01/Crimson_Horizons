package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.concurrent.ExecutionException;

public class HomepageActivity extends AppCompatActivity {
    static Account ACCOUNT;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ElasticSearchCtr.GetAccount getAccount = new ElasticSearchCtr.GetAccount();
        try{
            String email = intent.getStringExtra("email");
            getAccount.execute(email);
            ACCOUNT = getAccount.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_homepage);
    }

    public void clickAccount(View view){
        Intent intent = new Intent(this,AccountActivity.class);
        intent.putExtra("account",ACCOUNT);
        startActivity(intent);
    }

    public void clickSearch(View view){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }
    public void clickBidsOnStall(View view){
        Intent intent = new Intent(this, OwnStallsWithBidsActivity.class);
        startActivity(intent);
    }

    public void clickYourBids(View view){
        Intent intent = new Intent(this,YourBids.class);
        startActivity(intent);
    }

}
