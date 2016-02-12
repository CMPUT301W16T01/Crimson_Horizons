package com.example.lmingram.leesunittests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ReturnActivity extends AppCompatActivity {
    ArrayList<LeeThing> things;
    ListView thingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);

        thingView = (ListView)findViewById(R.id.returned_things);

        thingView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );
    }

    public ArrayList<LeeThing> getThingsOnScreen(){
        return new ArrayList<LeeThing>();
    }

    public int getNumItems() {
        return 0;
    }

}
