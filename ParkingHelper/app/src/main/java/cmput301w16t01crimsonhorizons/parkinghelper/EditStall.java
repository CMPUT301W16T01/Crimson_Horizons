package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Kevin
 * This is to edit the information of a stall the user owns. Got here from
 * AccountActivity
 * Intent retrieved from AccountActivity.
 */
public class EditStall extends AppCompatActivity {
    protected Stalls stall;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stall);
        //UpdateCommand my stall that user clicked
        intent = getIntent();
        stall = (Stalls)intent.getSerializableExtra("entry");
        int pos = intent.getIntExtra("id",-1);

        //Set all the fields
        EditText title = (EditText)findViewById(R.id.NamePrompEditStall);
        EditText description = (EditText)findViewById(R.id.DescriptionPrompEditStall);
        EditText status = (EditText)findViewById(R.id.StatusEditStallEv);
        title.setText(stall.getOwner());
        status.setText(stall.getStatus());
        description.setText(stall.getDescription());
    }
    @Override
    protected void onStart(){
        super.onStart();
        stall = (Stalls)intent.getSerializableExtra("entry");
        int pos = intent.getIntExtra("id",-1);

        this.update();
    }

    @Override
    protected void onResume(){
        super.onResume();
        this.update();
    }

    /**
     * Once the user finish entering information and hit save, it will obtain all information
     * update the information with the stall object then call the command object to update it.
     * .@param view
     */
    public void saveStallInformation(View view){
        EditText title = (EditText)findViewById(R.id.NamePrompEditStall);
        EditText description = (EditText)findViewById(R.id.DescriptionPrompEditStall);
        EditText status = (EditText)findViewById(R.id.StatusEditStallEv);
        String newTitle = title.getText().toString();
        String newDescription = description.getText().toString();
        String newStatus = status.getText().toString();
        stall.setDescription(newDescription);
        stall.setStatus(newStatus);
        stall.setOwner(newTitle);
        Commands command = new EditStallSave(stall);
        Boolean check = command.execute();
        if (check){
            finish();
        } else {
            Toast.makeText(getApplicationContext(),"didn't save",Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteStall(View view){
        Commands deleteStall = new DeleteStall(stall);
        Boolean check = deleteStall.execute();
        if (check){
            finish();
        } else {
            Toast.makeText(getApplicationContext(),"didn't delete",Toast.LENGTH_SHORT).show();
        }
    }

    public void takePicture(View view){
        Intent intent = new Intent();
    }
    public void update(){
        stall = (Stalls)intent.getSerializableExtra("entry");
        int pos = intent.getIntExtra("id",-1);

        //Set all the fields
        EditText title = (EditText)findViewById(R.id.NamePrompEditStall);
        EditText description = (EditText)findViewById(R.id.DescriptionPrompEditStall);
        EditText status = (EditText)findViewById(R.id.StatusEditStallEv);
        title.setText(stall.getOwner());
        status.setText(stall.getStatus());
        description.setText(stall.getDescription());
    }

}
