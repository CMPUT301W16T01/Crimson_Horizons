package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class HomepageActivity extends AppCompatActivity {
    static String USERNAME;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final String USERNAME = intent.getStringExtra("username");
        setContentView(R.layout.activity_homepage);
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

    public void clickAccount(View view){
        Intent intent = new Intent(this,AccountActivity.class);
        intent.putExtra("username",USERNAME);
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
