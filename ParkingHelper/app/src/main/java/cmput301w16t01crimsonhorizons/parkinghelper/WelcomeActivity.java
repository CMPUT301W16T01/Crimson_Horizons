package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //Here is to create database with one thing.
        ArrayList<Stalls>StAry = new ArrayList<>();
        Stalls s1 = new Stalls();
        s1.setOwner("second");
        s1.setStatus("another account");
        StAry.add(s1);
        Account a1 = new Account("ABC","123456","workphone",StAry);
        AsyncTask<Account, Void, Void> execute = new ElasticSearchCtr.MakeDatabase().execute(a1);
        setResult(RESULT_OK);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickLogin(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void clickSignin(View view){
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
    }
}
