package cmput301.jero1994testcases;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BidActivity extends AppCompatActivity {

    private int itemIndex;
    private int bidIndex;
    private Bid bid;
    private Button acceptButton;
    private Button declineButton;
    private Button backButton;

    private static BidActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid);

        Bundle extras = getIntent().getExtras();
        itemIndex = (int)(extras.get("ItemIndex"));
        bidIndex = (int)(extras.get("BidIndex"));

        bid = TestData.testUser.GetThingsWithBids().get(itemIndex).GetBids().get(bidIndex);

        instance = this;

        acceptButton = (Button)findViewById(R.id.btnAccept);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                accept();
            }
        });
        declineButton = (Button)findViewById(R.id.btnDecline);
        declineButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                decline();
            }
        });
        backButton = (Button)findViewById(R.id.btnBack3);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void accept() {
        TestData.testUser.AcceptBid(bid);
        finish();
    }
    private void decline() {
        TestData.testUser.RejectBid(bid);
        finish();
    }

    public static BidActivity GetInstance() { return instance; }
}
