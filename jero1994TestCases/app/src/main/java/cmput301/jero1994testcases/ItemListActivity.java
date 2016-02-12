package cmput301.jero1994testcases;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    private ArrayList<Thing> itemList = new ArrayList<Thing>();

    private Button backButton;
    private ArrayAdapter<Thing> adapter;
    private ListView listView;

    private static ItemListActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        instance = this;

        itemList = TestData.testUser.GetThingsWithBids();

        backButton = (Button)findViewById(R.id.btnBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        listView = (ListView)findViewById(R.id.itemListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                ShowBidsList(view, position);
            }
        });
        adapter = new ArrayAdapter<Thing>(this, android.R.layout.simple_list_item_1, itemList);
        listView.setAdapter(adapter);
    }

    public static ItemListActivity GetInstance() { return instance; }

    @Override
    protected void onResume() {
        super.onResume();
        itemList = TestData.testUser.GetThingsWithBids();
        adapter.notifyDataSetChanged();
        if (itemList.size() == 0) { finish(); }
    }

    protected void ShowBidsList(View view, int index) {
        Intent intent = new Intent(this, BidListActivity.class);
        intent.putExtra("ItemIndex", index);
        startActivity(intent);
    }
}
