# object-oriented-logging
This project demonstrates how to log what is happening during the code execution to many sources at the same time by using Timber. 

The solution is to plant different `Timber.Tree`s each of which implements its own logging implementation
```kotlin
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
```

However, error tracking services' API may differ and there may be not enough to have only standard `Timber` API. See `TimberExtensions.kt` to see how to implement report sending to `Firebase Crashlytics` and `Yandex AppMetrica` by single signature. One can conclude that it is definitely better to use the only one error tracking service and migrate from one to the other as fast as possible. Otherwise your logging functions can have too many different parameters needed for each concrete error tracking service and can become unclear to comprehend

Also, this project shows how to use `Timber` in unit tests. You can track the fact that logging itself has happened.
For example, you can write such unit test 
```kotlin
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
```
See `MainViewModelTest.kt` and `UnitTestTree.kt` for more details 
