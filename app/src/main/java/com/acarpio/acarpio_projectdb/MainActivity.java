package com.acarpio.acarpio_projectdb;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;

    EditText commentTitle, commentText, commentTextWatch;
    Button createButton, loadButton, deleteButton;
    Spinner spinner;
    String titleToQuery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // DBHelper instance
        dbHelper = new DBHelper(getApplicationContext());

        // Binding the views

        commentTitle = findViewById(R.id.commentTitle);
        commentText = findViewById(R.id.commentText);
        commentTextWatch = findViewById(R.id.commentTextWatch);

        createButton = findViewById(R.id.createButton);
        loadButton = findViewById(R.id.loadButton);
        deleteButton = findViewById(R.id.deleteButton);

        spinner = findViewById(R.id.spinner);

        // Populating the spinner with the queries

        // Again, it doesn't make sense to do this normally, I just want to get the titles for
        // the select query.

        populateSpinner();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                titleToQuery = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Listeners

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addComment(getCommentToAdd());
                populateSpinner();
            }});

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadComment(v);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteComment(v);
                populateSpinner();
            }
        });



    }

    private void populateSpinner() {
        ArrayList<String> titles = new ArrayList<>();
        for (Comment comment : dbHelper.getAllComments()) {
            titles.add(comment.getTitle());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, titles);

        spinner.setAdapter(adapter);
    }


    public Comment getCommentToAdd() {
        Comment comment = new Comment();
        comment.setTitle(commentTitle.getText().toString());
        comment.setText(commentText.getText().toString());
        return comment;

    }

    public void loadComment(View view) {
        commentTextWatch.setText(dbHelper.getCommentByTitle(titleToQuery));

    }

    public void deleteComment(View view) {
        dbHelper.deleteCommentByTitle(titleToQuery);
    }



}