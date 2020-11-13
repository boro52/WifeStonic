package com.example.wifestonic.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wifestonic.Database.DataBaseNames;
import com.example.wifestonic.Database.DatabaseHandler;
import com.example.wifestonic.MainActivity;
import com.example.wifestonic.Model.RecordModel;
import com.example.wifestonic.NewRecord.RecordAdder;
import com.example.wifestonic.R;

import java.util.List;

//Adapter laczy dane na ktorych dzialamy w programie i RecyclerView
//Pozwala to na poprawna obsluge danych i wyswietlenie ich we wczesniej stworzonym widoku
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private List<RecordModel> recordList;
    private MainActivity mainActivity;
    private DatabaseHandler dataBase;

    public RecordAdapter(MainActivity activity, DatabaseHandler db) {
        mainActivity = activity;
        dataBase = db;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_layout, parent, false);
        return new ViewHolder(itemView);
    }

    // Tworzymy widoki, ktore zostana wyswietlone na ekranie z danych ktore definiuja dany rekord - RecordModel
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        dataBase.accessDatabase();
        RecordModel item = recordList.get(position);
        holder.record.setText(item.getRecordText());
        holder.record.setChecked(item.isChecked());

        //Ustawiamy Listenera, ktory aktualizuje w bazie danych, czy dany rekord jest odhaczony lub nie
        holder.record.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    dataBase.updateRecordStatus(item.getId(), true);
                }
                else {
                    dataBase.updateRecordStatus(item.getId(), false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public void setRecord(List<RecordModel> recordList) {
        this.recordList = recordList;
    }

    //Pozwala na edycje danego rekordu
    //Pobiera intoformacje ktory zostal zmodyfikowany i przekazuje to jako Bundle do RecordAddera
    public void editRecord(int position) {
        RecordModel model = recordList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(DataBaseNames.ID, model.getId());
        bundle.putString(DataBaseNames.RECORD_TEXT, model.getRecordText());

        RecordAdder adder = new RecordAdder();
        adder.setArguments(bundle);
        adder.show(mainActivity.getSupportFragmentManager(), RecordAdder.TAG);
    }

    //Pozwala na usuniecie rekodru
    //Zbiera informacje ktory ma zostac usuniety, usuwa go z bazy danych,
    // z listy oraz powiadamia RecyclerView do aktualizacji widoku
    public void removeRecord(int position) {
        RecordModel model = recordList.get(position);
        dataBase.deleteRecord(model.getId());
        recordList.remove(position);
        notifyItemRemoved(position);
    }

    public Context getContext() {
        return mainActivity;
    }

    // Klasa zawierajaca widok "kafelka" wyswietlajacego rekord oraz checkbox
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox record;

        ViewHolder(View view) {
            super(view);
            record = view.findViewById(R.id.recordCheckBox);
        }
    }
}
