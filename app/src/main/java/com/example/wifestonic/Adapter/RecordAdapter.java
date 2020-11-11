package com.example.wifestonic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wifestonic.MainActivity;
import com.example.wifestonic.Model.RecordModel;
import com.example.wifestonic.R;

import java.util.List;

//Adapter laczy dane na ktorych dzialamy w programie i RecyclerView
//Pozwala to na poprawna obsluge danych i wyswietlenie ich we wczesniej stworzonym widoku
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    //Lista wszystkich rekordow
    private List<RecordModel> recordList;
    private MainActivity mainActivity;

    public RecordAdapter(MainActivity activity) {
        mainActivity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_layout, parent, false);
        return new ViewHolder(itemView);
    }

    // Tworzymy widoki, ktore zostana wyswietlone na ekranie z danych ktore definiuja dany rekord - RecordModel
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecordModel item = recordList.get(position);
        holder.record.setText(item.getRecordText());
        holder.record.setChecked(item.isChecked());
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    public void setRecord(List<RecordModel> recordList) {
        this.recordList = recordList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox record;

        ViewHolder(View view) {
            super(view);
            record = view.findViewById(R.id.recordCheckBox);
        }
    }
}
