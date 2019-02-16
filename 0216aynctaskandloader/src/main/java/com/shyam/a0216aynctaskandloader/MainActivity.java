package com.shyam.a0216aynctaskandloader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView title, author;
    private EditText editTextQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.book_name);
        author = findViewById(R.id.author);
        editTextQuery = findViewById(R.id.edit_text_title);
    }

    public void searchBooks(View view) {
        String queryString = editTextQuery.getText().toString();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager == null ? null : connectivityManager.getActiveNetworkInfo();

        if(queryString.length() == 0) {
            title.setText(R.string.empty_query);
            author.setText("");
        } else if(networkInfo == null || !networkInfo.isConnected()) {
            title.setText(R.string.no_network);
            author.setText("");
        } else {
            new FetchBook(title, author).execute(queryString);
            title.setText(R.string.loading);
            author.setText("");
        }
    }
}
