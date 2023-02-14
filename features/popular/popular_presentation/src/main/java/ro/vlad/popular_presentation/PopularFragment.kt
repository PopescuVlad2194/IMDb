package ro.vlad.popular_presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ro.vlad.core.R
import ro.vlad.core.util.*
import ro.vlad.core.util.Constants.FRAGMENT_TAG
import ro.vlad.core_ui.util.TagScreens
import ro.vlad.popular_domain.model.PopularMovie
import ro.vlad.popular_presentation.adapters.PopularMoviesAdapter
import ro.vlad.popular_presentation.databinding.FragmentPopularBinding
import javax.inject.Inject

@AndroidEntryPoint
class PopularFragment : Fragment() {
    @Inject
    lateinit var navigatorProvider: Navigator.Provider
    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private val popularViewModel: PopularViewModel by viewModels()
    private lateinit var fragmentTag: String
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private lateinit var popularShowsAdapter: PopularMoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            getMoviesByTag()
        }
    }

    private fun getMoviesByTag() {
        when (fragmentTag) {
            TagScreens.PopularMovies.tag -> popularViewModel.getPopularMovies()
            TagScreens.PopularShows.tag -> popularViewModel.getPopularShows()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = popularViewModel
            lifecycleOwner = this@PopularFragment.viewLifecycleOwner
        }

        arguments?.let {
            fragmentTag = it.getString(FRAGMENT_TAG, "")
        }
        setupMovieAdapter(fragmentTag)
        collectState(fragmentTag)
        setupRefreshListener()
    }

    private fun setupRefreshListener() {
        binding.swipeLayout.setOnRefreshListener {
            lifecycleScope.launch {
                when (fragmentTag) {
                    TagScreens.PopularMovies.tag -> {
                        popularViewModel.popularMoviesState.collect { state ->
                            handleSwipingState(state)
                        }
                    }
                    TagScreens.PopularShows.tag -> {
                        popularViewModel.popularShowsState.collect { state ->
                            handleSwipingState(state)
                        }
                    }
                }
            }
        }
    }

    private fun handleSwipingState(state: RequestStateMultipleResults) {
        when (state) {
            is RequestStateMultipleResults.Error -> {
                when (state.message) {
                    (ErrorCode.Internet.name), (ErrorCode.Network.name) -> {
                        getMoviesByTag()
                        binding.swipeLayout.isRefreshing = false
                    }
                }
            }
            else -> binding.swipeLayout.isRefreshing = false
        }
    }

    private fun collectState(tag: String) {
        lifecycleScope.launchWhenStarted {
            when (tag) {
                TagScreens.PopularMovies.tag -> {
                    popularViewModel.popularMoviesState.collect { state ->
                        when (state) {
                            is RequestStateMultipleResults.Success<*> -> {
                                state.data.let { resultList ->
                                    popularMoviesAdapter.differ.submitList(resultList.filterIsInstance<PopularMovie>())
                                }
                            }
                            is RequestStateMultipleResults.Error -> {
                                if (popularMoviesAdapter.differ.currentList.isEmpty()) {
                                    displayOnScreenError(state)
                                }
                            }
                            else -> {}
                        }
                    }
                }
                TagScreens.PopularShows.tag -> {
                    popularViewModel.popularShowsState.collect { state ->
                        when (state) {
                            is RequestStateMultipleResults.Success<*> -> {
                                state.data.let { resultList ->
                                    popularShowsAdapter.differ.submitList(resultList.filterIsInstance<PopularMovie>())
                                }
                            }
                            is RequestStateMultipleResults.Error -> {
                                if (popularShowsAdapter.differ.currentList.isEmpty()) {
                                    displayOnScreenError(state)
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    private fun displayOnScreenError(state: RequestStateMultipleResults.Error) {
        when (state.message) {
            ErrorCode.Network.name -> popularViewModel.setErrorMessage(
                getString(R.string.network_error)
            )
            ErrorCode.Internet.name -> popularViewModel.setErrorMessage(
                getString(R.string.internet_error)
            )
            ErrorCode.Unknown.name -> popularViewModel.setErrorMessage(
                getString(R.string.unknown_error)
            )
        }
    }

    private fun setupMovieAdapter(tag: String) {
        when (tag) {
            TagScreens.PopularMovies.tag -> {
                popularMoviesAdapter = PopularMoviesAdapter()
                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(
                        activity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = popularMoviesAdapter
                }
                popularMoviesAdapter.onClick = { movie ->
                    navigateWithMovieId(movie.id)
                }
            }
            TagScreens.PopularShows.tag -> {
                popularShowsAdapter = PopularMoviesAdapter()
                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(
                        activity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = popularShowsAdapter
                }
                popularShowsAdapter.onClick = { movie ->
                    navigateWithMovieId(movie.id)
                }
            }
        }
    }

    private fun navigateWithMovieId(movieId: String) {
        navigatorProvider.getDestinations(NavigationDestinations.DetailsActivity)
            .navigate(requireActivity(), movieId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}