package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class NewReview extends AppCompatActivity {
    private Stalls stall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);

        Intent intent = getIntent();
        stall = (Stalls) intent.getSerializableExtra("stall");

        NumberPicker numberPicker = (NumberPicker)findViewById(R.id.ratingPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        numberPicker.setValue(5);
    }

    public void NewReview(View view) {
        EditText reviewText = (EditText)findViewById(R.id.reviewText);
        NumberPicker numberPicker = (NumberPicker)findViewById(R.id.ratingPicker);

        Review newReview = new Review(CurrentAccount.getAccount().getEmail(),
                stall.getStallID(), reviewText.getText().toString(), numberPicker.getValue());
        ElasticSearchCtr.MakeReview makeReview= new ElasticSearchCtr.MakeReview();
        makeReview.execute(newReview);
        Toast.makeText(NewReview.this, "Review submitted!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
