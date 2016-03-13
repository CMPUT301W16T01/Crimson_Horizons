package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * This is where the user can sign up or login.
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //Here is to create database with one thing.
/*
        Account a1 = new Account("ABC","123456","workphone");
        AsyncTask<Account, Void, Boolean> execute = new ElasticSearchCtr.addUser().execute(a1);
        setResult(RESULT_OK);
        Account a2 = new Account("123@123","123456","workphone");
        AsyncTask<Account, Void, Boolean> execute2 = new ElasticSearchCtr.addUser().execute(a2);
        setResult(RESULT_OK);
*/

/*        Stalls s1 = new Stalls();
        s1.setOwner("123@123");
        s1.setStatus("Available");
        s1.setDescription("the first stall owned by 123@123");
        AsyncTask<Stalls, Void, Void> s1execute = new ElasticSearchCtr.MakeStall().execute(s1);
        setResult(RESULT_OK);*/
/*        Stalls s2 = new Stalls();
        s2.setOwner("123@123");
        s2.setStatus("Available");
        s2.setDescription("the second stall owned by 123@123");
        AsyncTask<Stalls, Void, Void> s2execute = new ElasticSearchCtr.MakeStall().execute(s2);
        setResult(RESULT_OK);*/
/*
        Stalls s3 = new Stalls();
        s3.setOwner("ABC");
        s3.setStatus("Available");
        s3.setDescription("the seccond stall owned by ABC");
        AsyncTask<Stalls, Void, Void> s3execute = new ElasticSearchCtr.MakeStall().execute(s3);
        setResult(RESULT_OK);
        Stalls s4 = new Stalls();
        s4.setOwner("123@123");
        s4.setStatus("Available");
        s4.setDescription("the second stall owned by 123@123");
        AsyncTask<Stalls, Void, Void> s4execute = new ElasticSearchCtr.MakeStall().execute(s4);
        setResult(RESULT_OK);*/

    }
    @Override
    protected void onStart(){
        super.onStart();
        new CurrentAccount();
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
