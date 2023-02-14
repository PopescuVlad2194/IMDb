package ro.vlad.top_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ro.vlad.core.util.ConnectivityObserver
import ro.vlad.core.util.ErrorCode
import ro.vlad.core.util.RequestStateMultipleResults
import ro.vlad.top_domain.use_case.TopUseCases
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val topUseCases: TopUseCases,
    private val networkConnectivityObserver: ConnectivityObserver
) : ViewModel() {
    private var _topMoviesState =
        MutableStateFlow<RequestStateMultipleResults>(RequestStateMultipleResults.Empty)
    val topMoviesState: StateFlow<RequestStateMultipleResults> = _topMoviesState
    private var _topShowsState =
        MutableStateFlow<RequestStateMultipleResults>(RequestStateMultipleResults.Empty)
    val topShowsState: StateFlow<RequestStateMultipleResults> = _topShowsState
    private var networkStatus: MutableStateFlow<ConnectivityObserver.Status> =
        MutableStateFlow(ConnectivityObserver.Status.Available)
    var currentScreenLoadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
        private set
    var currentScreenErrorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
        private set

    init {
        viewModelScope.launch {
            networkConnectivityObserver.observe().collect() { networkStatus.value = it }
        }
    }

    fun getTopMovies() = viewModelScope.launch {
        setLoading(true)
        networkStatus.collect {
            if (networkStatus.value == ConnectivityObserver.Status.Available) {
                topUseCases
                    .getTopMovies()
                    .onSuccess { movieList ->
                        setLoading(false)
                        setErrorMessage(null)
                        movieList?.let {
                            _topMoviesState.value = RequestStateMultipleResults.Success(it)
                        }
                    }
                    .onFailure {
                        setLoading(false)
                        when (it) {
                            is IOException -> _topMoviesState.value =
                                RequestStateMultipleResults.Error(ErrorCode.Network.name)
                            else -> _topMoviesState.value =
                                RequestStateMultipleResults.Error(ErrorCode.Unknown.name)
                        }
                    }
            } else {
                setLoading(false)
                _topMoviesState.value = RequestStateMultipleResults.Error(ErrorCode.Internet.name)
            }
        }
    }

    fun getTopShows() = viewModelScope.launch {
        setLoading(true)
        networkStatus.collect {
            if (networkStatus.value == ConnectivityObserver.Status.Available) {
                topUseCases
                    .getTopShows()
                    .onSuccess { movieList ->
                        setLoading(false)
                        setErrorMessage(null)
                        movieList?.let {
                            _topShowsState.value = RequestStateMultipleResults.Success(it)
                        }
                    }
                    .onFailure {
                        setLoading(false)
                        when (it) {
                            is IOException -> _topShowsState.value =
                                RequestStateMultipleResults.Error(ErrorCode.Network.name)
                            else -> _topShowsState.value =
                                RequestStateMultipleResults.Error(ErrorCode.Unknown.name)
                        }
                    }
            } else {
                setLoading(false)
                _topShowsState.value = RequestStateMultipleResults.Error(ErrorCode.Internet.name)
            }
        }
    }

    private fun setLoading(newValue: Boolean) {
        currentScreenLoadingState.value = newValue
    }

    fun setErrorMessage(message: String?) {
        currentScreenErrorMessage.value = message
    }
}