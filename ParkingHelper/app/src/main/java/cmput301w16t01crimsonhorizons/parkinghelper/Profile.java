package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
public class Profile extends AppCompatActivity {
    private EditText OriginalEmailET;
    private EditText EmailET;
    private EditText CellPhoneET;
    private EditText WorkPhoneET;
    private CurrentAccount  userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //needs to display the user's current data

        userAccount = new CurrentAccount();

        EmailET = (EditText) findViewById(R.id.EmailET);
        EmailET.setText(userAccount.getEmail());
        OriginalEmailET.setText(userAccount.getEmail());
        CellPhoneET = (EditText) findViewById(R.id.CellPhoneET);
        CellPhoneET.setText(userAccount.getCellPhone());
        WorkPhoneET = (EditText) findViewById(R.id.WorkPhoneET);
        WorkPhoneET.setText(userAccount.getWorkPhone());



        Button notificationButton = (Button) findViewById(R.id.NotificationBtn);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifications();
            }
        });

        Button saveButton = (Button) findViewById(R.id.SaveInProfileBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
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
     * Changes the current activity to the notification activity
     * @param ()
     * @return null
     */
    public void notifications(){
        Intent intent = new Intent(this, Notifications.class);
        startActivity(intent);
    }

    /**
     * Sets the user's profile info to the editText typed into the various fields
     * @param ()
     * @return null
     * if the elastic search finds that <code>EmailET</code> already exists in the database
     */
    public void save(){
        userAccount.setEmail(EmailET.toString());
        userAccount.setWorkPhone(WorkPhoneET.toString());
        userAccount.setCellPhone(CellPhoneET.toString());

        ElasticSearchCtr.updateUser elasticSearchCtr = new ElasticSearchCtr.updateUser();
        if (elasticSearchCtr.doInBackground(userAccount)){

        } else {
            //TODO: make it display a pop-up error informing the user that the username already exists
        }
    }

}
