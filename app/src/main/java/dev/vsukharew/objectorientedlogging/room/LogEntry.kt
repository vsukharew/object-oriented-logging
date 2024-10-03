package dev.vsukharew.objectorientedlogging.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logs")
data class LogEntry(
    val date: String,
    val priority: String,
    val tag: String?,
    val message: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)