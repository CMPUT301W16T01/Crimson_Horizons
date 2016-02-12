package cmput301.jero1994testcases;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class BidListActivity extends AppCompatActivity {

    private ArrayList<Bid> bidList = new ArrayList<Bid>();

    private int index;

    private Button backButton;
    private ArrayAdapter<Bid> adapter;
    private ListView listView;
    private static BidListActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_list);

        Bundle extras = getIntent().getExtras();
        index = (int)(extras.get("ItemIndex"));

        bidList = TestData.testUser.GetThingsWithBids().get(index).GetBids();

        instance = this;

        backButton = (Button) findViewById(R.id.btnBack2);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.bidList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ShowBid(view, position);
            }
        });
        adapter = new ArrayAdapter<Bid>(this, android.R.layout.simple_list_item_1, bidList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bidList = TestData.testUser.GetThingsWithBids().get(index).GetBids();
        adapter.notifyDataSetChanged();
        if (bidList.size() == 0) { finish(); }
    }

    protected void ShowBid(View view, int index) {
        Intent intent = new Intent(this, BidActivity.class);
        intent.putExtra("ItemIndex", this.index);
        intent.putExtra("BidIndex", index);
        startActivity(intent);
    }

    public static BidListActivity GetInstance() { return instance; }
}
