package com.example.wifestonic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

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

    private void initializeMembers() {
        //Znajdujemy przycisk i przypisujemy mu onClick
        //onClick tworzy nowa instacje "RecordAddera" co pozwala na obsluge dodawania nowych rekordow do aplikacji
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordAdder.newInstance().show(getSupportFragmentManager(), RecordAdder.TAG);
            }
        });

        //Tworzymy obiekt ktory odpowiada RecyclerView z activity_main.xml i ustawiamy jego managera
        recordRecyclerView = findViewById(R.id.mainRecyclerView);
        recordRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Tworzymy Handlera do bazy danych i otwieramy ja do odczytu / modyfikacji
        dataBase = new DatabaseHandler(this);
        dataBase.accessDatabase();

        //Tworzymy adapter - pozwala on na komunikacje z baza danych i informuje RecyclerView o zmianach
        recordAdapter = new RecordAdapter(this, dataBase);
        recordRecyclerView.setAdapter(recordAdapter);

        // Wypełniamy Liste rekordow pobrana z bazy danych
        //Obracamy liste, aby ostatnio dodane rekordy byly na gorze
        //Uzupelniamy liste w adapterze
        recordModelList = new ArrayList<>();
        recordModelList = dataBase.getAllRecords();
        Collections.reverse(recordModelList);
        recordAdapter.setRecord(recordModelList);

        //Dodajemy helpera, do obslugi ruchu kazdego rekodru
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeHelper(recordAdapter));
        itemTouchHelper.attachToRecyclerView(recordRecyclerView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeMembers();
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
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        //Metoda z interfejsu ktora jest wolana z "RecordAddera"
        //po uprzednim dodaniu rekordu do bazy danych przekazujemy liste do adaptera, ktory aktualizuje widok
        recordModelList = dataBase.getAllRecords();
        Collections.reverse(recordModelList);
        recordAdapter.setRecord(recordModelList);
        recordAdapter.notifyDataSetChanged();
    }
}