package ro.vlad.popular_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ro.vlad.core.util.ConnectivityObserver
import ro.vlad.core.util.ErrorCode
import ro.vlad.core.util.RequestStateMultipleResults
import ro.vlad.popular_domain.use_case.PopularUseCases
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val popularUseCases: PopularUseCases,
    private val networkConnectivityObserver: ConnectivityObserver
) : ViewModel() {
    private var _popularMoviesState =
        MutableStateFlow<RequestStateMultipleResults>(RequestStateMultipleResults.Empty)
    val popularMoviesState: StateFlow<RequestStateMultipleResults> = _popularMoviesState
    private var _popularShowsState =
        MutableStateFlow<RequestStateMultipleResults>(RequestStateMultipleResults.Empty)
    val popularShowsState: StateFlow<RequestStateMultipleResults> = _popularShowsState
    private var networkStatus: MutableStateFlow<ConnectivityObserver.Status> =
        MutableStateFlow(ConnectivityObserver.Status.Available)
    var currentScreenLoadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
        private set
    var currentScreenErrorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
        private set

    init {
        viewModelScope.launch {
            networkConnectivityObserver.observe().collect { networkStatus.value = it }
        }
    }

    fun getPopularMovies() = viewModelScope.launch {
        setLoading(true)
        networkStatus.collect {
            if (networkStatus.value == ConnectivityObserver.Status.Available) {
                popularUseCases
                    .getPopularMovies()
                    .onSuccess { moviesList ->
                        setLoading(false)
                        setErrorMessage(null)
                        moviesList?.let { list ->
                            _popularMoviesState.value = RequestStateMultipleResults.Success(list)
                        }
                    }
                    .onFailure { throwable ->
                        setLoading(false)
                        when (throwable) {
                            is IOException -> _popularMoviesState.value =
                                RequestStateMultipleResults.Error(ErrorCode.Network.name)
                            else -> _popularMoviesState.value =
                                RequestStateMultipleResults.Error(ErrorCode.Unknown.name)
                        }
                    }
            } else {
                setLoading(false)
                _popularMoviesState.value =
                    RequestStateMultipleResults.Error(ErrorCode.Internet.name)
            }
        }
    }

    fun getPopularShows() = viewModelScope.launch {
        setLoading(true)
        networkStatus.collect {
            if (networkStatus.value == ConnectivityObserver.Status.Available) {
                popularUseCases
                    .getPopularMovies()
                    .onSuccess { moviesList ->
                        setLoading(false)
                        setErrorMessage(null)
                        moviesList?.let { list ->
                            _popularShowsState.value = RequestStateMultipleResults.Success(list)
                        }
                    }
                    .onFailure { throwable ->
                        setLoading(false)
                        when (throwable) {
                            is IOException -> _popularShowsState.value =
                                RequestStateMultipleResults.Error(ErrorCode.Network.name)
                            else -> _popularShowsState.value =
                                RequestStateMultipleResults.Error(ErrorCode.Unknown.name)
                        }
                    }
            } else {
                setLoading(false)
                _popularShowsState.value =
                    RequestStateMultipleResults.Error(ErrorCode.Internet.name)
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