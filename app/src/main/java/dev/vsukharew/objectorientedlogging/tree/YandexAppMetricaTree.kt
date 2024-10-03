package dev.vsukharew.objectorientedlogging.tree

import android.util.Log
import io.appmetrica.analytics.AppMetrica
import timber.log.Timber

class YandexAppMetricaTree : Timber.Tree(), CrashlyticsTree {

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
        when {
            tag == null -> AppMetrica.reportError(message, t)
            t == null -> AppMetrica.reportError(tag, message)
            else -> AppMetrica.reportError(tag, message, t)
        }
    }

    override fun setCustomKey(key: String, value: String) {}
}