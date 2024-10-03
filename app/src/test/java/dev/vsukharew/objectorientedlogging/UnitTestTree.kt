package dev.vsukharew.objectorientedlogging

import timber.log.Timber

class UnitTestTree : Timber.Tree() {
    val logs = mutableListOf<Log>()

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        logs.add(Log(priority, tag, message, t))
    }
}