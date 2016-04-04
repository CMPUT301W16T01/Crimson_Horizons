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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Activity for viewing a list of all reviews submitted for a given stall.
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
            String temp = reviewlist.get(idx).getUser() + " says: \""
                    + reviewlist.get(idx).getText().toString()
                    + "\" (Rating: " + reviewlist.get(idx).getRating() + ")";
            review_string.add(temp);
            idx = idx+1;
        }
        myAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.review_layout,review_string);
        reviewsLV.setAdapter(myAdapter);

        TextView average = (TextView)findViewById(R.id.txtAverageRating);
        String averageValue = (reviewlist.size() > 0) ? average().toString() : "N/A";
        average.setText("Average Rating: " + averageValue);
    }

    private Double average() {
        Double result = 0.00;
        for (Review r : reviewlist) {
            result += r.getRating();
        }
        result /= reviewlist.size();
        return result;
    }

    public void addReview(View view){
        Intent intent = new Intent(getApplicationContext(),NewReview.class);
        intent.putExtra("stall",stall);
        startActivity(intent);
    }
}
