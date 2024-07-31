package com.tanya.finhelp.screens.coinInfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.tanya.finhelp.data.Api
import com.tanya.finhelp.domain.CoinInfo
import com.tanya.finhelp.domain.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CoinInfoVM @Inject constructor(
    private val api: Api
) : ViewModel() {

    private val _state = MutableLiveData<List<CoinInfo>>()
    val state: LiveData<List<CoinInfo>> = _state

    fun getHistoryCoin(id: String) {
        viewModelScope.launch {
            runCatching {
                api.getHistoryCoin(id)
            }.onSuccess {
                _state.value = it.toDomain()
            }.onFailure {
                Log.d("BOB", "getHistoryCoin: $it ")
            }
        }
    }
}
