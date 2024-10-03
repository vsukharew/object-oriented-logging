package dev.vsukharew.objectorientedlogging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : ViewModel() {

    fun i() {
        viewModelScope.launch {
            Timber.i("Invoke Timber.i() | args: %s, %s, %.1f", *anyArgs)
            Timber.i(Exception())
            Timber.i(Exception(), "Invoke Timber.i()", *anyArgs)
        }
    }

    fun d() {
        viewModelScope.launch {
            Timber.d("Invoke Timber.d() | args: %s, %s, %.1f", *anyArgs)
            Timber.d(Exception())
            Timber.d(Exception(), "Invoke Timber.d()", *anyArgs)
        }
    }

    fun w() {
        viewModelScope.launch {
            Timber.w("Invoke Timber.w() | args: %s, %s, %.1f", *anyArgs)
            Timber.w(Exception())
            Timber.w(Exception(), "Invoke Timber.w()", *anyArgs)
        }
    }

    fun e() {
        viewModelScope.launch {
            Timber.e(
                reportId = "object_oriented_logging",
                t = Exception(),
                message = "Invoke Timber.e() | args: %s, %s, %.1f",
                args = anyArgs
            )
        }
    }

    private companion object {
        val anyArgs = arrayOf("payload", 0xFF, 2.0f)
    }
}