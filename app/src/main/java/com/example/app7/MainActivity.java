package com.example.app7;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class MainActivity extends AppCompatActivity {

    TextView lblEmail;
    Switch switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblEmail = findViewById(R.id.lblEmailAddress);


        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userdetails",0);

        lblEmail.setText(sharedPreferences.getString("email","0"));

        switch1 = findViewById(R.id.switch1);
        switch1.setChecked(true);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    SharedPreferences sharedpreferences = getSharedPreferences("userdetails", 0);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("email", null);
                    editor.putString("password", null);
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("EXIT", true);
                    startActivity(intent);
                }
            }

        }


        );
    }
}