package cmput301w16t01crimsonhorizons.parkinghelper;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by Kevin
 * This is where user needs to be if user wants to bid on a stall
 */
public class BidStall extends AppCompatActivity {
    private Stalls stall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_bid_stall);
        //Retrieve stalls and set all information.
        Intent intent = getIntent();
        stall = (Stalls) intent.getSerializableExtra("stall");
        TextView HighestBid = (TextView)findViewById(R.id.BidStallHighestBidDisp);
        TextView Owner = (TextView)findViewById(R.id.BidStallNameDisp);
        TextView Descrip = (TextView)findViewById(R.id.BidStallDescriptionDisp);
        TextView latitude = (TextView)findViewById(R.id.latitudeBiStallTV);
        TextView longtitude = (TextView)findViewById(R.id.longitudeBidStallTV);
        HighestBid.setText(stall.getHighBidAmount().toString());
        Owner.setText(stall.getOwner().toString());
        Descrip.setText(stall.getDescription().toString());
        longtitude.setText(stall.getLocation()[0].toString());
        latitude.setText(stall.getLocation()[1].toString());
    }

    /**
     * This is when the suer enters all information and wants to bid on it.
     * It will do a check to make sure the user's bid is higher than highest bid
     * If so, it will modify.
     * After each action, there will be a message to display what was done.
     * .@param view
     */

    public void BidStall(View view){
        Account account = CurrentAccount.getAccount();
        NumberFormat two = new DecimalFormat("0.##");
        EditText UserBid = (EditText)findViewById(R.id.BidStallAmtET);
        Double BidAmt = Double.valueOf(two.format(Double.parseDouble(UserBid.getText().toString())));

        if (BidAmt<=stall.getHighBidAmount()){
            Toast.makeText(BidStall.this,"Your bid was too low", Toast.LENGTH_SHORT).show();
        } else if (stall.getStatus() == "Borrowed") {
            Toast.makeText(BidStall.this,"Stall unavailable", Toast.LENGTH_SHORT).show();
        } else {
            stall.setStatus("Bidded");
            Commands command = new EditStallSave(stall);
            Boolean check = command.execute();
            String bidderInfo = CurrentAccount.getAccount().getEmail()+" "+BidAmt;
            ElasticSearchCtr.MakeBid makeBid = new ElasticSearchCtr.MakeBid();
            makeBid.execute(new Bid(CurrentAccount.getAccount().getEmail(),
                    BidAmt, stall.getStallID()));
            Toast.makeText(BidStall.this, "You have made the bid!", Toast.LENGTH_SHORT).show();
            DateFormat dateformat = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
            Date date = new Date();
            NotificationObject notification = new NotificationObject();
            notification.setOwner(stall.getOwner());
            notification.setBidder(account.getEmail());
            notification.setBidAmt(BidAmt.toString());
            notification.setDate(dateformat.format(date));
            NotificationElasticSearch.AddNotification addNotification = new NotificationElasticSearch.AddNotification();
            addNotification.execute(notification);
            finish();
        }
    }

    /**
     * This is the function called when the direction is clicked on the screen.
     * This is where bidders (potential bidders) can look at the possible stalls
     * and get the idea of where they are on google maps.
     * @param view
     */
    public void getDirection(View view){
        Double lat = stall.getLocation()[1];
        Double lon = stall.getLocation()[0];
        String query = "geo:"+lat.toString()+","+lon.toString()+"?q="+lat.toString()+","+lon.toString();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(query));
        startActivity(intent);
    }
    public void adapterClickUserName(View view){
        ClickUserName clickUserName = new ClickUserName();
        Intent newIntent = clickUserName.clickUserName(this, view, new ElasticSearchCtr.GetAccount());
        startActivity(newIntent);
    }
    public void newReview(View view) {
        Intent intent = new Intent(getApplicationContext(),ReviewsActivity.class);
        intent.putExtra("stall",stall);
        startActivity(intent);
    }
}
