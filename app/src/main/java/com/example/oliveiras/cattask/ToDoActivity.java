package com.example.oliveiras.cattask;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



public class ToDoActivity extends ActionBarActivity {
    //create variables for the listview
    private ArrayList<String> todoitems;
    private ArrayAdapter<String> todoAdapter;
    private ListView lvItems;
    private EditText etNewItem;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        etNewItem = (EditText) findViewById(R.id.etNewItem); //gets the edit text from xml
        //define the listview items in java
        lvItems = (ListView) findViewById(R.id.lvitems);
        //set method to populate the arraylist
        readItems();
        //set the adapter
        todoAdapter = new ArrayAdapter<String>(this, R.layout.list_item,
                android.R.id.text1, todoitems);
        //make the adapter get the listview
        lvItems.setAdapter(todoAdapter);
        //now we need to be able to remove items
        //so, we are creating a method to remove items
        setupListViewListener();
    }

    private void setupListViewListener() {
        //here we connect the method to the lv items
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                //this removes the item from the array
                todoitems.remove(pos);
                //this notifies the adapter that we changed the array
                todoAdapter.notifyDataSetChanged();
                writeItems();
                return false;
            }
        });
    }

    public void onAddedItem(View v){
        //method to make button add the task to the list
        String itemText = etNewItem.getText().toString();
        if (itemText.trim().equals("")) {
            return;
        }
        todoAdapter.add(itemText);
        //this resets the settext field
        etNewItem.setText("");
        writeItems();
    }

    //create a method to read items from an external file
    private void readItems(){
        //create absolute path to where files are created
        File filesDir = getFilesDir();
        //create the file
        File todoFile = new File(filesDir, "todo.txt");
        try{
            todoitems = new ArrayList<String>(FileUtils.readLines(todoFile));

        } catch (IOException e){
            todoitems = new ArrayList<String>();
        }

    }
    private void writeItems(){
        //create absolute path to where files are created
        File filesDir = getFilesDir();
        //create the file
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, todoitems);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_do, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
