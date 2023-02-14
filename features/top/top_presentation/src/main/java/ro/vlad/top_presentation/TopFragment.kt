package ro.vlad.top_presentation

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
import ro.vlad.core.util.Constants.FRAGMENT_TAG
import ro.vlad.core.util.ErrorCode
import ro.vlad.core.util.NavigationDestinations
import ro.vlad.core.util.Navigator
import ro.vlad.core.util.RequestStateMultipleResults
import ro.vlad.core_ui.util.TagScreens
import ro.vlad.top_domain.model.TopMovie
import ro.vlad.top_presentation.adapters.TopMoviesAdapter
import ro.vlad.top_presentation.databinding.FragmentTopBinding
import javax.inject.Inject

@AndroidEntryPoint
class TopFragment : Fragment() {
    private var _binding: FragmentTopBinding? = null
    private val binding get() = _binding!!
    private val topViewModel: TopViewModel by viewModels()
    private lateinit var fragmentTag: String
    private lateinit var topMoviesAdapter: TopMoviesAdapter
    private lateinit var topShowsAdapter: TopMoviesAdapter
    @Inject lateinit var navigatorProvider: Navigator.Provider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopBinding.inflate(inflater, container, false)
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
            TagScreens.TopMovies.tag -> topViewModel.getTopMovies()
            TagScreens.TopShows.tag -> topViewModel.getTopShows()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = topViewModel
            lifecycleOwner = this@TopFragment.viewLifecycleOwner
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
                    TagScreens.TopMovies.tag -> {
                        topViewModel.topMoviesState.collect { state ->
                            handleSwipingState(state)
                        }
                    }
                    TagScreens.TopShows.tag -> {
                        topViewModel.topShowsState.collect { state ->
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
                TagScreens.TopMovies.tag -> {
                    topViewModel.topMoviesState.collect { state ->
                        when (state) {
                            is RequestStateMultipleResults.Success<*> -> {
                                state.data.let { resultList ->
                                    topMoviesAdapter.differ.submitList(resultList.filterIsInstance<TopMovie>())
                                }
                            }
                            is RequestStateMultipleResults.Error -> {
                                if (topMoviesAdapter.differ.currentList.isEmpty()) {
                                    displayOnScreenError(state)
                                }
                            }
                            else -> {}
                        }
                    }
                }
                TagScreens.TopShows.tag -> {
                    topViewModel.topShowsState.collect { state ->
                        when (state) {
                            is RequestStateMultipleResults.Success<*> -> {
                                state.data.let { resultList ->
                                    topShowsAdapter.differ.submitList(resultList.filterIsInstance<TopMovie>())
                                }
                            }
                            is RequestStateMultipleResults.Error -> {
                                if (topShowsAdapter.differ.currentList.isEmpty()) {
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
            ErrorCode.Network.name -> topViewModel.setErrorMessage(
                getString(R.string.network_error)
            )
            ErrorCode.Internet.name -> topViewModel.setErrorMessage(
                getString(R.string.internet_error)
            )
            ErrorCode.Unknown.name -> topViewModel.setErrorMessage(
                getString(R.string.unknown_error)
            )
        }
    }

    private fun setupMovieAdapter(tag: String) {
        when (tag) {
            TagScreens.TopMovies.tag -> {
                topMoviesAdapter = TopMoviesAdapter()
                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(
                        activity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = topMoviesAdapter
                }
                topMoviesAdapter.onClick = { movie ->
                    navigateWithMovieId(movie.id)
                }
            }
            TagScreens.TopShows.tag -> {
                topShowsAdapter = TopMoviesAdapter()
                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(
                        activity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = topShowsAdapter
                }
                topShowsAdapter.onClick = { movie ->
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