package ro.vlad.theaters_domain.use_case

import ro.vlad.theaters_domain.model.BoxOfficeMovie
import ro.vlad.theaters_domain.repository.TheatersRepository

class GetBoxOfficeMovies(
    private val repository: TheatersRepository
) {

    suspend operator fun invoke(): Result<List<BoxOfficeMovie>?> {
        return repository.getBoxOfficeMovies()
    }
}