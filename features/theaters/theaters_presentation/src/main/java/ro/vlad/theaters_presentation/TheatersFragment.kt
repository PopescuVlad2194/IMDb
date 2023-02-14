package ro.vlad.theaters_presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ro.vlad.core.R
import ro.vlad.core.util.Constants.FRAGMENT_TAG
import ro.vlad.core.util.ErrorCode
import ro.vlad.core.util.NavigationDestinations
import ro.vlad.core.util.Navigator
import ro.vlad.core.util.RequestStateMultipleResults
import ro.vlad.core_ui.util.TagScreens
import ro.vlad.theaters_domain.model.BoxOfficeMovie
import ro.vlad.theaters_domain.model.ComingSoonMovie
import ro.vlad.theaters_domain.model.now_showing.NowShowingMovie
import ro.vlad.theaters_presentation.adapters.BoxOfficeAdapter
import ro.vlad.theaters_presentation.adapters.ComingSoonAdapter
import ro.vlad.theaters_presentation.adapters.NowShowingAdapter
import ro.vlad.theaters_presentation.databinding.FragmentTheatersBinding
import javax.inject.Inject

@AndroidEntryPoint
class TheatersFragment : Fragment() {
    @Inject
    lateinit var navigatorProvider: Navigator.Provider
    private var _binding: FragmentTheatersBinding? = null
    private val binding get() = _binding!!
    private val theatersViewModel: TheatersViewModel by viewModels()
    private lateinit var fragmentTag: String
    private lateinit var nowShowingMovieAdapter: NowShowingAdapter
    private lateinit var boxOfficeMovieAdapter: BoxOfficeAdapter
    private lateinit var comingSoonMovieAdapter: ComingSoonAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTheatersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            getMoviesByTag()
        }
    }

    private fun getMoviesByTag() {
        when (fragmentTag) {
            TagScreens.NowShowing.tag -> theatersViewModel.getNowShowingMovies()
            TagScreens.BoxOffice.tag -> theatersViewModel.getBoxOfficeMovies()
            TagScreens.ComingSoon.tag -> theatersViewModel.getComingSoonMovies()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel = theatersViewModel
            lifecycleOwner = this@TheatersFragment.viewLifecycleOwner
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
                    TagScreens.NowShowing.tag -> {
                        theatersViewModel.nowShowingState.collect { state ->
                            handleSwipingState(state)
                        }
                    }
                    TagScreens.BoxOffice.tag -> {
                        theatersViewModel.boxOfficeState.collect { state ->
                            handleSwipingState(state)
                        }
                    }
                    TagScreens.ComingSoon.tag -> {
                        theatersViewModel.comingSoonState.collect { state ->
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
        lifecycleScope.launch {
            when (tag) {
                TagScreens.NowShowing.tag -> {
                    theatersViewModel.nowShowingState.collect { state ->
                        when (state) {
                            is RequestStateMultipleResults.Success<*> -> {
                                state.data.let { resultList ->
                                    nowShowingMovieAdapter.differ.submitList(resultList.filterIsInstance<NowShowingMovie>())
                                }
                            }
                            is RequestStateMultipleResults.Error -> {
                                if (nowShowingMovieAdapter.differ.currentList.isEmpty()) {
                                    displayOnScreenError(state)
                                }
                            }
                            else -> {}
                        }
                    }
                }
                TagScreens.BoxOffice.tag -> {
                    theatersViewModel.boxOfficeState.collect { state ->
                        when (state) {
                            is RequestStateMultipleResults.Success<*> -> {
                                state.data.let { resultList ->
                                    boxOfficeMovieAdapter.differ.submitList(resultList.filterIsInstance<BoxOfficeMovie>())
                                }
                            }
                            is RequestStateMultipleResults.Error -> {
                                if (boxOfficeMovieAdapter.differ.currentList.isEmpty()) {
                                    displayOnScreenError(state)
                                }
                            }
                            else -> {}
                        }
                    }
                }
                TagScreens.ComingSoon.tag -> {
                    theatersViewModel.comingSoonState.collect { state ->
                        when (state) {
                            is RequestStateMultipleResults.Success<*> -> {
                                state.data.let { resultList ->
                                    comingSoonMovieAdapter.differ.submitList(resultList.filterIsInstance<ComingSoonMovie>())
                                }
                            }
                            is RequestStateMultipleResults.Error -> {
                                if (comingSoonMovieAdapter.differ.currentList.isEmpty()) {
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
            ErrorCode.Network.name -> theatersViewModel.setErrorMessage(
                getString(R.string.network_error)
            )
            ErrorCode.Internet.name -> theatersViewModel.setErrorMessage(
                getString(R.string.internet_error)
            )
            ErrorCode.Unknown.name -> theatersViewModel.setErrorMessage(
                getString(R.string.unknown_error)
            )
        }
    }

    private fun setupMovieAdapter(tag: String) {
        when (tag) {
            TagScreens.NowShowing.tag -> {
                nowShowingMovieAdapter = NowShowingAdapter()
                binding.viewPager.apply {
                    orientation = ViewPager2.ORIENTATION_VERTICAL
                    adapter = nowShowingMovieAdapter
                }
                nowShowingMovieAdapter.onClick = { movie ->
                    navigateWithMovieId(movie.id)
                }
            }
            TagScreens.BoxOffice.tag -> {
                boxOfficeMovieAdapter = BoxOfficeAdapter()
                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(
                        activity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = boxOfficeMovieAdapter
                }
                boxOfficeMovieAdapter.onClick = { movie ->
                    navigateWithMovieId(movie.id)
                }
            }
            TagScreens.ComingSoon.tag -> {
                comingSoonMovieAdapter = ComingSoonAdapter()
                binding.viewPager.apply {
                    orientation = ViewPager2.ORIENTATION_VERTICAL
                    adapter = comingSoonMovieAdapter
                }
                comingSoonMovieAdapter.onClick = { movie ->
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