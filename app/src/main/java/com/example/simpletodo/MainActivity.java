package com.example.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /*
        Called by the android sys to inform the user/dev
        that the mainActivity has been created.
     */

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;

    List<String> items;
    // member var for each view
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;


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

        // initilize the model
        loadItems();
//        items = new ArrayList<>();
//        // populate list w/ mock data
//        items.add("Buy milk");
//        items.add("Do HW1");
//        items.add("Do HW2");

        /*
            Deletion: Listen for user interaction to apply deletion
         */
        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                // Delete the item from the model
                items.remove(position);
                // Notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Todo-item was removed!", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        /*
            Edit: Listen for user interaction to pull up edit
         */
        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener(){
            @Override
            public void onItemClicked(int position) {
                // open up the editActivity view
//                Log.d("MainActivity","Single click at position " + position);
                // Create the new edit activity
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                // Pass the data to be editted
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
                // Display the activity
                startActivityForResult(i, EDIT_TEXT_CODE);
            }
        };

        // construct the ItemsAdapter
        this.itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                // Add item to the model
                items.add(todoItem);
                // Notify adapter of the changes -- the new item added
                itemsAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                // Notify of the successful addition of the new item
                Toast.makeText(getApplicationContext(), "New todo-item added!", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    } // onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE){
            // Retrieve the updated text value
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
            // extract the original position of the editted item from the position key
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);

            // update the model at the right position w/ the new item text
            items.set(position, itemText);
            // notify the adapter
            itemsAdapter.notifyItemChanged(position);
            // persist changes
            saveItems();
            // indicate to user of succesful update
            Toast.makeText(getApplicationContext(), "Todo-item updated!", Toast.LENGTH_SHORT).show();
        } else {
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    private File getDataFile(){
        return new File(getFilesDir(),"data.txt");
    }

    // Load items by reading line by line of the data file
    // more realistic version would be a request w/ user credentials to a server
    // to get the necessary data to populat the model
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e){
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    // Save items by writing them into the data file
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}