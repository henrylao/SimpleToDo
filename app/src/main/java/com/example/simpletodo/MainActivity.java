package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /*
        Called by the android sys to inform the user/dev
        that the mainActivity has been created.
     */

    List<String> items;
    // member var for each view
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
            Place for taking action on the mainActivity object
         */
        super.onCreate(savedInstanceState);
        // sets the activity_main.xml file as the content of the activity
        // which is how the mocked design is able to be displayed
        // when we run the app
        setContentView(R.layout.activity_main);

        // get the reference from the view xml of activity_main.xml
        btnAdd = findViewById(R.id.btnAdd);
        rvItems = findViewById(R.id.rvItems);
        etItem = findViewById(R.id.etItem);

        // instantiate the list
        items = new ArrayList<>();
        // populate list w/ mock data
        items.add("Buy milk");
        items.add("Do HW1");
        items.add("Do HW2");

        // construct the ItemsAdapter
        ItemsAdapter itemsAdapter = new ItemsAdapter(items);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

    }
}