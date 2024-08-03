package com.tanya.finhelp.screens.coins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.finhelp.domain.BaseRecyclerItem
import com.tanya.finhelp.domain.CoinRepository
import com.tanya.finhelp.util.getSkeletons
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CoinsVM @Inject constructor(
    private val repository: CoinRepository
) : ViewModel() {

    private val _state = MutableLiveData<List<BaseRecyclerItem>>()
    val state: LiveData<List<BaseRecyclerItem>> = _state

    private val _errorState = MutableLiveData<Boolean>()
    val errorState: LiveData<Boolean> = _errorState

    fun getCoins() {
        viewModelScope.launch {
            runCatching {
                _errorState.value = false
                _state.value = getSkeletons()
                repository.getCoins()
            }.onSuccess { coins ->
                _state.value = coins
            }.onFailure {
                _errorState.value = true
            }
        }
    }
}
