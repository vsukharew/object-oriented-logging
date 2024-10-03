package dev.vsukharew.objectorientedlogging

import android.util.Log

fun priorityToString(priority: Int): String {
    return when (priority) {
        Log.DEBUG -> "DEBUG"
        Log.ERROR -> "ERROR"
        Log.INFO -> "INFO"
        Log.VERBOSE -> "VERBOSE"
        Log.WARN -> "WARN"
        Log.ASSERT -> "ASSERT"
        else -> ""
    }
}

sealed class BuildType {
    data object Debug : BuildType()
    data object Release : BuildType()
}

// warning is ignored because you're always inside the given build typeF
@Suppress("KotlinConstantConditions")
fun <T> runIfBuild(buildTypeBlock: (BuildType) -> T): T {
    val buildType = when (BuildConfig.BUILD_TYPE) {
        "debug" -> BuildType.Debug
        "release" -> BuildType.Release
        else -> throw Exception()
    }
    return buildTypeBlock.invoke(buildType)
}
