package ro.vlad.search_data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ro.vlad.core.data.remote.IMDbApi
import ro.vlad.search_data.repository.SearchRepositoryImpl
import ro.vlad.search_domain.repository.SearchRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchDataModule {

    @Provides
    @Singleton
    fun provideSearchRepository(
        api: IMDbApi
    ): SearchRepository {
        return SearchRepositoryImpl(
            api = api
        )
    }
}