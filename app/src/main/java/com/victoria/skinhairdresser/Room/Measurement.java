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
    public int year;

    @ColumnInfo(name = "month")
    public int month;

    @ColumnInfo(name = "day")
    public int day;

    @ColumnInfo(name = "pg")
    public int pg;

    @ColumnInfo(name = "moisture")
    public int moisture;

    @ColumnInfo(name = "hole")
    public int hole;

    @ColumnInfo(name = "tone")
    public int tone;

    @ColumnInfo(name = "color_washing")
    public int color_washing;

    @ColumnInfo(name = "sensitive")
    public int sensitivity;

    @ColumnInfo(name = "fold")
    public int fold;

    @ColumnInfo(name = "oil")
    public int oil;

    public Measurement(int year, int month, int day,
                       int pg, int moisture, int hole, int tone, int color_washing, int sensitivity, int fold, int oil) {
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
