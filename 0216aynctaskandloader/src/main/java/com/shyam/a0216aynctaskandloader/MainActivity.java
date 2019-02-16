package com.shyam.a0216aynctaskandloader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private TextView title, author;
    private EditText editTextQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportLoaderManager().initLoader(0, null, this) != null) {
            getSupportLoaderManager().initLoader(0,null,this);
        }

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
            Bundle bundle = new Bundle();
            bundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, bundle, this);
            title.setText(R.string.loading);
            author.setText("");
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        String queryString = "";
        if(bundle != null) {
            queryString = bundle.getString("queryString");
        }
        return new BookLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            int i = 0;
            String titleString = null;
            String authorString = null;
            while (i < jsonArray.length() && (authorString == null && titleString == null)) {
                JSONObject book = jsonArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                try {
                    titleString = volumeInfo.getString("title");
                    authorString = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                i++;
            }
            if (authorString != null && titleString != null) {
                title.setText(titleString);
                author.setText(authorString);
            } else {
                title.setText(R.string.no_result);
                author.setText("");
            }
        } catch (Exception e) {
            title.setText(R.string.no_result);
            author.setText("");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
