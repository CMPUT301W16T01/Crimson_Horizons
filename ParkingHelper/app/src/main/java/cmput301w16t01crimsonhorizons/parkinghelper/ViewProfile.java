package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by schuman on 3/2/16.
 */
public class ViewProfile extends AppCompatActivity {
    private TextView emailAddress;
    private TextView workNumber;
    private TextView cellNumber;
    private Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_profile);

        Intent intent = getIntent();

        account = ((Account)intent.getSerializableExtra("account"));

        emailAddress = (TextView)findViewById(R.id.emailAddress);
        workNumber = (TextView)findViewById(R.id.workNumber);
        cellNumber = (TextView)findViewById(R.id.cellNumber);

        emailAddress.setText(account.getEmail());
        workNumber.setText(account.getWorkPhone());
        cellNumber.setText(account.getCellPhone());
    }
}
