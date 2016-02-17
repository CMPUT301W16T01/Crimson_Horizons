package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.suitebuilder.TestMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {
    private ListView MyStalls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_acitivity);
        MyStalls = (ListView)findViewById(R.id.OwnStalls);
    }
    @Override
    protected void onStart(){
        super.onStart();
        ArrayList<String> TempArray = new ArrayList<>();
        TempArray.add("Something");
        TempArray.add("Another thing");
        MyStalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent clickStall = new Intent(view.getContext(), EditStall.class);
                String entry = MyStalls.getItemAtPosition(position).toString();
                clickStall.putExtra("entry", entry);
                clickStall.putExtra("id", position);
                startActivity(clickStall);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.account_stalls,TempArray);
        MyStalls.setAdapter(adapter);
    }

    public void addStall(View view){
        Intent intent = new Intent(this,AddStall.class);
        startActivity(intent);
    }

    public void profile(View view){
        Intent intent = new Intent(this,Profile.class);
        startActivity(intent);
    }

}
