package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditStall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stall);
        //Retrieve my stall that user clicked
        Intent intent = getIntent();
        Stalls stall = (Stalls)intent.getSerializableExtra("entry");
        int pos = intent.getIntExtra("id",-1);

        //Set all the fields
        EditText title = (EditText)findViewById(R.id.NamePrompEditStall);
        EditText description = (EditText)findViewById(R.id.DescriptionPrompEditStall);
        title.setText(stall.getOwner());
        description.setText(stall.getDescription());
    }

    public void saveStallInformation(View view){

    }

}
