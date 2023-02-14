package ro.vlad.search_presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ro.vlad.core.R
import ro.vlad.core.util.Constants.SEARCH_EXTRA_KEY
import ro.vlad.core.util.Constants.SEARCH_TIME_DELAY
import ro.vlad.core.util.ErrorCode
import ro.vlad.core.util.NavigationDestinations
import ro.vlad.core.util.Navigator
import ro.vlad.core.util.RequestStateMultipleResults
import ro.vlad.search_domain.model.ResultEntry
import ro.vlad.search_presentation.adapter.SearchAdapter
import ro.vlad.search_presentation.databinding.ActivitySearchBinding
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    @Inject
    lateinit var navigatorProvider: Navigator.Provider
    private lateinit var binding: ActivitySearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private val searchViewModel: SearchViewModel by viewModels()

    companion object {
        fun launchActivity(activity: Activity, searchKeywords: String?) {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra(SEARCH_EXTRA_KEY, searchKeywords)
            activity.startActivity(intent)
        }
    }

    object NavigateToSearchActivity : Navigator {
        override fun navigate(activity: Activity, movieId: String?, searchKeywords: String?) {
            launchActivity(activity, searchKeywords)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            viewModel = searchViewModel
            lifecycleOwner = this@SearchActivity
            backButton.setOnClickListener { finish() }
            searchEt.requestFocus()
        }
        setupSearchAdapter()
        setupSearchListener()
        setupRefreshListener()

        lifecycleScope.launchWhenStarted {
            searchViewModel.searchResults.collect { state ->
                when (state) {
                    is RequestStateMultipleResults.Success<*> -> {
                        state.data.let { results ->
                            searchAdapter.differ.submitList(results.filterIsInstance<ResultEntry>())
                        }
                    }
                    is RequestStateMultipleResults.Error -> {
                        if (searchAdapter.differ.currentList.isEmpty()) {
                            displayOnScreenError(state)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setupRefreshListener() {
        binding.swipeLayout.setOnRefreshListener {
            lifecycleScope.launch {
                searchViewModel.searchResults.collect { state ->
                    when (state) {
                        is RequestStateMultipleResults.Error -> {
                            when (state.message) {
                                (ErrorCode.Internet.name), (ErrorCode.Network.name)-> {
                                    if (binding.searchEt.text.toString().isNotEmpty()) {
                                        searchForResults(binding.searchEt.text.toString())
                                        binding.swipeLayout.isRefreshing = false
                                    }
                                }
                            }
                        }
                        else -> {
                            binding.swipeLayout.isRefreshing = false
                        }
                    }
                }
            }
        }
    }

    private fun setupSearchListener() {
        var job: Job? = null
        binding.searchEt.addTextChangedListener(
            onTextChanged = { editable, _, _, _ ->
                job?.cancel()
                job = MainScope().launch {
                    delay(SEARCH_TIME_DELAY)
                    editable?.let {
                        if (editable.toString().isNotEmpty()) {
                            searchForResults(editable.toString())
                        }
                    }
                }
            }
        )
        binding.searchEt.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (binding.searchEt.text.toString().isNotEmpty()) {
                    searchForResults(binding.searchEt.text.toString())
                    binding.searchEt.clearFocus()
                    hideKeyboard(binding.searchEt)
                    handled = true
                }
            }
            handled
        }
    }

    private fun displayOnScreenError(state: RequestStateMultipleResults.Error) {
        when (state.message) {
            ErrorCode.Network.name -> searchViewModel.setErrorMessage(
                getString(R.string.network_error)
            )
            ErrorCode.Internet.name -> searchViewModel.setErrorMessage(
                getString(R.string.internet_error)
            )
            ErrorCode.Unknown.name -> searchViewModel.setErrorMessage(
                getString(R.string.unknown_error)
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSearchAdapter() {
        searchAdapter = SearchAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                this@SearchActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = searchAdapter
            setOnTouchListener { v, _ ->
                hideKeyboard(v)
                false
            }
        }
        searchAdapter.onClick = { result ->
            navigatorProvider.getDestinations(NavigationDestinations.DetailsActivity)
                .navigate(this, result.id)
        }
    }

    private fun searchForResults(expression: String) {
        searchViewModel.getSearchResults(expression)
        searchAdapter.differ.submitList(emptyList())
    }

    private fun hideKeyboard(v: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }
}