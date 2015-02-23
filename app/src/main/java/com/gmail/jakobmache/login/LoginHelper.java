package com.gmail.jakobmache.login;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakob on 23.02.2015.
 */
public class LoginHelper {

    private final String LOGIN_URL = "http://asgspez.de/index.php/anmelden.html";

    public String openConnectionAndLogIn(String username, String password) throws MalformedURLException, IOException {
        URL url = new URL(LOGIN_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        connection.setUseCaches(false);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("return", "aHR0cDovL2FzZ3NwZXouZGUvaW5kZXgucGhwL3NjaHVlbGVyLWludGVybi92ZXJ0cmV0dW5nc3BsYW4uaHRtbA=="));
        params.add(new BasicNameValuePair("task", "user.login"));

        OutputStream stream = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream, "UTF-8"));
        writer.write(getQuery(params));
        writer.flush();
        writer.close();
        stream.close();

        InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        return response.toString();

    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair:params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }



}
