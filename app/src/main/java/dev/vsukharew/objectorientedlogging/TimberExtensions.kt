package dev.vsukharew.objectorientedlogging

import dev.vsukharew.objectorientedlogging.tree.CrashlyticsTree
import timber.log.Timber

fun Timber.Forest.setCustomKey(key: String, value: String) {
    forest()
        .filterIsInstance<CrashlyticsTree>()
        .forEach { it.setCustomKey(key, value) }
}

fun Timber.Forest.e(
    reportId: String?,
    t: Throwable?,
    message: String?,
    vararg args: Any?
): Timber.Forest {
    return apply {
        forest().forEach {
            when (it) {
                is CrashlyticsTree -> it.e(
                    reportId = reportId,
                    t = t,
                    message = message,
                    args = args
                )
                else -> it.e(t, message, *args)
            }
        }
    }
}