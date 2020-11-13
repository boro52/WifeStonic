package com.example.wifestonic.Database;

// Spi
public class DataBaseNames {
    public static int VERSION = 1;
    public static String NAME = "recordListDatabase";
    public static String RECORD_TABLE = "record";
    public static String ID = "id";
    public static String STATUS = "status";
    public static String RECORD_TEXT = "recordText";
    public static String CREATE_RECORD_TABLE =
                    "CREATE TABLE " +
                    RECORD_TABLE +
                    "(" +
                    ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    RECORD_TEXT +
                    " TEXT, " +
                    STATUS +
                    " INTEGER)";
}
