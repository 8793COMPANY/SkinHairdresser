package com.corporation8793.skinhairdresser.Room;

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

    @ColumnInfo(name = "fold")
    public int fold;

    @ColumnInfo(name = "oil")
    public int oil;

    public Measurement(int year, int month, int day,
                       int pg, int moisture, int hole, int tone, int color_washing, int fold, int oil) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.pg = pg;
        this.moisture = moisture;
        this.hole = hole;
        this.tone = tone;
        this.color_washing = color_washing;
        this.fold = fold;
        this.oil = oil;
    }

    public int getCid() {
        return cid;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getPg() {
        return pg;
    }

    public int getMoisture() {
        return moisture;
    }

    public int getHole() {
        return hole;
    }

    public int getTone() {
        return tone;
    }

    public int getColor_washing() {
        return color_washing;
    }

    public int getFold() {
        return fold;
    }

    public int getOil() {
        return oil;
    }

    public void setPg(int pg) {
        this.pg = pg;
    }

    public void setMoisture(int moisture) {
        this.moisture = moisture;
    }

    public void setHole(int hole) {
        this.hole = hole;
    }

    public void setTone(int tone) {
        this.tone = tone;
    }

    public void setColor_washing(int color_washing) {
        this.color_washing = color_washing;
    }

    public void setFold(int fold) {
        this.fold = fold;
    }

    public void setOil(int oil) {
        this.oil = oil;
    }
}