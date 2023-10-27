package mx.irisoft.pruebatecniva

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private var _isConnected = MutableLiveData<Boolean>().apply {
        value = false
    }

    private var currentDate = 0L

    fun setConnected(isConnected : Boolean){
        this@AppTest._isConnected.postValue(isConnected)
    }

    val isConnected: LiveData<Boolean> get() = _isConnected
}
