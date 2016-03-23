package cmput301w16t01crimsonhorizons.parkinghelper;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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
        stall.setThumbnail(((BitmapDrawable) picture.getDrawable()).getBitmap());


        AsyncTask<Stalls, Void, Void> s1execute = new ElasticSearchCtr.MakeStall().execute(stall);
        setResult(RESULT_OK);
        finish();
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
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            options.inMutable = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            //options.
            Bitmap bigThumbnail = (Bitmap) extras.get("data");
            //BitmapFactory.
            //Bitmap bigThumbnail = (Bitmap) extras.get("data");
            int size = bigThumbnail.getByteCount();
            ////bigThumbnail.setHeight();
            //Bitmap thumbnail = Bitmap.createScaledBitmap(bigThumbnail, options.outWidth, options.outHeight, false);

            picture.setImageBitmap(bigThumbnail);
        }
    }

}
