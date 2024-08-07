package com.tanya.finhelp.screens.coinInfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.finhelp.domain.model.CoinInfo
import com.tanya.finhelp.domain.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CoinInfoVM @Inject constructor(
    private val repository: CoinRepository
) : ViewModel() {

    private val _state = MutableLiveData<List<CoinInfo>>()
    val state: LiveData<List<CoinInfo>> = _state

    fun getHistoryCoin(id: String) {
        viewModelScope.launch {
            runCatching {
                repository.getHistoryCoin(id)
            }.onSuccess {
                _state.value = it
            }.onFailure {
                Log.d("getHistoryCoin", "getHistoryCoin: $it ")
            }
        }
    }
}
