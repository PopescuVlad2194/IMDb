package ro.vlad.theaters_presentation.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ro.vlad.core.util.getFragmentByTag
import ro.vlad.core_ui.util.TagScreens
import ro.vlad.theaters_presentation.TheatersFragment

class TheatersTabAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = TheatersFragment()
        when (position) {
            0 -> return getFragmentByTag(fragment, TagScreens.NowShowing.tag)
            1 -> return getFragmentByTag(fragment, TagScreens.BoxOffice.tag)
            2 -> return getFragmentByTag(fragment, TagScreens.ComingSoon.tag)
        }
        return getFragmentByTag(fragment, TagScreens.NowShowing.tag)
    }
}


