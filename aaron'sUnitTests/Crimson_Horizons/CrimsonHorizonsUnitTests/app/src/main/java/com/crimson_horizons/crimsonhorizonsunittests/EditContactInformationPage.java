package com.crimson_horizons.crimsonhorizonsunittests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by schuman on 2/9/16.
 */
public class EditContactInformationPage extends Activity{

    public static final String TEXT_TO_NEW_USER_KEY = "USER";
    public static final String MEANS_OF_INPUT_KEY = "INPUT";

    public static final int CONTACT_INFO = 1;
    public static final int PASSWORD = 2;

    private String text;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        //
        Intent intent = getIntent();
        mode = intent.getIntExtra(MEANS_OF_INPUT_KEY, CONTACT_INFO);
        text = intent.getStringExtra(TEXT_TO_NEW_USER_KEY);

        switch(mode) {
            case CONTACT_INFO:
                ((TextView) findViewById(R.id.fab)).setText(text);
                break;
            case PASSWORD:
                ((TextView) findViewById(R.id.action_settings)).setText(text);
                break;
            default:
                break;
        }

    }
}
