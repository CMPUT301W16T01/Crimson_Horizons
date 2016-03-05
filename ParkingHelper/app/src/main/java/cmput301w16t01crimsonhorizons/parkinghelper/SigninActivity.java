package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

/**
 * The sign-in activity is the activity that allows users to create new accounts, which consist
 * of an email address, work phone number and cellphone number. If the email address already exists
 * then a pop-up error will be displayed to the user.
 *
 * <p><code>SigninActivity</code></p> is designed for creating new accounts,
 * @author Aaron Schuman
 */
public class SigninActivity extends AppCompatActivity {
    private Account userAccount;
    private EditText SigninEmailEditTxt;
    private EditText SigninWorkPhonEditTxt;
    private EditText SigninCellPhonEditTxt;
    private EditText SigninUsernameEditTxt;
    private EditText SigninPasswordEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SigninEmailEditTxt = (EditText) findViewById(R.id.SigninEmailEditTxt);
        SigninWorkPhonEditTxt = (EditText) findViewById(R.id.SigninHomePhonEditTxt);
        SigninCellPhonEditTxt = (EditText) findViewById(R.id.SigninCellPhonEditTxt);
        SigninUsernameEditTxt = (EditText) findViewById(R.id.SigninUsernameEditTxt);
        SigninPasswordEditTxt = (EditText) findViewById(R.id.SigninPasswordEditTxt);

        userAccount = new Account();

        userAccount.setEmail(SigninEmailEditTxt.toString());
        userAccount.setCellPhone(SigninCellPhonEditTxt.toString());
        userAccount.setWorkPhone(SigninWorkPhonEditTxt.toString());


        Button createButton = (Button) findViewById(R.id.CreateAccountBtn);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSignin();
            }
        });

        Button cancelButton = (Button) findViewById(R.id.CancelBtn);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCancel();
            }
        });

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

    /**
     * Changes the current activity to the HomepageActivity activity
     * @param ()
     * @return null
     */
    public void clickCancel(){
        Intent intent = new Intent(this,WelcomeActivity.class);
        startActivity(intent);
    }

    /**
     * Assigns the values currently in the edit text to the account and then uses elastic search
     * to see if the email exists in the database. If there was no email then the activity changes
     * to the HomepageActivity activity, otherwise a pop-up error is displayed.
     * @param ()
     * @return null
     */
    public void clickSignin(){

        //Here is assuming that it was able to verify the account

        userAccount.setEmail(SigninEmailEditTxt.toString());
        userAccount.setWorkPhone(SigninWorkPhonEditTxt.toString());
        userAccount.setCellPhone(SigninCellPhonEditTxt.toString());

        final AsyncTask<String, Void, Boolean> executeVerify = new ElasticSearchCtr.verifyUserName();
        final AsyncTask<Account, Void, Boolean> executeAdd = new ElasticSearchCtr.addUser();


        setResult(RESULT_OK);

        try {
            if (!executeVerify.execute(userAccount.getEmail()).get()){
                executeAdd.execute(userAccount);
                Intent intent = new Intent(this,WelcomeActivity.class);
                startActivity(intent);
            } else {
                //TODO: make it display a pop-up error informing the user that the username already exists
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
