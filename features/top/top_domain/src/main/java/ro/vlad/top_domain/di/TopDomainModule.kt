package ro.vlad.top_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ro.vlad.top_domain.repository.TopRepository
import ro.vlad.top_domain.use_case.GetTopMovies
import ro.vlad.top_domain.use_case.GetTopShows
import ro.vlad.top_domain.use_case.TopUseCases

@Module
@InstallIn(ViewModelComponent::class)
object TopDomainModule {

    @Provides
    @ViewModelScoped
    fun provideTopUseCases(
        repository: TopRepository
    ): TopUseCases {
        return TopUseCases(
            getTopMovies = GetTopMovies(repository),
            getTopShows = GetTopShows(repository)
        )
    }
}