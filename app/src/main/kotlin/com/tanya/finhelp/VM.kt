package com.tanya.finhelp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.finhelp.data.Api
import com.tanya.finhelp.data.response.CoinResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class VM @Inject constructor(
    private val api: Api
) : ViewModel() {

    private val _state = MutableLiveData<List<CoinResponse>>()
    val state: LiveData<List<CoinResponse>> = _state

    fun getCoins() {
        viewModelScope.launch {
            runCatching {
                api.getCoins()
            }.onSuccess { coins ->
                _state.value = coins
            }.onFailure {
                Log.d("TAG", "getKoin: ____________________-ERROR")
            }
        }
    }
}
