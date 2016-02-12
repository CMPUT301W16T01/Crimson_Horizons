package com.example.lmingram.leesunittests;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private Button searchButton;
    private EditText searchBar;
    private ListView searchResults;
    private ArrayList<LeeThing> things;
    private ArrayAdapter<LeeThing> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchResults = (ListView)findViewById(R.id.search_results);
        searchButton = (Button)findViewById(R.id.search_button);
        searchBar = (EditText)findViewById(R.id.search_bar);

        things = load();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tags = searchBar.getText().toString();
                ArrayList<LeeThing> results = searchThings(tags);
                displaySearchResults(results);
            }
        });
    }

    public void displaySearchResults(ArrayList<LeeThing> things){};


    public ArrayList<LeeThing> getSearchResults(){
        return new ArrayList<LeeThing>();
    }

    public ArrayList<LeeThing> load(){
        return new ArrayList<LeeThing>();
    }

    public ArrayList<LeeThing> searchThings(String tags){
        return new ArrayList<LeeThing>();
    }
}
