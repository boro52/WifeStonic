package com.example.wifestonic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wifestonic.Adapter.RecordAdapter;
import com.example.wifestonic.Database.DatabaseHandler;
import com.example.wifestonic.Interface.DialogCloseListener;
import com.example.wifestonic.Model.RecordModel;
import com.example.wifestonic.NewRecord.RecordAdder;
import com.example.wifestonic.Touch.SwipeHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView recordRecyclerView;
    private RecordAdapter recordAdapter;
    private DatabaseHandler dataBase;
    private FloatingActionButton addButton;

    private List<RecordModel> recordModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordAdder.newInstance().show(getSupportFragmentManager(), RecordAdder.TAG);
            }
        });

        dataBase = new DatabaseHandler(this);
        dataBase.accessDatabase();
        //Tworzymy obiekt ktory odpowiada RecyclerView z activity_main.xml i ustawiamy jego managera
        recordRecyclerView = findViewById(R.id.mainRecyclerView);
        recordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recordAdapter = new RecordAdapter(this, dataBase);
        recordRecyclerView.setAdapter(recordAdapter);

        recordModelList = new ArrayList<>();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeHelper(recordAdapter));
        itemTouchHelper.attachToRecyclerView(recordRecyclerView);

        recordModelList = dataBase.getAllRecords();
        Collections.reverse(recordModelList);
        recordAdapter.setRecord(recordModelList);
//        RecordModel model = new RecordModel();
//        model.setId(1);
//        model.setChecked(false);
//        model.setRecordText("Test bardzo");
//
//        recordModelList.add(model);
//        recordModelList.add(model);
//        recordModelList.add(model);
//        recordModelList.add(model);
//        recordModelList.add(model);
//        recordModelList.add(model);
//        recordModelList.add(model);
//        recordModelList.add(model);
//        recordModelList.add(model);
//        recordModelList.add(model);
//        recordModelList.add(model);
//        recordModelList.add(model);
//
//        recordAdapter.setRecord(recordModelList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Wypełnienie górnego paska stworzonym menu, jego struktura znajduje się w res/about_menu.xml
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Handlowanie przyciśnięcia itemu z about_menu.xml
        switch(item.getItemId())
        {
            case R.id.about:
                Intent i = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(i);
                Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        recordModelList = dataBase.getAllRecords();
        Collections.reverse(recordModelList);
        recordAdapter.setRecord(recordModelList);
        recordAdapter.notifyDataSetChanged();
    }
}