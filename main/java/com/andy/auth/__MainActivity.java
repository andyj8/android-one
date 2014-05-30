package com.andy.auth;

import android.accounts.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        AccountManager mAccountManager = AccountManager.get(this);
        Account[] accounts = mAccountManager.getAccountsByType("com.andy.auth");

        if (accounts.length > 0) {
            Account account = accounts[0];

            final AccountManagerFuture<Bundle> future = mAccountManager.getAuthToken(account, "FULL", null, this, null, null);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bundle bnd = future.getResult();
                        final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                        Log.d("udinic", "GetToken Bundle is " + bnd);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } else {
            final AccountManagerFuture<Bundle> future = mAccountManager.addAccount("com.andy.auth", "FULL", null, null, this, new AccountManagerCallback<Bundle>() {
                @Override
                public void run(AccountManagerFuture<Bundle> future) {
                    try {
                        Bundle bnd = future.getResult();
                        Log.d("udinic", "AddNewAccount Bundle is " + bnd);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        }

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
