package dev.vsukharew.objectorientedlogging

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import timber.log.Timber

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @Before
    fun before() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun e() {
        runTest {
            val tree = UnitTestTree()
            Timber.plant(tree)
            val viewModel = MainViewModel()
            viewModel.e()
            assertEquals(1, tree.logs.size)
        }
    }

    @Test
    fun w() {
        runTest {
            val tree = UnitTestTree()
            Timber.plant(tree)
            val viewModel = MainViewModel()
            viewModel.w()
            assertEquals(3, tree.logs.size)
        }
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }
}