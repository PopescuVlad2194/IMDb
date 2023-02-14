package ro.vlad.imdb.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ro.vlad.core.util.ConnectivityObserver
import ro.vlad.core.util.Navigator
import ro.vlad.imdb.util.DefaultNavigator
import ro.vlad.imdb.util.NetworkConnectivityObserver
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(
        @ApplicationContext appContext: Context
    ): ConnectivityObserver {
        return NetworkConnectivityObserver(appContext)
    }

    @Provides
    @Singleton
    fun provideProvider(): Navigator.Provider {
        return DefaultNavigator()
    }
}