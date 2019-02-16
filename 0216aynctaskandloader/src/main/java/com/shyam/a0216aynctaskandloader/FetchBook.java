package com.shyam.a0216aynctaskandloader;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            int i = 0;
            String title = null;
            String author = null;
            while (i < jsonArray.length() && (author == null && title == null)) {
                JSONObject book = jsonArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                try {
                    title = volumeInfo.getString("title");
                    author = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                i++;
            }
            if (author != null && title != null) {
                titleText.get().setText(title);
                authorText.get().setText(author);
            } else {
                titleText.get().setText(R.string.no_result);
                authorText.get().setText("");
            }
        } catch (Exception e) {
            titleText.get().setText(R.string.no_result);
            authorText.get().setText("");
        }
    }
}
