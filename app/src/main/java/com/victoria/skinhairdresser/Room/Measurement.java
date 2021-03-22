package com.victoria.skinhairdresser.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "measurement_table")
public class Measurement {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cid")
    public int cid;

    @ColumnInfo(name = "year")
    public String year;

    @ColumnInfo(name = "month")
    public String month;

    @ColumnInfo(name = "day")
    public String day;

    @ColumnInfo(name = "pg")
    public String pg;

    @ColumnInfo(name = "moisture")
    public String moisture;

    @ColumnInfo(name = "hole")
    public String hole;

    @ColumnInfo(name = "tone")
    public String tone;

    @ColumnInfo(name = "color_washing")
    public String color_washing;

    @ColumnInfo(name = "sensitive")
    public String sensitivity;

    @ColumnInfo(name = "fold")
    public String fold;

    @ColumnInfo(name = "oil")
    public String oil;

    Measurement(String year, String month, String day,
                String pg, String moisture, String hole, String tone, String color_washing, String sensitivity, String fold, String oil) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.pg = pg;
        this.moisture = moisture;
        this.hole = hole;
        this.tone = tone;
        this.color_washing = color_washing;
        this.sensitivity = sensitivity;
        this.fold = fold;
        this.oil = oil;
    }
}
