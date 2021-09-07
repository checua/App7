package com.example.app7;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Account extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_account );

        mTextView = (TextView) findViewById ( R.id.text );

        // Enables Always-on
        //setAmbientEnabled ( );
    }
}