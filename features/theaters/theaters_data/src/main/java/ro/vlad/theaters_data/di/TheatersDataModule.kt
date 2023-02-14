package ro.vlad.theaters_data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ro.vlad.core.data.remote.IMDbApi
import ro.vlad.theaters_data.repository.TheatersRepositoryImpl
import ro.vlad.theaters_domain.repository.TheatersRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TheatersDataModule {

    @Provides
    @Singleton
    fun provideTheatersRepository(
        api: IMDbApi
    ): TheatersRepository {
        return TheatersRepositoryImpl(
            api = api
        )
    }

}