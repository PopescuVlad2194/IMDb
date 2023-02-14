package ro.vlad.core.util

import android.app.Activity

interface Navigator {

    fun navigate(activity: Activity, movieId: String? = null, searchKeywords: String? = null)

    interface Provider {
        fun getDestinations(destinations: NavigationDestinations): Navigator
    }
}