package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    static final int REQUEST_IMAGE_CAPTURE = 1234;
    private Bitmap thumbnail;
    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit_stall);
        //UpdateCommand my stall that user clicked
        intent = getIntent();
        stall = (Stalls)intent.getSerializableExtra("entry");
        int pos = intent.getIntExtra("id",-1);

        picture = (ImageView) findViewById(R.id.editStallImage);

        //Set all the fields
        EditText title = (EditText)findViewById(R.id.NamePrompEditStall);
        EditText description = (EditText)findViewById(R.id.DescriptionPrompEditStall);
        EditText status = (EditText)findViewById(R.id.StatusEditStallEv);
        EditText longitude = (EditText)findViewById(R.id.longitudeEditStallET);
        EditText latitude = (EditText)findViewById(R.id.latitudeEditStallET);
        title.setText(stall.getOwner());
        status.setText(stall.getStatus());
        description.setText(stall.getDescription());
        longitude.setText(stall.getLocation()[0].toString());
        latitude.setText(stall.getLocation()[1].toString());
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
        EditText longitude = (EditText)findViewById(R.id.longitudeEditStallET);
        EditText latitude = (EditText)findViewById(R.id.latitudeEditStallET);
        Double[] location_double = new Double[2];
        location_double[0]=Double.parseDouble(longitude.getText().toString());
        location_double[1]=Double.parseDouble(latitude.getText().toString());
        String newTitle = title.getText().toString();
        String newDescription = description.getText().toString();
        String newStatus = status.getText().toString();
        stall.setDescription(newDescription);
        stall.setStatus(newStatus);
        stall.setOwner(newTitle);
        stall.setLocation(location_double);
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            Commands command = new EditStallSave(stall);
            Boolean check = command.execute();
            if (check) {
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "didn't save", Toast.LENGTH_SHORT).show();
            }
        } else {
            OfflineIO io = new OfflineIO();
            ArrayList<Stalls>allStalls = CurrentStalls.getCurrentStalls();
            int idx = -1;
            int idxTraverse = allStalls.size()-1;
            while(idxTraverse>=0){
                if (allStalls.get(idxTraverse).getStallID().equals(stall.getStallID())){
                    idx=idxTraverse;
                    break;
                } else {
                    idxTraverse=idxTraverse-1;
                }
            }
            allStalls.set(idx, stall);
            CurrentStalls.setCurrentStalls(allStalls);
            io.StoreStall(allStalls,this);
            ArrayList<Stalls>updateStalls = new ArrayList<>();
            updateStalls.add(stall);
            io.StoreStallsToUpdate(updateStalls, this);
        }
        finish();
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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data .getExtras();
            thumbnail = (Bitmap) extras.get("data");
            picture.setImageBitmap(thumbnail);
        }
    }

    public void reviews(View view){
        Intent intent = new Intent (this,ReviewsActivity.class);
        startActivity(intent);
    }

}
