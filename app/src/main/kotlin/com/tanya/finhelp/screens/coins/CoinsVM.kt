package com.tanya.finhelp.screens.coins

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.finhelp.data.Api
import com.tanya.finhelp.domain.Coin
import com.tanya.finhelp.domain.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CoinsVM @Inject constructor(
    private val api: Api
) : ViewModel() {

    private val _state = MutableLiveData<List<Coin>>()
    val state: LiveData<List<Coin>> = _state

    fun getCoins() {
        viewModelScope.launch {
            runCatching {
                api.getCoins()
            }.onSuccess { coins ->
                _state.value = coins.toDomain()
            }.onFailure { error ->
                Log.d("TAG", "________________________________$error")
            }
        }
    }
}
