package com.shyam.a0216aynctaskandloader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        new FetchBook(title, author).execute(queryString);
    }
}
