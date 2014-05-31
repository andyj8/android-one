package com.andy.auth;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.andy.auth.auth.NetworkClient;

public class LoginActivity extends AccountAuthenticatorActivity implements LoginFragment.OnLoginListener {

    private final String TAG = "LoginActivity";

    private AccountManager mAccountManager;

    private String mUsername;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountManager = AccountManager.get(this);

        setContentView(R.layout.activity_login);
    }

    public void handleLogin(String username, String password) {
        mUsername = username;
        mPassword = password;
        new UserLoginTask().execute();
    }

    public void onAuthenticationResult(String authToken) {
        boolean success = ((authToken != null) && (authToken.length() > 0));
        if (success) {
            final Account account = new Account(mUsername, "com.andy.auth");

            if (getIntent().getBooleanExtra("new", false)) {
                mAccountManager.addAccountExplicitly(account, mPassword, null);
                // mAccountManager.setAuthToken(account, "FULL", authToken);
            } else {
                mAccountManager.setPassword(account, mPassword);
            }

            final Intent intent = new Intent();
            intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, mUsername);
            intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, "com.andy.auth");
            setAccountAuthenticatorResult(intent.getExtras());
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
        }
    }

    public class UserLoginTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                return NetworkClient.authenticate(mUsername, mPassword);
            } catch (Exception ex) {
                Log.i(TAG, ex.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String token) {
            onAuthenticationResult(token);
        }
    }

}
