package dev.vsukharew.objectorientedlogging.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LogEntryDao {
    @Insert
    suspend fun insert(entry: LogEntry)

    @Query("DELETE FROM logs")
    suspend fun deleteAll()

    @Query("SELECT COUNT (*) FROM logs")
    suspend fun count(): Int
}