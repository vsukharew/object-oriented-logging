package dev.vsukharew.objectorientedlogging.tree

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import timber.log.Timber

class FirebaseTree : Timber.Tree(), CrashlyticsTree {
    private val crashlytics = Firebase.crashlytics

    init {
        crashlytics.sendUnsentReports()
    }

    override fun setCustomKey(key: String, value: String) {
        crashlytics.setCustomKey(key, value)
    }

    override fun e(reportId: String?, t: Throwable?, message: String?, vararg args: Any?) {
        if (message.isNullOrEmpty() && t == null) {
            return
        }
        log(Log.ERROR, reportId, message?.format(*args).orEmpty(), t)
    }

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority != Log.DEBUG && priority != Log.INFO && priority != Log.VERBOSE
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        t ?: return
        with(crashlytics) {
            if (message.isNotEmpty()) {
                log(message)
            }
            recordException(t)
        }
    }
}