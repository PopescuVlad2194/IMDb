package ro.vlad.popular_domain.use_case

import ro.vlad.popular_domain.model.PopularMovie
import ro.vlad.popular_domain.repository.PopularRepository

class GetPopularShows(
    private val repository: PopularRepository
) {

    suspend operator fun invoke(): Result<List<PopularMovie>?> {
        return repository.getPopularShows()
    }
}