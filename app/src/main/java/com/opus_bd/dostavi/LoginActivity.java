package com.opus_bd.dostavi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.opus_bd.dostavi.models.LoggedUser;
import com.opus_bd.dostavi.shared_pref_manager.SharedPrefManager;
import com.opus_bd.dostavi.singletones.UserSingleton;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    int attemptLogin = 0;
    ArrayAdapter<String> emailAdapter;
    String[] emails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    sendLoginRequest();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLoginRequest();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        emails = SharedPrefManager.getInstance(this).getSavedEmails();
        emailAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, emails);

        mEmailView.setAdapter(emailAdapter);
        mEmailView.setThreshold(1);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            mEmailView.setText(SharedPrefManager.getInstance(this).getUser().getEmail());
            mPasswordView.setText(SharedPrefManager.getInstance(this).getUser().getPassword());
        }
    }

    private void sendLoginRequest() {
        showProgress(true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.LOGIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                showProgress(false);
                // Successfully Logged in
                if (response.equals("1")) {
                    attemptLogin = 0;
                    saveUserInfo();
                    updateSharedPreference(); // For updating auto complete text
                    clearTexts();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    Toasty.success(getApplicationContext(), "Successfully Logged in",
                            Toast.LENGTH_SHORT, true).show();
                } else {
                    Toasty.error(getApplicationContext(), "Invalid email or password! please try again",
                            Toast.LENGTH_SHORT, true).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError && attemptLogin == 0) {
                    sendLoginRequest();
                    attemptLogin = 1;
                } else {
                    showProgress(false);
                    attemptLogin = 0;
                    Toasty.error(getApplicationContext(), "Something went wrong!  Please try again later",
                            Toast.LENGTH_SHORT, true).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();

                Map<String, String> params = new HashMap<>();
                params.put("UserName", email);
                params.put("Password", password);
                return params;
            }
        };
        AppSingleton.getInstance(getApplicationContext())
                .addToRequestQueue(stringRequest, Constants.REQUEST_TAG);
    }

    private void clearTexts() {
        mEmailView.setText("");
        mPasswordView.setText("");
    }

    private void updateSharedPreference() {
        SharedPrefManager.getInstance(LoginActivity.this).
                saveEmail(mEmailView.getText().toString());
        emailAdapter = new ArrayAdapter<String>(LoginActivity.this,
                android.R.layout.simple_dropdown_item_1line, emails);
        mEmailView.setAdapter(emailAdapter);
    }

    private void saveUserInfo() {
        UserSingleton.getInstance().setUserName(mEmailView.getText().toString());
        LoggedUser user = new LoggedUser(mEmailView.getText().toString(), mPasswordView.getText().toString());
        SharedPrefManager.getInstance(this).userLogin(user);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        Utils.hideSoftKeyboard(this);
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

