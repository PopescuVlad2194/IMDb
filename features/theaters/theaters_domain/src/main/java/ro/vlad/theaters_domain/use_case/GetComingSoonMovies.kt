package ro.vlad.theaters_domain.use_case

import ro.vlad.theaters_domain.model.ComingSoonMovie
import ro.vlad.theaters_domain.repository.TheatersRepository

class GetComingSoonMovies(
    private val repository: TheatersRepository
) {

    suspend operator fun invoke(): Result<List<ComingSoonMovie>?> {
        return repository.getComingSoonMovies()
    }
}