package cmput301w16t01crimsonhorizons.parkinghelper;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

public class AddStall extends AppCompatActivity {
    protected Stalls stall;
    static final int REQUEST_IMAGE_CAPTURE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_stall);
    }
    public void saveStallInformation(View view){
        stall = new Stalls();
        EditText description = (EditText)findViewById(R.id.DescriptionET);
        EditText longitude = (EditText)findViewById(R.id.longitudeAddStallET);
        EditText latitude = (EditText)findViewById(R.id.latitudeAddStallET);
        ImageView picture = (ImageView)findViewById(R.id.addStallImage);
        Double[] location_double = new Double[2];

        try {
            location_double[0] = Double.parseDouble(longitude.getText().toString());
            location_double[1] = Double.parseDouble(latitude.getText().toString());
        }catch (Exception e){
            location_double[0]=0.00;
            location_double[1]=0.00;
        }
        String newDescription = description.getText().toString();
        stall.setOwner(CurrentAccount.getAccount().getEmail());
        stall.setDescription(newDescription);
        stall.setStatus("Available");
        stall.setLocation(location_double);

        //http://stackoverflow.com/questions/8306623/get-bitmap-attached-to-imageview
        if(picture.getDrawable() == null)
            stall.setThumbnail(null);
        else
            stall.setThumbnail(((BitmapDrawable) picture.getDrawable()).getBitmap());

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            AsyncTask<Stalls, Void, Void> s1execute = new ElasticSearchCtr.MakeStall().execute(stall);
            setResult(RESULT_OK);
        } else {
            ArrayList<Stalls> allStalls = CurrentStalls.getCurrentStalls();
            allStalls.add(stall);
            CurrentStalls.setCurrentStalls(allStalls);
            OfflineIO io = new OfflineIO();
            io.StoreStall(allStalls, this);
            ArrayList<Stalls>addStalls = io.LoadStallsToAdd(this);
            addStalls.add(stall);
            io.StoreStallsToAdd(addStalls,this);
        }
        finish();
    }
    public void map(View view){
        String query = "geo:0,0?q=0,0";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(query));
        startActivity(intent);
    }

    public void takePicture(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }

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

}
