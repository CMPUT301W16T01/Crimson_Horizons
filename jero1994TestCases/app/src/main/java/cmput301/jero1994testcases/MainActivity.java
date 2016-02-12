package cmput301.jero1994testcases;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.btnItemList);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShowItemsList(v);
            }
        });
    }

    protected void ShowItemsList(View view) {
        if (TestData.testUser.GetThings().size() == 0) { return; }
        Intent itemListIntent = new Intent(this, ItemListActivity.class);
        startActivity(itemListIntent);
    }
}
