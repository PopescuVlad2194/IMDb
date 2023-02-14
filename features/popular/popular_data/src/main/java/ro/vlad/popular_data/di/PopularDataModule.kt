package ro.vlad.popular_data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ro.vlad.core.data.remote.IMDbApi
import ro.vlad.popular_data.repository.PopularRepositoryImpl
import ro.vlad.popular_domain.repository.PopularRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PopularDataModule {

    @Provides
    @Singleton
    fun providePopularRepository(
        api: IMDbApi
    ): PopularRepository {
        return PopularRepositoryImpl(
            api = api
        )
    }
}