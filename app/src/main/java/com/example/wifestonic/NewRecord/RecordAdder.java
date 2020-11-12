package com.example.wifestonic.NewRecord;

import android.app.Activity;
import android.content.DialogInterface;
import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wifestonic.Database.DatabaseHandler;
import com.example.wifestonic.Interface.DialogCloseListener;
import com.example.wifestonic.Model.RecordModel;
import com.example.wifestonic.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

// Klasa pozwala wyswietlic okno do dodawania nowych rekordow
public class RecordAdder extends BottomSheetDialogFragment {
    public static String TAG = "BottomWindow";

    private EditText newRecordText;
    private Button newRecordButton;
    private DatabaseHandler dataBase;

    public static RecordAdder newInstance() {
        return new RecordAdder();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_record_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newRecordText = getView().findViewById(R.id.addNewRecordText);
        newRecordButton = getView().findViewById(R.id.addNewRecordButton);
        newRecordButton.setEnabled(false);

        dataBase = new DatabaseHandler(getActivity());
        dataBase.accessDatabase();

        boolean isUpdate = false;

        Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdate = true;
            String task = bundle.getString("recordText");
            newRecordText.setText(task);
            if (task.length() > 0) {

            }
        }

        newRecordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    newRecordButton.setEnabled(false);
                } else {
                    newRecordButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boolean finalIsUpdate = isUpdate;
        newRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newRecordText.getText().toString();
                if (finalIsUpdate) {
                    dataBase.updateRecordText(bundle.getInt("id"), text);
                } else {
                    RecordModel model = new RecordModel();
                    model.setRecordText(text);
                    model.setChecked(false);
                    dataBase.addRecord(model);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener) {
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }
}
