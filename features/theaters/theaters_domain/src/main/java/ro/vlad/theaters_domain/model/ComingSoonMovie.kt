package ro.vlad.theaters_domain.model

data class ComingSoonMovie(
    val id: String,
    val movieImage: String,
    val movieTitle: String,
    val movieRuntimeMinutes: String,
    val movieReleaseDate: String
)
