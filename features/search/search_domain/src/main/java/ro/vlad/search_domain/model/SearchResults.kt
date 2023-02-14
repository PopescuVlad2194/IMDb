package ro.vlad.search_domain.model

data class SearchResults(
    val resultEntries: List<ResultEntry>,
    val searchType: String
)
