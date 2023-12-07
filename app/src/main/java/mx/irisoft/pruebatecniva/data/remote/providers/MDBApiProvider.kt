package mx.irisoft.pruebatecniva.data.remote.providers

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mx.irisoft.pruebatecniva.BuildConfig
import mx.irisoft.pruebatecniva.data.remote.api.InterfaceMDB
import mx.irisoft.pruebatecniva.data.remote.interceptors.NetworkAvailableInterceptor
import mx.irisoft.pruebatecniva.utils.constants.CLIENT_MDB
import mx.irisoft.pruebatecniva.utils.constants.RETROFIT_MDB
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MDBApiProvider @Inject constructor() {

    private val gsonConverterFactory by lazy {
        GsonConverterFactory.create(
            Gson().newBuilder()
                .serializeNulls()
                .setDateFormat(
                    BuildConfig.DATE_FORMAT,
                ).create(),
        )
    }

    private val logInterceptor: Interceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @Named(CLIENT_MDB)
    fun okHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(NetworkAvailableInterceptor())
                .callTimeout(
                    BuildConfig.TIMEOUT_SECONDS,
                    TimeUnit.SECONDS,
                )
                .readTimeout(
                    BuildConfig.TIMEOUT_SECONDS,
                    TimeUnit.SECONDS,
                )
                .connectTimeout(
                    BuildConfig.TIMEOUT_SECONDS,
                    TimeUnit.SECONDS,
                )
            if (BuildConfig.DEBUG) {
                addInterceptor(logInterceptor)
            }
        }.build()

    @Provides
    @Singleton
    @Named(RETROFIT_MDB)
    fun retrofit(@Named(CLIENT_MDB) okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(BuildConfig.BASE_URL)
            client(okHttpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(gsonConverterFactory)
        }.build()

    @Provides
    @Singleton
    fun mbdAPI(@Named(RETROFIT_MDB) retrofit: Retrofit): InterfaceMDB =
        retrofit.create(InterfaceMDB::class.java)
}
