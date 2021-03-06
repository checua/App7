package com.example.app7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login extends AppCompatActivity {

    EditText edtEmailAddress, edtPassword;
    Button btnLogin;
    ProgressBar progressBar;
    LinearLayout lvparent;
    TextView tvSignUp;

    //Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finishActivity(0);
        }
        super.onCreate ( savedInstanceState );



        SharedPreferences sharedpreferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        //get your string with default string in case referred key is not found
        String str1 = sharedpreferences.getString("email",  null);
        String str2 = sharedpreferences.getString("password",  null);

        if (str1 != null){// && str2 != null) { //Aquí es cuando si hay un usuario logueado
            //edtEmailAddress.setText ("email"  );
            //edtPassword.setText ( "password" );

            Intent i = new Intent(login.this, MainActivity.class);
            startActivity(i);
        }
        else {

            setContentView ( R.layout.login );

            edtEmailAddress = findViewById ( R.id.edtEmailAddress );
            edtPassword = findViewById ( R.id.edtPassword );
            btnLogin = findViewById ( R.id.btnLogin );
            lvparent = findViewById ( R.id.lvparent );
            //btnSignUp = findViewById(R.id.btnSignUp);
            progressBar = findViewById ( R.id.progressBar );
            //tvSignUp = findViewById(R.id.tvSingUp);


            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder ( ).permitAll ( ).build ( );
            StrictMode.setThreadPolicy ( policy );
/*
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, signup.class);
                startActivity(i);

            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(login.this, signup.class);
                startActivity(i);
            }
        });
        */
        }
    }


    private class DoLoginForUser extends AsyncTask<String, Void, String> {
        String emailId, password;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            emailId = edtEmailAddress.getText().toString();
            password = edtPassword.getText().toString();
            progressBar.setVisibility(View.VISIBLE);
            //btnLogin.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                ConnectionHelper con = new ConnectionHelper();
                Connection connect = ConnectionHelper.CONN();

                String query = "Select * from Usuarios where correo='" + emailId + "'";
                PreparedStatement ps = connect.prepareStatement(query);

                Log.e("query",query);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String passcode = rs.getString("contra");
                    connect.close();
                    rs.close();
                    ps.close();
                    if (passcode != null && !passcode.trim().equals("") && passcode.equals(password))
                        return "success";
                    else
                        return "Invalid Credentials";

                } else
                    return "User does not exists.";
            } catch (SQLException e) {

                return "Error:" + e.getMessage().toString();
            } catch (Exception e) {
                return "Error:" + e.getMessage().toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            //Toast.makeText(signup.this, result, Toast.LENGTH_SHORT).show();
            ShowSnackBar(result);
            progressBar.setVisibility(View.INVISIBLE);
            //btnLogin.setVisibility(View.VISIBLE);
            if (result.equals("success")) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userdetails",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("email",edtEmailAddress.getText().toString());

                editor.commit();

                Intent i = new Intent(login.this, MainActivity.class);
                startActivity(i);

            } else {
                ShowSnackBar(result);
            }
        }
    }

    public void ShowSnackBar(String message) {
        Snackbar.make(lvparent, message, Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }

    public void DoLogin(View v)
    {
        DoLoginForUser login = new DoLoginForUser();
        login.execute("");
    }

    public void SingUp(View v)
    {
        Intent i = new Intent(login.this, signup.class);
        startActivity(i);
    }


}