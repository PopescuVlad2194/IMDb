package ro.vlad.imdb.util

import ro.vlad.core.util.NavigationDestinations
import ro.vlad.core.util.Navigator
import ro.vlad.details_presentation.DetailsActivity
import ro.vlad.popular_presentation.PopularActivity
import ro.vlad.search_presentation.SearchActivity
import ro.vlad.theaters_presentation.TheatersActivity
import ro.vlad.top_presentation.TopActivity

class DefaultNavigator : Navigator.Provider {

    override fun getDestinations(destinations: NavigationDestinations): Navigator {
        return when (destinations) {
            NavigationDestinations.TheatersActivity -> {
                TheatersActivity.NavigateToTheatersActivity
            }
            NavigationDestinations.TopActivity -> {
                TopActivity.NavigateToTopActivity
            }
            NavigationDestinations.PopularActivity -> {
                PopularActivity.NavigateToPopularActivity
            }
            NavigationDestinations.DetailsActivity -> {
                DetailsActivity.NavigateToDetailsActivity
            }
            NavigationDestinations.SearchActivity -> {
                SearchActivity.NavigateToSearchActivity
            }
        }
    }
}