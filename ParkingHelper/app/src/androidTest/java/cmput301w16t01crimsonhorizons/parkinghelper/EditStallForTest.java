package cmput301w16t01crimsonhorizons.parkinghelper;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin L on 3/7/2016.
 */
public class EditStallForTest extends EditStall {
    private Stalls stall;
    @Override
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
        ElasticSearchForTest.updateStallES updateStallES = new ElasticSearchForTest.updateStallES();
        updateStallES.execute(stall);
        try {
            updateStallES.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
