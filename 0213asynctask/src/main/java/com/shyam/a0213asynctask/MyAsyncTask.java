package com.shyam.a0213asynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class MyAsyncTask extends AsyncTask<Void, Integer, String> {

    private WeakReference<TextView> viewWeakReference, progressText;

    public MyAsyncTask(TextView textView, TextView progressTextView) {
        viewWeakReference = new WeakReference<>(textView);
        progressText = new WeakReference<>(progressTextView);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random r = new Random();
        int n = r.nextInt(11);
        try {
            for (int i = 0; i < n; i++) {
                Thread.sleep(200);
                publishProgress( (int) (((i + 1) / (float) n) * 100));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Awake at last after sleeping for " + (n * 200) + " milliseconds!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressText.get().setText("Complete: " + values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        viewWeakReference.get().setText(s);
    }
}
