package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SigninActivity extends AppCompatActivity {
    private Account userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void clickCancel(View view){
        finish();
    }

    public void clickSignin(View view){

        //Here is assuming that it was able to verify the account

        userAccount.setUsername(findViewById(R.id.SigninUsernameEditTxt).toString());
        userAccount.setEmail(findViewById(R.id.SigninEmailEditTxt).toString());
        userAccount.setWorkPhone(findViewById(R.id.SigninHomePhonEditTxt).toString());
        userAccount.setUsername(findViewById(R.id.SigninCellPhonEditTxt).toString());

        if (ElasticSearchCtr.addUser(userAccount)){
            Intent intent = new Intent(this,HomepageActivity.class);
            startActivity(intent);
        } else {
            //TODO: make it display a pop-up error informing the user that the username already exists
        }
    }

}
