package ro.vlad.top_presentation.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ro.vlad.core.util.getFragmentByTag
import ro.vlad.core_ui.util.TagScreens
import ro.vlad.top_presentation.TopFragment

class TopTabAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = TopFragment()
        when (position) {
            0 -> return getFragmentByTag(fragment, TagScreens.TopMovies.tag)
            1 -> return getFragmentByTag(fragment, TagScreens.TopShows.tag)
        }
        return getFragmentByTag(fragment, TagScreens.TopMovies.tag)
    }
}