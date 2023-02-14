package ro.vlad.details_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ro.vlad.details_domain.repository.DetailsRepository
import ro.vlad.details_domain.use_case.GetMovieDetails

@Module
@InstallIn(ViewModelComponent::class)
object DetailsDomainModule {

    @Provides
    @ViewModelScoped
    fun provideDetailsUseCase(
        repository: DetailsRepository
    ): GetMovieDetails {
        return GetMovieDetails(repository)
    }
}