package ro.vlad.top_data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ro.vlad.core.data.remote.IMDbApi
import ro.vlad.top_data.repository.TopRepositoryImpl
import ro.vlad.top_domain.repository.TopRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TopDataModule {

    @Provides
    @Singleton
    fun provideTopRepository(
        api: IMDbApi
    ): TopRepository {
        return TopRepositoryImpl(
            api = api
        )
    }
}