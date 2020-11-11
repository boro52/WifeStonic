package com.example.wifestonic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.wifestonic.Adapter.RecordAdapter;
import com.example.wifestonic.Model.RecordModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recordRecyclerView;
    private RecordAdapter recordAdapter;

    private List<RecordModel> recordModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tworzymy obiekt ktory odpowiada RecyclerView z activity_main.xml i ustawiamy jego managera
        recordRecyclerView = findViewById(R.id.mainRecyclerView);
        recordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recordAdapter = new RecordAdapter(this);
        recordRecyclerView.setAdapter(recordAdapter);

        recordModelList = new ArrayList<>();

        RecordModel model = new RecordModel();
        model.setId(1);
        model.setChecked(false);
        model.setRecordText("Test bardzo");

        recordModelList.add(model);
        recordModelList.add(model);
        recordModelList.add(model);
        recordModelList.add(model);
        recordModelList.add(model);
        recordModelList.add(model);
        recordModelList.add(model);
        recordModelList.add(model);
        recordModelList.add(model);
        recordModelList.add(model);
        recordModelList.add(model);
        recordModelList.add(model);

        recordAdapter.setRecord(recordModelList);
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
}