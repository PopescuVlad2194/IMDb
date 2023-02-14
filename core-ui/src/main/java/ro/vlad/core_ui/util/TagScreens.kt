package ro.vlad.core_ui.util

sealed class TagScreens(val tag: String) {
    object PopularMovies : TagScreens("PopularMovies")
    object PopularShows : TagScreens("PopularShows")
    object NowShowing : TagScreens("NowShowing")
    object BoxOffice : TagScreens("BoxOffice")
    object ComingSoon : TagScreens("ComingSoon")
    object TopMovies : TagScreens("TopMovies")
    object TopShows : TagScreens("TopShows")
}
