package ro.vlad.theaters_domain.model.now_showing

data class NowShowingMovie(
    val id: String,
    val movieImage: String,
    val movieTitle: String,
    val movieRuntimeMinutes: String,
    val movieReleaseDate: String
)
