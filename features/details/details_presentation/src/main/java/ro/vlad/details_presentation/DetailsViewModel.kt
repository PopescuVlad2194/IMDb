package ro.vlad.details_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ro.vlad.core.util.ConnectivityObserver
import ro.vlad.core.util.ErrorCode
import ro.vlad.core.util.RequestStateSingleResult
import ro.vlad.details_domain.use_case.GetMovieDetails
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieDetails,
    private val networkConnectivityObserver: ConnectivityObserver
) : ViewModel() {
    private val _movieDetails = MutableStateFlow<RequestStateSingleResult>(RequestStateSingleResult.Empty)
    val movieDetails: StateFlow<RequestStateSingleResult> = _movieDetails
    private var networkStatus: MutableStateFlow<ConnectivityObserver.Status> =
        MutableStateFlow(ConnectivityObserver.Status.Available)
    var currentLoadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
        private set
    var currentErrorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
        private set

    init {
        viewModelScope.launch {
            networkConnectivityObserver.observe().collect { networkStatus.value = it}
        }
    }

    fun getMovieDetails(movieId: String) = viewModelScope.launch {
        setLoading(true)
        networkStatus.collect {
            if (networkStatus.value == ConnectivityObserver.Status.Available) {
                getMovieUseCase(movieId)
                    .onSuccess { movieInfo ->
                        setLoading(false)
                        setErrorMessage(null)
                        _movieDetails.value = RequestStateSingleResult.Success(movieInfo)
                    }
                    .onFailure { throwable ->
                        setLoading(false)
                        when (throwable) {
                            is IOException -> _movieDetails.value = RequestStateSingleResult.Error(
                                ErrorCode.Network.name
                            )
                            else -> _movieDetails.value = RequestStateSingleResult.Error(
                                ErrorCode.Unknown.name
                            )
                        }
                    }
            } else {
                setLoading(false)
                _movieDetails.value = RequestStateSingleResult.Error(ErrorCode.Internet.name)
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