package ru.aip.intern.di

import android.app.DownloadManager
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.aip.intern.networking.ConnectivityObserver
import ru.aip.intern.networking.NetworkConnectivityObserver
import ru.aip.intern.snackbar.SnackbarMessageHandler
import ru.aip.intern.storage.DataStoreRepository
import ru.aip.intern.ui.managers.TitleManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreRepository(context)
    }

    @Singleton
    @Provides
    fun provideTitleManager(): TitleManager {
        return TitleManager()
    }

    @Provides
    @Singleton
    fun provideSnackbarMessageHandler(@ApplicationContext context: Context): SnackbarMessageHandler {
        return SnackbarMessageHandler(context)
    }

    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }

    @Provides
    @Singleton
    fun provideDownloadManager(@ApplicationContext context: Context): DownloadManager {
        return context.getSystemService(DownloadManager::class.java)
    }

}