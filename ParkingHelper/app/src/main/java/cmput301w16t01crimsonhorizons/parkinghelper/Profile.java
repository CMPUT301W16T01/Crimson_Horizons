package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

/**
 * The profile activity is an activity for viewing the user's current profile. Within the the
 * profile the user can change their email, work phone, and cell phone. The save method will only
 * succeed if the email is not being used in the data base. There is also another button that
 * changes the user's activity to the notification view. If the user attempts to change their email
 * address to that of an already known user then the system displays a pop-up error.
 *
 *<p><code>Profile</code> is designed to hold the user's account information, change that
 * information, or to switch to the notification activity</p>
 *
 * @author Aaron Schuman
 */
public class    Profile extends AppCompatActivity {
    private String ProfileOriginalEmail;
    private EditText ProfileEmailET;
    private EditText ProfileCellPhoneET;
    private EditText ProfileWorkPhoneET;
    private Account  userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/


        //needs to display the user's current data

        userAccount = CurrentAccount.getAccount();
        ProfileOriginalEmail = CurrentAccount.getAccount().getEmail();

        ProfileEmailET = (EditText) findViewById(R.id.EmailET);
        ProfileEmailET.setText(CurrentAccount.getAccount().getEmail());
        ProfileCellPhoneET = (EditText) findViewById(R.id.CellPhoneET);
        ProfileCellPhoneET.setText(CurrentAccount.getAccount().getCellPhone());
        ProfileWorkPhoneET = (EditText) findViewById(R.id.WorkPhoneET);
        ProfileWorkPhoneET.setText(CurrentAccount.getAccount().getWorkPhone());



        Button notificationButton = (Button) findViewById(R.id.NotificationBtn);
        notificationButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                notifications();
            }
        });

        //<updateUserClass, verifyUserNameClass>
        Button saveButton = (Button) findViewById(R.id.SaveInProfileBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                ElasticSearchCtr.updateUser typeB =new ElasticSearchCtr.updateUser();
                ElasticSearchCtr.verifyUserName typeA = new ElasticSearchCtr.verifyUserName();
                save(typeA, typeB);
                finish();
            }
        });


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }


    /**
     * Changes the current activity to the notification activity
     * .@param ()
     * @return null
     */
    public void notifications(){
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    /**
     * Sets the user's profile info to the editText typed into the various fields
     * .@param ()
     * @return null
     * if the elastic search finds that <code>ProfileEmailET</code> already exists in the database
     */
    public <elasticVerify extends AsyncTask<String, Void, Boolean>, elasticUpdate extends AsyncTask<Account, Void, Boolean>> void save(elasticVerify typeA, elasticUpdate typeB){

        userAccount = CurrentAccount.getAccount();

        final elasticVerify executeVerify = typeA;// = new elasticVerify();
        final elasticUpdate executeUpdate = typeB;
        //final ElasticSearchCtr.verifyUserName executeVerify = new ElasticSearchCtr.verifyUserName();
        //final ElasticSearchCtr.updateUser executeUpdate = new ElasticSearchCtr.updateUser();

        String Temp = ProfileEmailET.getText().toString();

        try {
            Boolean result = executeVerify.execute(ProfileEmailET.getText().toString()).get();
            if (!result || ProfileEmailET.getText().toString().equals(ProfileOriginalEmail)){
                //with current method ALL of the stalls will need to get updated too
                /*if(executeDelete.execute(userAccount).get()) {
                    System.out.print("DELETED\n");
                }*/
                userAccount.setEmail(ProfileEmailET.getText().toString());
                userAccount.setWorkPhone(ProfileWorkPhoneET.getText().toString());
                userAccount.setCellPhone(ProfileCellPhoneET.getText().toString());
                executeUpdate.execute(userAccount);
                ProfileOriginalEmail = userAccount.getEmail().toString();


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
