package com.example.remediescontrol.data
import androidx.room.*
import androidx.room.Dao
import com.example.remediescontrol.model.Remedy

@Dao
interface Dao {

    @Insert
    fun newRemedy(remedy: Remedy)

    @Query("SELECT * FROM Remedy")
    fun getAllRemedies(): MutableList<Remedy>

    @Update
    fun updateRemedy(remedy: Remedy)

    @Query("DELETE FROM Remedy WHERE id = :id")
    fun deleteRemedy(id: Int)

    @Query("SELECT MAX(ID) FROM Remedy")
    fun getId(): Int

}