package ro.vlad.theaters_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ro.vlad.core.util.ConnectivityObserver
import ro.vlad.core.util.ErrorCode
import ro.vlad.core.util.RequestStateMultipleResults
import ro.vlad.theaters_domain.use_case.TheatersUseCases
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class TheatersViewModel @Inject constructor(
    private val theatersUseCases: TheatersUseCases,
    private val networkConnectivityObserver: ConnectivityObserver
) : ViewModel() {
    private var _nowShowingState =
        MutableStateFlow<RequestStateMultipleResults>(RequestStateMultipleResults.Empty)
    val nowShowingState: StateFlow<RequestStateMultipleResults> = _nowShowingState
    private var _boxOfficeState =
        MutableStateFlow<RequestStateMultipleResults>(RequestStateMultipleResults.Empty)
    val boxOfficeState: StateFlow<RequestStateMultipleResults> = _boxOfficeState
    private var _comingSoonState =
        MutableStateFlow<RequestStateMultipleResults>(RequestStateMultipleResults.Empty)
    val comingSoonState: StateFlow<RequestStateMultipleResults> = _comingSoonState
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

    fun getNowShowingMovies() = viewModelScope.launch {
        setLoading(true)
        networkStatus.collect {
            if (networkStatus.value == ConnectivityObserver.Status.Available) {
                theatersUseCases
                    .getNowShowingMovies()
                    .onSuccess { moviesList ->
                        setLoading(false)
                        setErrorMessage(null)
                        moviesList?.let { list ->
                            _nowShowingState.value = RequestStateMultipleResults.Success(list)
                        }
                    }
                    .onFailure { throwable ->
                        setLoading(false)
                        when (throwable) {
                            is IOException -> _nowShowingState.value =
                                RequestStateMultipleResults.Error(ErrorCode.Network.name)
                            else -> _nowShowingState.value =
                                RequestStateMultipleResults.Error(ErrorCode.Unknown.name)
                        }
                    }
            } else {
                setLoading(false)
                _nowShowingState.value = RequestStateMultipleResults.Error(ErrorCode.Internet.name)
            }
        }
    }

    fun getBoxOfficeMovies() = viewModelScope.launch {
        setLoading(true)
        networkStatus.collect {
            if (networkStatus.value == ConnectivityObserver.Status.Available) {
                theatersUseCases
                    .getBoxOfficeMovies()
                    .onSuccess { moviesList ->
                        setLoading(false)
                        setErrorMessage(null)
                        moviesList?.let { list ->
                            _boxOfficeState.value = RequestStateMultipleResults.Success(list)
                        }
                    }
                    .onFailure { throwable ->
                        setLoading(false)
                        when (throwable) {
                            is IOException -> _boxOfficeState.value =
                                RequestStateMultipleResults.Error(ErrorCode.Network.name)
                            else -> _boxOfficeState.value =
                                RequestStateMultipleResults.Error(ErrorCode.Unknown.name)
                        }
                    }
            } else {
                setLoading(false)
                _boxOfficeState.value = RequestStateMultipleResults.Error(ErrorCode.Internet.name)
            }
        }
    }

    fun getComingSoonMovies() = viewModelScope.launch {
        setLoading(true)
        networkStatus.collect {
            if (networkStatus.value == ConnectivityObserver.Status.Available) {
                theatersUseCases
                    .getComingSoonMovies()
                    .onSuccess { moviesList ->
                        setLoading(false)
                        setErrorMessage(null)
                        moviesList?.let { list ->
                            _comingSoonState.value = RequestStateMultipleResults.Success(list)
                        }
                    }
                    .onFailure { throwable ->
                        setLoading(false)
                        when (throwable) {
                            is IOException -> _comingSoonState.value =
                                RequestStateMultipleResults.Error(ErrorCode.Network.name)
                            else -> _comingSoonState.value =
                                RequestStateMultipleResults.Error(ErrorCode.Unknown.name)
                        }
                    }
            } else {
                setLoading(false)
                _comingSoonState.value = RequestStateMultipleResults.Error(ErrorCode.Internet.name)
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