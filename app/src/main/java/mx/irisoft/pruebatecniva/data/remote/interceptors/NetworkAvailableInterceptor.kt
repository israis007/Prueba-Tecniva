package mx.irisoft.pruebatecniva.data.remote.interceptors

import android.util.Log
import mx.irisoft.pruebatecniva.AppTest
import mx.irisoft.pruebatecniva.data.remote.exceptions.NoNetworkAvailableException
import mx.irisoft.pruebatecniva.utils.tools.NetworkChecker
import okhttp3.Interceptor
import okhttp3.Response

class NetworkAvailableInterceptor : Interceptor {

    private val TAG = "NetworkInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (!NetworkChecker.isConnected()) {
            Log.d(TAG, "Device is not connect to any network")
            AppTest.instance.setConnected(false)
            throw NoNetworkAvailableException("No connected to Internet")
        } else
            AppTest.instance.setConnected(true)
        return chain.proceed(request)
    }
}