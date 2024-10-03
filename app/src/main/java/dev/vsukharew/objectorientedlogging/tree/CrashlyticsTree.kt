package dev.vsukharew.objectorientedlogging.tree

interface CrashlyticsTree {
    fun setCustomKey(key: String, value: String)
    fun e(reportId: String?, t: Throwable?, message: String?, vararg args: Any?)
}