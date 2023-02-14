package ro.vlad.theaters_domain.model

data class BoxOfficeMovie(
    val id: String,
    val movieImage: String,
    val movieTitle: String,
    val movieEarningsWeekend: String,
    val movieEarningsGross: String,
    val movieWeeks: String,
    val movieRank: String
)
