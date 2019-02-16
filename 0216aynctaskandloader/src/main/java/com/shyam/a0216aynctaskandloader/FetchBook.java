package com.shyam.a0216aynctaskandloader;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> titleText, authorText;

    FetchBook(TextView title, TextView author) {
        titleText = new WeakReference<>(title);
        authorText = new WeakReference<>(author);
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBooks(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
