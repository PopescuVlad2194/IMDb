package ro.vlad.theaters_domain.use_case

import ro.vlad.theaters_domain.model.now_showing.NowShowingMovie
import ro.vlad.theaters_domain.repository.TheatersRepository

class GetNowShowingMovies(
    private val repository: TheatersRepository
) {

    suspend operator fun invoke(): Result<List<NowShowingMovie>?> {
        return repository.getNowShowingMovies()
    }
}