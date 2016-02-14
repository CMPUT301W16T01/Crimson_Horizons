package com.example.lmingram.leesunittests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class BorrowingActivity extends AppCompatActivity {
    private Button ownedButton;
    private Button borrowedButton;
    private ListView thingsView;
    private ArrayList<LeeThing> things;
    private ArrayAdapter<LeeThing> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowing);

        ownedButton = (Button)findViewById(R.id.owned_button);
        borrowedButton = (Button)findViewById(R.id.borrowed_button);
        thingsView = (ListView)findViewById(R.id.things_view);

        ownedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display();
            }
        });

        borrowedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display();
            }
        });

    }

    public void display(){}

    public ArrayList<LeeThing> getThingsOnScreen(){
        return new ArrayList<LeeThing>();
    }
}
