package ro.vlad.search_data.repository

import ro.vlad.core.data.remote.IMDbApi
import ro.vlad.search_data.mapper.toResultEntry
import ro.vlad.search_domain.model.ResultEntry
import ro.vlad.search_domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val api: IMDbApi
): SearchRepository {
    override suspend fun getSearchResults(expression: String): Result<List<ResultEntry>?> {
        return try {
            val dto = api.getSearchResult(expression = expression)
            Result.success(
                dto.results?.map { it.toResultEntry() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}