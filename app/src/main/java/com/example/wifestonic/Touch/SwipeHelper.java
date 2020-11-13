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

        if(direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Remove Record");
            builder.setMessage("Your're about to remove record");
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.removeRecord(position);
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyItemChanged(position);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
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

        if(dX > 0) {
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.edit_icon);
            background = new ColorDrawable(Color.BLACK);
        }
        else {
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.delete_icon);
            background = new ColorDrawable(Color.RED);
        }

        int iconMargin = (view.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = view.getTop() + iconMargin / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) { // Swiping to the right
            int iconLeft = view.getLeft() + iconMargin;
            int iconRight = view.getLeft() + iconMargin + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(view.getLeft(), view.getTop(),
                    view.getLeft() + ((int) dX) + backgroundCornerOffset, view.getBottom());
        } else if (dX < 0) { // Swiping to the left
            int iconLeft = view.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = view.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(view.getRight() + ((int) dX) - backgroundCornerOffset,
                    view.getTop(), view.getRight(), view.getBottom());
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        icon.draw(c);
    }
}
