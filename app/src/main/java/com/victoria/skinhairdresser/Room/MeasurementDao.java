package com.victoria.skinhairdresser.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MeasurementDao {
    @Query("SELECT * FROM measurement_table")
    List<Measurement> getAll();

    @Query("SELECT * FROM measurement_table WHERE year LIKE :year AND month LIKE :month " +
    "ORDER BY day DESC")
    Measurement findByYm(String year, String month);

    @Update
    void update(Measurement measurement);

    @Insert
    void insertAll(Measurement... measurements);

    @Delete
    void delete(Measurement measurement);
}