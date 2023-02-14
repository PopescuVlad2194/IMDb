package ro.vlad.search_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import ro.vlad.search_domain.repository.SearchRepository
import ro.vlad.search_domain.use_case.GetSearchResults

@Module
@InstallIn(ViewModelComponent::class)
object SearchDomainModule {

    @Provides
    @ViewModelScoped
    fun provideSearchUseCase(
        repository: SearchRepository
    ): GetSearchResults {
        return GetSearchResults(repository)
    }
}