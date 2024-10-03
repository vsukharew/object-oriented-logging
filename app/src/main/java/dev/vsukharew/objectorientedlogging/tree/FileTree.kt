package dev.vsukharew.objectorientedlogging.tree

import android.content.Context
import android.content.Context.MODE_PRIVATE
import dev.vsukharew.objectorientedlogging.priorityToString
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FileTree(context: Context) : Timber.DebugTree(), CloseableTree {
    private val fileStream = context
        .openFileOutput("logs.txt", MODE_PRIVATE)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    private val file = File("${context.filesDir}/logs.txt")

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val date = dateFormat.format(Date())
        if (file.length() > MB) {
            clearFileContents(file)
        }
        fileStream.write("$date | ${priorityToString(priority)} | $tag | $message".toByteArray())
    }

    override fun close() {
        fileStream.close()
    }

    private fun clearFileContents(file: File) {
        FileOutputStream(file).write("".toByteArray())
    }

    companion object {
        private const val MB = 1024 * 1024
    }
}