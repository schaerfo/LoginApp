package com.gmail.jakobmache.login;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

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
                    helper.login(name, password);
                    String result = helper.fetchPlan();
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

        name = userInput.getText().toString();
        password = passwordInput.getText().toString();
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
