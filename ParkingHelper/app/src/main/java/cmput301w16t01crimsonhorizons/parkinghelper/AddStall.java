package cmput301w16t01crimsonhorizons.parkinghelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class AddStall extends AppCompatActivity {
    protected Stalls stall;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stall);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void saveStallInformation(View view){
        stall = new Stalls();
        EditText title = (EditText)findViewById(R.id.NamePrompET);
        EditText description = (EditText)findViewById(R.id.DescriptionET);
        String newTitle = title.getText().toString();
        String newDescription = description.getText().toString();
        stall.setOwner(CurrentAccount.getAccount().getEmail());
        stall.setDescription(newDescription);
        stall.setStatus("Available");
        AsyncTask<Stalls, Void, Void> s1execute = new ElasticSearchCtr.MakeStall().execute(stall);
        setResult(RESULT_OK);
        finish();
    }

}
