package ro.vlad.details_data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ro.vlad.core.data.remote.IMDbApi
import ro.vlad.details_data.repository.DetailsRepositoryImpl
import ro.vlad.details_domain.repository.DetailsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailsDataModule {

    @Provides
    @Singleton
    fun provideDetailsRepository(
        api: IMDbApi
    ): DetailsRepository {
        return DetailsRepositoryImpl(
            api = api
        )
    }
}