package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by schuman on 2/26/16.
 * US 06.01.01
 */

public class BorrowedStalls extends AppCompatActivity {
    private ListView eachBorrowedStalls;
    private Intent intent;
    private String user;
    private BorrowedStallAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_borrowed_stalls);
        intent = getIntent();
        user = intent.getStringExtra("email");
        eachBorrowedStalls = (ListView)findViewById(R.id.borrowedStallsList);
        ArrayList<Stalls> stallsArrayList = new ArrayList<>();
        ElasticSearchCtr.GetStallBorrowing getStallBorrowing = new ElasticSearchCtr.GetStallBorrowing();
        String[] query = new String[2];
        query[0]="Borrower";
        query[1]=user;
        getStallBorrowing.execute(query);
        try {
            stallsArrayList = getStallBorrowing.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        myAdapter = new BorrowedStallAdapter(this,R.layout.borrowed_stall_layout,stallsArrayList);
        eachBorrowedStalls.setAdapter(myAdapter);
    }
}
