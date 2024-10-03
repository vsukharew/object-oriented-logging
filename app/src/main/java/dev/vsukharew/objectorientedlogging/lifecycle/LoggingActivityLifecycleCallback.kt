package dev.vsukharew.objectorientedlogging.lifecycle

import android.app.Activity
import dev.vsukharew.objectorientedlogging.tree.CloseableTree
import timber.log.Timber

class LoggingActivityLifecycleCallback : ActivityLifecycleCallback() {
    override fun onActivityDestroyed(activity: Activity) {
        if (activity.isFinishing) {
            Timber.forest()
                .filterIsInstance<CloseableTree>()
                .forEach(CloseableTree::close)
        }
    }
}

