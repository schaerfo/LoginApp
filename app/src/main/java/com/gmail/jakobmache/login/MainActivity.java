package com.gmail.jakobmache.login;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends ActionBarActivity {

    private static final String PLAN_URL = "http://asgspez.de/index.php/schueler-intern/vertretungsplan.html";
    private static final String SIGN_IN_URL = "http://asgspez.de/index.php/anmelden.html";
    private final String TAG = getClass().getSimpleName();
    private String name;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        final WebView view = (WebView) findViewById(R.id.webView);
        Button okButton = (Button) findViewById(R.id.button);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputData();
                LoginHelper helper = new LoginHelper();

                try {
                    String result = "";
                    result = helper.openConnectionAndLogIn(name, password);
                    URL url = new URL(PLAN_URL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    // result = readStream(in);
                    urlConnection.disconnect();
                    view.loadData(result, "text/html", "UTF-8");
                    }
                catch (Exception e)
                {
                    Log.e(TAG,"exception", e);
                }
                password = name = "";
            }
        });



    }

    private void getInputData()
    {
        EditText userInput = (EditText) findViewById(R.id.nameInput);
        EditText passwordInput = (EditText) findViewById(R.id.passwordInput);

        name = (String) userInput.getText().toString();
        password = (String) passwordInput.getText().toString();
    }

    private static String readStream(InputStream is)
    {
        String result;
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line);
            }

            result = stringBuilder.toString();
        }

        catch (Exception e)
        {
            result = "";
        }

        return result;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
