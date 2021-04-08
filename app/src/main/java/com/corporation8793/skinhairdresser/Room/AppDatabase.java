package com.corporation8793.skinhairdresser.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Measurement.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MeasurementDao measurementDao();
}