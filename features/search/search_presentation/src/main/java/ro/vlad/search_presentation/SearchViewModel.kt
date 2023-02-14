package ro.vlad.search_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ro.vlad.core.util.ConnectivityObserver
import ro.vlad.core.util.ErrorCode
import ro.vlad.core.util.RequestStateMultipleResults
import ro.vlad.search_domain.use_case.GetSearchResults
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchResultsUseCase: GetSearchResults,
    private val networkConnectivityObserver: ConnectivityObserver
) : ViewModel() {
    private val _searchResults =
        MutableStateFlow<RequestStateMultipleResults>(RequestStateMultipleResults.Empty)
    val searchResults: StateFlow<RequestStateMultipleResults> = _searchResults
    private var networkStatus: MutableStateFlow<ConnectivityObserver.Status> =
        MutableStateFlow(ConnectivityObserver.Status.Available)
    var currentLoadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
        private set
    var currentErrorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
        private set

    init {
        viewModelScope.launch {
            networkConnectivityObserver.observe().collect { networkStatus.value = it }
        }
    }

    fun getSearchResults(expression: String) = viewModelScope.launch {
        setLoading(true)
        setErrorMessage(null)
        networkStatus.collect {
            if (networkStatus.value == ConnectivityObserver.Status.Available) {
                getSearchResultsUseCase(expression)
                    .onSuccess { results ->
                        setLoading(false)
                        setErrorMessage(null)
                        results?.let { list ->
                            _searchResults.value = RequestStateMultipleResults.Success(list)
                        }
                    }
                    .onFailure { throwable ->
                        setLoading(false)
                        when (throwable) {
                            is IOException -> _searchResults.value =
                                RequestStateMultipleResults.Error(
                                    ErrorCode.Network.name
                                )
                            else -> _searchResults.value = RequestStateMultipleResults.Error(
                                ErrorCode.Unknown.name
                            )
                        }
                    }
            } else {
                setLoading(false)
                _searchResults.value = RequestStateMultipleResults.Error(ErrorCode.Internet.name)
            }
        }
    }

    private fun setLoading(newValue: Boolean) {
        currentLoadingState.value = newValue
    }

    fun setErrorMessage(message: String?) {
        currentErrorMessage.value = message
    }
}