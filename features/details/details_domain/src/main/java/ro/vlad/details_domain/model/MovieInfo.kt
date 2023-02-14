package ro.vlad.details_domain.model

data class MovieInfo(
    val movieImage: String,
    val movieDuration: String,
    val movieState: String,
    val movieRating: String,
    val movieRatingVotes: String?,
    val moviePlot: String,
    val movieDirectors: String,
    val movieGenresDescription: String,
    val movieTitle: String,
    val movieCast: List<Actor>
)
