package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class EditStall extends AppCompatActivity {
    private Stalls stall;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stall);
        //Retrieve my stall that user clicked
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

        //Set all the fields
        EditText title = (EditText)findViewById(R.id.NamePrompEditStall);
        EditText description = (EditText)findViewById(R.id.DescriptionPrompEditStall);
        EditText status = (EditText)findViewById(R.id.StatusEditStallEv);
        title.setText(stall.getOwner());
        status.setText(stall.getStatus());
        description.setText(stall.getDescription());
    }

    public void saveStallInformation(View view){
        EditText title = (EditText)findViewById(R.id.NamePrompEditStall);
        EditText description = (EditText)findViewById(R.id.DescriptionPrompEditStall);
        EditText status = (EditText)findViewById(R.id.StatusEditStallEv);
        String newTitle = title.getText().toString();
        String newDescription = description.getText().toString();
        String newStatus = status.getText().toString();
        Stalls nStall = new Stalls();
        nStall.setDescription(newDescription);
        nStall.setStatus(newStatus);
        nStall.setOwner(newTitle);
        nStall.setStallID(stall.getStallID());
        Commands command = new EditStallSave(nStall);
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

}
