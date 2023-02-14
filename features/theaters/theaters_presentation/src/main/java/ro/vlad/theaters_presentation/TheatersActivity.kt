package ro.vlad.theaters_presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ro.vlad.core.R
import ro.vlad.core.util.NavigationDestinations
import ro.vlad.core.util.Navigator
import ro.vlad.theaters_presentation.adapters.TheatersTabAdapter
import ro.vlad.theaters_presentation.databinding.ActivityTheatersBinding
import javax.inject.Inject

@AndroidEntryPoint
class TheatersActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityTheatersBinding
    @Inject
    lateinit var navigatorProvider: Navigator.Provider

    companion object {
        fun launchActivity(activity: Activity) {
            val intent = Intent(activity, TheatersActivity::class.java)
            activity.startActivity(intent)
        }
    }

    object NavigateToTheatersActivity : Navigator {
        override fun navigate(activity: Activity, movieId: String?, searchKeywords: String?) {
            launchActivity(activity)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTheatersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val theatersViewPager = binding.viewPager
        val adapter = TheatersTabAdapter(this)
        theatersViewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, theatersViewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        setupDrawerNavigation()

        binding.searchButton.setOnClickListener {
            navigatorProvider.getDestinations(NavigationDestinations.SearchActivity).navigate(this)
        }

    }

    private fun setupDrawerNavigation() {
        binding.navigationView.bringToFront()
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navigationView.menu.findItem(R.id.navigation_inTheaters).isChecked = true

        binding.menuButton.setOnClickListener {
            val navDrawer = binding.drawerLayout
            if (!navDrawer.isDrawerOpen(GravityCompat.START)) {
                navDrawer.openDrawer(GravityCompat.START)
            }
        }
        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            0 -> getString(R.string.tab_now_showing)
            1 -> getString(R.string.tab_box_office)
            2 -> getString(R.string.tab_coming_soon)
            else -> null
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_top250 -> {
                navigatorProvider.getDestinations(NavigationDestinations.TopActivity).navigate(this)
                finish()
            }
            R.id.navigation_mostPopular -> {
                navigatorProvider.getDestinations(NavigationDestinations.PopularActivity)
                    .navigate(this)
                finish()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}