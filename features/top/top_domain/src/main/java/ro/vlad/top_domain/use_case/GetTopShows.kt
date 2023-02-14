package ro.vlad.top_domain.use_case

import ro.vlad.top_domain.model.TopMovie
import ro.vlad.top_domain.repository.TopRepository

class GetTopShows(
    private val repository: TopRepository
) {

    suspend operator fun invoke(): Result<List<TopMovie>?> {
        return repository.getTopShows()
    }
}