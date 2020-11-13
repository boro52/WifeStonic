package com.example.wifestonic.Touch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wifestonic.Adapter.RecordAdapter;
import com.example.wifestonic.R;

//Klasa do obslugi przesuwania rekordow
public class SwipeHelper extends ItemTouchHelper.SimpleCallback {

    private RecordAdapter adapter;

    public SwipeHelper(RecordAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();

        //Gdy przesuwamy w lewo, bedziemy usuwac rekord
        if(direction == ItemTouchHelper.LEFT) {
            //Tworzymy komunikat do potwierdzenia, czy na pewno chcemy usunac rekord
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Remove Record");
            builder.setMessage("Your're about to remove record");

            //Ustawiamy przycisk do potwierdzenia
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.removeRecord(position);
                }
            });

            //Ustawiamy przycisk do anulowania
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyItemChanged(position);
                }
            });
            //Wyswietlamy komunikat
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        //Gdy przesuniemy w prawo, aktualizujemy rekord
        //Powoduje to stworzenie widoku modyfukacji rekordu
        else {
            adapter.editRecord(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon;
        ColorDrawable background;

        View view = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        //Gdy przesuwamy w lewo, ustawiamy kolor na niebiesko
        if(dX > 0) {
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.edit_icon);
            background = new ColorDrawable(Color.BLUE);
        }
        //Gdy przesuwamy w prawo, ustawiamy kolor czerwony
        else {
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.delete_icon);
            background = new ColorDrawable(Color.RED);
        }

        //Ustawiamy zmienne do ograniczenia wielkosci ikon ustawionych wyzej z delete_icon / edic_icon
        int iconMargin = (view.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = view.getTop() + iconMargin / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) {
            //Ustawiamy "plynny ruch" ikony
            int iconLeft = view.getLeft() + iconMargin;
            int iconRight = view.getLeft() + iconMargin + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            //Ustawiamy "plynny ruch" koloru za rekordem
            background.setBounds(view.getLeft(), view.getTop(),
                    view.getLeft() + ((int) dX) + backgroundCornerOffset, view.getBottom());
        }
        else if (dX < 0) {
            int iconLeft = view.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = view.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(view.getRight() + ((int) dX) - backgroundCornerOffset,
                    view.getTop(), view.getRight(), view.getBottom());
        }
        else {
            background.setBounds(0, 0, 0, 0);
        }

        //Rysujemy na biezaco
        background.draw(c);
        icon.draw(c);
    }
}
