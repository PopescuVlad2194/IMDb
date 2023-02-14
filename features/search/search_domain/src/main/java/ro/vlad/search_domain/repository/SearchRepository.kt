package ro.vlad.search_domain.repository

import ro.vlad.search_domain.model.ResultEntry

interface SearchRepository {
    suspend fun getSearchResults(expression: String): Result<List<ResultEntry>?>
}