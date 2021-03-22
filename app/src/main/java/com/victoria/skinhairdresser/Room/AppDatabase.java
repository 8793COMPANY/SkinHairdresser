package com.victoria.skinhairdresser.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Measurement.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MeasurementDao measurementDao();
}