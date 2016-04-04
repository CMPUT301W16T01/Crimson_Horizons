package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * View for the review activity
 */

public class ReviewsActivity extends AppCompatActivity {
    private ArrayAdapter<String> myAdapter;
    private ListView reviewsLV;
    private Stalls stall;
    private ArrayList<Review>reviewlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        reviewsLV = (ListView)findViewById(R.id.reviewsLVEditStall);
        Intent intent =getIntent();
        stall = (Stalls)intent.getSerializableExtra("stall");
        ElasticSearchCtr.GetReview getReview = new ElasticSearchCtr.GetReview();
        String[] query = new String[2];
        query[0]="StallID";
        query[1]=stall.getStallID();
        getReview.execute(query);

        try {
            reviewlist = getReview.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ArrayList<String> review_string = new ArrayList<>();
        int idx =0;
        while (idx<reviewlist.size()){
            review_string.add(reviewlist.get(0).getText().toString());
            idx = idx+1;
        }
        myAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.review_layout,review_string);
        reviewsLV.setAdapter(myAdapter);
    }

    private Double average() {
        Double result = 0.00;
        for (Review r : reviewlist) {
            result += r.getRating();
        }
        result /= reviewlist.size();
        return result;
    }
}
