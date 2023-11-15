package mx.irisoft.pruebatecniva.data.local.room.instances

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mx.irisoft.pruebatecniva.R
import mx.irisoft.pruebatecniva.data.local.room.daos.MovieLocalDao
import mx.irisoft.pruebatecniva.data.local.room.db.LocalDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): LocalDatabase {
        return Room.databaseBuilder(
            appContext,
            LocalDatabase::class.java,
            appContext.getString(R.string.database_name)
        ).build()
    }

    @Provides
    fun provideMovieLocalDao(localDatabase: LocalDatabase): MovieLocalDao {
        return localDatabase.movieLocal()
    }
}