package cmput301w16t01crimsonhorizons.parkinghelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Created by schuman on 2/26/16.
 */
public class BorrowedStalls extends AppCompatActivity {
    private ListView eachBorrowedStalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_borrowed_stalls);
    }
}
