package ro.vlad.search_domain.use_case

import ro.vlad.search_domain.model.ResultEntry
import ro.vlad.search_domain.model.SearchResults
import ro.vlad.search_domain.repository.SearchRepository

class GetSearchResults(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(expression: String): Result<List<ResultEntry>?> {
        return repository.getSearchResults(expression)
    }
}