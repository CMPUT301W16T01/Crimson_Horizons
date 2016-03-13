package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Kevin
 * This is where user needs to be if user wants to bid on a stall
 */
public class BidStall extends AppCompatActivity {
    //These are variables required for this activity
    // Stall is the stall it has
    //Intent contains the stall retrieved from searchActivity.
    private Intent intent;
    private Stalls stall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_stall);
        //Retrieve stalls and set all information.
        intent = getIntent();
        stall = (Stalls)intent.getSerializableExtra("stall");
        TextView HighestBid = (TextView)findViewById(R.id.BidStallHighestBidDisp);
        TextView Owner = (TextView)findViewById(R.id.BidStallNameDisp);
        TextView Descrip = (TextView)findViewById(R.id.BidStallDescriptionDisp);
        Double temp = Double.valueOf(stall.getBidAmt());
        if (temp==null){
            temp = 0.00;
        }
        HighestBid.setText(temp.toString());
        Owner.setText(stall.getOwner().toString());
        Descrip.setText(stall.getDescription().toString());
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

        if (BidAmt<=stall.getBidAmt()){
            Toast.makeText(BidStall.this,"Your bid was too low", Toast.LENGTH_SHORT).show();
        } else {
            stall.setBidAmt(BidAmt);
            stall.setBidder(account.getEmail());
            Commands command = new EditStallSave(stall);
            Boolean check = command.execute();
            if (check){
                Toast.makeText(BidStall.this, "You have made the bid!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(BidStall.this, "Have not made bid!",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
