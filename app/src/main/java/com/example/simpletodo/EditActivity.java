package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    /*
        View for handling editting of the user selected item from the main screen
        Editted data is passed back to the main view using the Intent class
        at which any changes are to be handled by the MainActivity
     */
    EditText etItem;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etItem = findViewById(R.id.etItem);
        btnSave = findViewById(R.id.btnSave);

        getSupportActionBar().setTitle("Edit item");

        // prepopulate the text box w/ the user selected to-do item data
        etItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        // WHen the suser is done editting, they click the save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent containing the results post-edit
                Intent i = new Intent();
                // Pass results
                i.putExtra(MainActivity.KEY_ITEM_TEXT, etItem.getText().toString());
                i.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));
                // Set the results of the intent
                setResult(RESULT_OK, i);
                // Finish activity ie close the edit screen and return back to the main screen
                finish();
            }
        });
    }
}