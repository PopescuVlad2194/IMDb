package ro.vlad.popular_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ro.vlad.popular_domain.repository.PopularRepository
import ro.vlad.popular_domain.use_case.GetPopularMovies
import ro.vlad.popular_domain.use_case.GetPopularShows
import ro.vlad.popular_domain.use_case.PopularUseCases

@Module
@InstallIn(ViewModelComponent::class)
object PopularDomainModule {

    @Provides
    @ViewModelScoped
    fun providePopularUseCases(
        repository: PopularRepository
    ): PopularUseCases {
        return PopularUseCases(
            getPopularMovies = GetPopularMovies(repository),
            getPopularShows = GetPopularShows(repository)
        )
    }
}