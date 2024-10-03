package dev.vsukharew.objectorientedlogging.tree

import dev.vsukharew.objectorientedlogging.priorityToString
import dev.vsukharew.objectorientedlogging.room.AppDatabase
import dev.vsukharew.objectorientedlogging.room.LogEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RoomTree(
    private val database: AppDatabase,
    private val scope: CoroutineScope
) : Timber.DebugTree(), CloseableTree {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val date = dateFormat.format(Date())
        scope.launch(Dispatchers.IO) {
            with(database.logEntryDao()) {
                if (count() > 10) {
                    deleteAll()
                }
                insert(
                    LogEntry(
                        date,
                        priorityToString(priority),
                        tag,
                        message,
                    )
                )
            }
        }
    }

    override fun close() {
        database.close()
    }
}