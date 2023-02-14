package ro.vlad.core.util

sealed class NavigationDestinations {
    object TheatersActivity : NavigationDestinations()
    object TopActivity : NavigationDestinations()
    object PopularActivity : NavigationDestinations()
    object DetailsActivity : NavigationDestinations()
    object SearchActivity : NavigationDestinations()
}