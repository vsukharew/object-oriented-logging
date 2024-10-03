package dev.vsukharew.objectorientedlogging

import android.app.ActivityManager
import android.app.Application
import android.os.Build
import android.os.Process
import androidx.room.Room
import com.google.firebase.BuildConfig
import com.google.firebase.FirebaseApp
import dev.vsukharew.objectorientedlogging.lifecycle.LoggingActivityLifecycleCallback
import dev.vsukharew.objectorientedlogging.room.AppDatabase
import dev.vsukharew.objectorientedlogging.tree.FileTree
import dev.vsukharew.objectorientedlogging.tree.FirebaseTree
import dev.vsukharew.objectorientedlogging.tree.RoomTree
import dev.vsukharew.objectorientedlogging.tree.YandexAppMetricaTree
import io.appmetrica.analytics.AppMetrica
import io.appmetrica.analytics.AppMetricaConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeAppMetrica(this)
        initializeTimber(this)
        if (!isMainProcess()) return
        registerActivityLifecycleCallbacks(LoggingActivityLifecycleCallback())
        Thread.setDefaultUncaughtExceptionHandler { _, exception ->
            Timber.e(exception)
        }
    }

    private fun initializeAppMetrica(app: App) {
        runIfBuild {
            when (it) {
                is BuildType.Debug -> {
                    // empty implementation
                }
                is BuildType.Release -> {
                    FirebaseApp.initializeApp(app)
                    AppMetrica.activate(
                        app,
                        AppMetricaConfig
                            .newConfigBuilder("d014fc12-4b23-4296-bcbb-76d810dbddbf")
                            .build()
                    )
                }
            }
        }
    }

    private fun initializeTimber(app: App) {
        runIfBuild {
            when (it) {
                is BuildType.Debug -> {
                    arrayOf(
                        Timber.DebugTree(),
                        FileTree(app)
                    )
                }
                is BuildType.Release -> {
                    arrayOf(
                        RoomTree(createDatabase(app), CoroutineScope(Job())),
                        YandexAppMetricaTree(),
                        FirebaseTree()
                    )
                }
            }
        }.let(Timber::plant)
    }

    private fun createDatabase(app: App): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "app_database")
            .build()
    }

    private fun isMainProcess(): Boolean {
        val currentPid = Process.myPid()
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses = manager.runningAppProcesses
        val currentProcessName = runningProcesses.find { it.pid == currentPid }?.processName
        return packageName == currentProcessName
    }
}