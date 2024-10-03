package dev.vsukharew.objectorientedlogging

data class Log(
    val priority: Int,
    val tag: String?,
    val message: String,
    val t: Throwable?
)