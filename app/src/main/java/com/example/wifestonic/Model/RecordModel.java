package com.example.wifestonic.Model;

//Klasa stworzona jako reprezentacja kazdego rekordu
//Odpowiednio mamy ID, czy zaznaczony jest box, tekst w rekordzie
public class RecordModel {
    private int id;
    private boolean isChecked;
    private String recordText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getRecordText() {
        return recordText;
    }

    public void setRecordText(String recordText) {
        this.recordText = recordText;
    }
}
