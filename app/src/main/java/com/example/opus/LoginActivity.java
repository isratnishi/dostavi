package com.example.opus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextView emailTextView;
    private EditText passwordEditText;
    private ProgressBar progressBar;
    ProgressDialog progress;

    TextView signinButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeVariables();

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLoginRequest();
            }
        });
    }

    private void initializeVariables() {
        emailTextView = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        signinButton = findViewById(R.id.email_sign_in_button);
        progressBar = findViewById(R.id.login_progress);

        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait");
    }

    private void sendLoginRequest() {
        //progressBar.setVisibility(View.VISIBLE);
        progress.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.LOGIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //progressBar.setVisibility(View.GONE);
                progress.dismiss();

                // Successfully Logged in
                if (response.equals("1")) {
                    Toast.makeText(LoginActivity.this, "Successfully Logged in",
                            Toast.LENGTH_SHORT).show();
                    Constants.USER_EMAIL = emailTextView.getText().toString();
                    //finish();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email or password! please try again",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong!  Please try again later",
                        Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String email = emailTextView.getText().toString();
                String password = passwordEditText.getText().toString();

                Map<String, String> params = new HashMap<>();
                params.put("Email", email);
                params.put("Password", password);

                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext())
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }
}
