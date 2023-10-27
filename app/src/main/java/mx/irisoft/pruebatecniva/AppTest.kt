package mx.irisoft.pruebatecniva

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppTest : MultiDexApplication() {

    companion object {
        lateinit var instance: AppTest
            private set
    }

    init {
        @JvmStatic
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}
