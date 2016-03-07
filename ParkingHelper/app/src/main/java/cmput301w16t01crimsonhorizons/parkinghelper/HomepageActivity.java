package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomepageActivity extends AppCompatActivity {
    private Account ACCOUNT = new Account("","","",new ArrayList<Stalls>());
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
        ElasticSearchCtr.GetAccount getAccount = new ElasticSearchCtr.GetAccount();
        try {
            getAccount.execute(email);
            ACCOUNT=getAccount.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
        intent.putExtra("account", ACCOUNT);
        startActivity(intent);
    }

}
