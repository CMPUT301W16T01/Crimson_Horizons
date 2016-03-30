package cmput301w16t01crimsonhorizons.parkinghelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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
    protected Stalls stall_ori = new Stalls();
    private Intent intent;
    static final int REQUEST_IMAGE_CAPTURE = 1234;
    //private Bitmap thumbnail;
    //private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit_stall);
        //UpdateCommand my stall that user clicked
        intent = getIntent();
        stall = (Stalls)intent.getSerializableExtra("entry");
        try {
            stall_ori.setStallID(stall.getStallID());
        }catch(NullPointerException e ){

        }
        stall_ori.setOwner(stall.getOwner());
        stall_ori.setBidAmt(stall.getBidAmt());
        stall_ori.setDescription(stall.getDescription());
        int pos = intent.getIntExtra("id",-1);

        //Set all the fields
        EditText title = (EditText)findViewById(R.id.NamePrompEditStall);
        EditText description = (EditText)findViewById(R.id.DescriptionPrompEditStall);
        EditText status = (EditText)findViewById(R.id.StatusEditStallEv);
        EditText longitude = (EditText)findViewById(R.id.longitudeEditStallET);
        EditText latitude = (EditText)findViewById(R.id.latitudeEditStallET);
        ImageView picture = (ImageView)findViewById(R.id.editStallImage);
        title.setText(stall.getOwner());
        status.setText(stall.getStatus());
        description.setText(stall.getDescription());
        longitude.setText(stall.getLocation()[0].toString());
        latitude.setText(stall.getLocation()[1].toString());
        picture.setImageBitmap(stall.getThumbnail());
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
        ImageView picture = (ImageView)findViewById(R.id.editStallImage);
        Double[] location_double = new Double[2];
        location_double[0]=Double.parseDouble(longitude.getText().toString());
        location_double[1]=Double.parseDouble(latitude.getText().toString());
        String newTitle = title.getText().toString();
        String newDescription = description.getText().toString();
        String newStatus = status.getText().toString();
        Bitmap thumbnail = ((BitmapDrawable)picture.getDrawable()).getBitmap();
        stall.setDescription(newDescription);
        stall.setStatus(newStatus);
        stall.setOwner(newTitle);
        stall.setLocation(location_double);
        stall.setThumbnail(thumbnail);
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
            ArrayList<Stalls> allStalls = CurrentStalls.getCurrentStalls();
            int idx = -1;
            int idxTraverse = allStalls.size()-1;
            while(idxTraverse>=0){
                if (this.compare(allStalls.get(idxTraverse), stall_ori)){
                    idx=idxTraverse;
                    break;
                } else {
                    idxTraverse=idxTraverse-1;
                }
            }
            if (allStalls.get(idxTraverse).getStallID()!=null) {
                allStalls.set(idx, stall);
                CurrentStalls.setCurrentStalls(allStalls);
                io.StoreStall(allStalls, this);
                ArrayList<Stalls> updateStalls = io.LoadStallsUpdate(this);
                updateStalls.add(stall);
                io.StoreStallsToUpdate(updateStalls, this);
            } else {
                allStalls.set(idx, stall);
                CurrentStalls.setCurrentStalls(allStalls);
                io.StoreStall(allStalls, this);
                ArrayList<Stalls> addStalls = io.LoadStallsToAdd(this);
                addStalls.add(stall);
                io.StoreStallsToAdd(addStalls,this);
            }
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
        ImageView picture = (ImageView)findViewById(R.id.editStallImage);
        title.setText(stall.getOwner());
        status.setText(stall.getStatus());
        description.setText(stall.getDescription());
        picture.setImageBitmap(stall.getThumbnail());
    }

    public void deletePicture(View view){
        ImageView picture = (ImageView)findViewById(R.id.editStallImage);
        picture.setImageBitmap(null);
        stall.setThumbnail(null);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data .getExtras();
            ImageView picture = (ImageView)findViewById(R.id.addStallImage);
            Bitmap bigThumbnail = (Bitmap) extras.get("data");

        //Taken from:https://github.com/CMPUT301F15T07/TradingApp/blob/master/SSCTE/app/src/main/java/com/sherpasteven/sscte/Models/Image.java#L53https://github.com/CMPUT301F15T07/TradingApp/blob/master/SSCTE/app/src/main/java/com/sherpasteven/sscte/Models/Image.java#L53
            Double width = (double) bigThumbnail.getWidth();
            Double height = (double) bigThumbnail.getHeight();
            Double max = 120.0;

            if (width > height) {
                height = max * (height / width);
                width = max;
            } else {
                width = max * (width / height);
                height = max;
            }

            Bitmap thumbnail = Bitmap.createScaledBitmap(bigThumbnail, width.intValue(), height.intValue(), false);

            bigThumbnail.recycle();
            bigThumbnail = null;

            picture.setImageBitmap(thumbnail);
        }
    }



    public void reviews(View view){
        Intent intent = new Intent (this,ReviewsActivity.class);
        startActivity(intent);
    }
    public Boolean compare(Stalls s1,Stalls s2){
        try {
            if ((s1.getDescription().equals(s2.getDescription())) && (s1.getOwner().equals(s2.getOwner()))
                    && (s1.getStallID().equals(s2.getStallID())) && (s1.getBidder().equals(s2.getBidder()))) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e){
            if ((s1.getDescription().equals(s2.getDescription())) && (s1.getOwner().equals(s2.getOwner()))
                   && (s1.getBidder().equals(s2.getBidder()))) {
                return true;
            } else {
                return false;
            }
        }
    }
}
