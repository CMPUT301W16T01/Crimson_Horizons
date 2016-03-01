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

public class Profile extends AppCompatActivity {
    private EditText OriginalEmailET;
    private EditText EmailET;
    private EditText CellPhoneET;
    private EditText WorkPhoneET;
    private Account  userAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //needs to display the user's current data
        EmailET = (EditText) findViewById(R.id.EmailET);
        OriginalEmailET = EmailET;
        CellPhoneET = (EditText) findViewById(R.id.CellPhoneET);
        WorkPhoneET = (EditText) findViewById(R.id.WorkPhoneET);

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

    public void notifications(){
        Intent intent = new Intent(this, Notifications.class);
        startActivity(intent);
    }


    public void save(){

        ElasticSearchCtr.GetAccount getAccount = new ElasticSearchCtr.GetAccount();
        userAccount = getAccount.doInBackground(OriginalEmailET.toString());

        if (EmailET == OriginalEmailET || !ElasticSearchCtr.verifyUserName(OriginalEmailET.toString())){
            ElasticSearchCtr.deleteUser(userAccount);
            userAccount.setEmail(EmailET.toString());
            userAccount.setWorkPhone(WorkPhoneET.toString());
            userAccount.setCellPhone(CellPhoneET.toString());
            ElasticSearchCtr.addUser(userAccount);
        } else {
            //TODO: make it display a pop-up error informing the user that the username already exists
        }
    }

}
