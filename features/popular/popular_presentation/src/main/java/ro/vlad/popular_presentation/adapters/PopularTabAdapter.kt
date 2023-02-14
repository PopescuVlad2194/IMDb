package ro.vlad.popular_presentation.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ro.vlad.core.util.getFragmentByTag
import ro.vlad.core_ui.util.TagScreens
import ro.vlad.popular_presentation.PopularFragment

class PopularTabAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = PopularFragment()
        when (position) {
            0 -> return getFragmentByTag(fragment, TagScreens.PopularMovies.tag)
            1 -> return getFragmentByTag(fragment, TagScreens.PopularShows.tag)
        }
        return getFragmentByTag(fragment, TagScreens.PopularMovies.tag)
    }
}