package com.andy.auth.auth;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetworkClient
{
    private static final String TAG = "AuthNetworkClient";

    private static final String PARAM_USERNAME = "user";
    private static final String PARAM_PASSWORD = "pass";

    /**
     * @param username The server account username
     * @param password The server account password
     * @return String The authentication token returned by the server (or null)
     */
    public static String authenticate(String username, String password)
    {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.56.5:8081/");

        String token = null;

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair(PARAM_USERNAME, username));
            nameValuePairs.add(new BasicNameValuePair(PARAM_PASSWORD, password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                token = EntityUtils.toString(response.getEntity());
            }

        } catch (ClientProtocolException e) {
            Log.i(TAG, "ClientProtocolException");
        } catch (IOException e) {
            Log.i(TAG, "IOException");
        }

        return token;
    }

}
