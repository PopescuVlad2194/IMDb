package ro.vlad.theaters_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ro.vlad.theaters_domain.repository.TheatersRepository
import ro.vlad.theaters_domain.use_case.GetBoxOfficeMovies
import ro.vlad.theaters_domain.use_case.GetComingSoonMovies
import ro.vlad.theaters_domain.use_case.GetNowShowingMovies
import ro.vlad.theaters_domain.use_case.TheatersUseCases

@Module
@InstallIn(ViewModelComponent::class)
object TheatersDomainModule {

    @Provides
    @ViewModelScoped
    fun provideTheatersUseCases(
        repository: TheatersRepository
    ): TheatersUseCases {
        return TheatersUseCases(
            getNowShowingMovies = GetNowShowingMovies(repository),
            getBoxOfficeMovies = GetBoxOfficeMovies(repository),
            getComingSoonMovies = GetComingSoonMovies(repository)
        )
    }

}