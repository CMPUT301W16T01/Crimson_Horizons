package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This is to show a list of stalls the user is borrowing
 */
public class BorrowStallActivity extends AppCompatActivity {
    private ListView eachBorrowedStalls;
    private Intent intent;
    private String user;
    private BorrowedStallAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_stall);
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        myAdapter = new BorrowedStallAdapter(this,R.layout.borrowed_stall_layout,stallsArrayList);
        eachBorrowedStalls.setAdapter(myAdapter);
    }

}
