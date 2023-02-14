package ro.vlad.theaters_domain.use_case

data class TheatersUseCases(
    val getNowShowingMovies: GetNowShowingMovies,
    val getBoxOfficeMovies: GetBoxOfficeMovies,
    val getComingSoonMovies: GetComingSoonMovies
)
