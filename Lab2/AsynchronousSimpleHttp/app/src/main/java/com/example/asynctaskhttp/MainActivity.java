package com.example.asynctaskhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    EditText mEditText;
    Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.etPrint);
        mEditText = findViewById(R.id.myET);
        btnGo = findViewById(R.id.buttonGo);


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new LongRunningTask().execute();
            }
        });

    }

    private class LongRunningTask extends AsyncTask<Void, Void, Void> {
        String myAddress = mEditText.getText().toString();

        @Override
        protected Void doInBackground(Void... voids) {
            makeHttprequest(myAddress);
            return null;
        }
    }



    public void makeHttprequest(String urlString) {
        try {
            URL url = new URL(urlString);

            HttpURLConnection mConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStreamReader = new BufferedInputStream(mConnection.getInputStream());
            String result = fromStream(inputStreamReader);

            mTextView.setText(result);
            mConnection.disconnect();
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public String fromStream(InputStream in) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }

}
