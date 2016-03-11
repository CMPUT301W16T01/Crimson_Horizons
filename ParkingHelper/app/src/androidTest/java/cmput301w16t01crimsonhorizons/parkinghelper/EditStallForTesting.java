package cmput301w16t01crimsonhorizons.parkinghelper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditStallForTesting extends EditStall {
    private Commands command;
    private Stalls s1;
    public EditStallForTesting(Commands command,Stalls s1){
        command=command;
    }
    @Override
    public void saveStallInformation(View view){
        EditText title = (EditText)findViewById(R.id.NamePrompEditStall);
        EditText description = (EditText)findViewById(R.id.DescriptionPrompEditStall);
        EditText status = (EditText)findViewById(R.id.StatusEditStallEv);
        String newTitle = title.getText().toString();
        String newDescription = description.getText().toString();
        String newStatus = status.getText().toString();
        s1.setDescription(newDescription);
        s1.setStatus(newStatus);
        s1.setOwner(newTitle);
        command = new CommandForTesting(s1);
    }

}
