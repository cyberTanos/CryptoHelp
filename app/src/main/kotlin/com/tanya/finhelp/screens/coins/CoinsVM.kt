package com.tanya.finhelp.screens.coins

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.finhelp.data.Api
import com.tanya.finhelp.domain.BaseRecyclerItem
import com.tanya.finhelp.domain.toDomain
import com.tanya.finhelp.util.getSkeletons
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@HiltViewModel
class CoinsVM @Inject constructor(
    private val api: Api
) : ViewModel() {

    private val _state = MutableLiveData<List<BaseRecyclerItem>>()
    val state: LiveData<List<BaseRecyclerItem>> = _state

    fun getCoins() {
        viewModelScope.launch {
            runCatching {
                _state.value = getSkeletons()
                api.getCoins()
            }.onSuccess { coins ->
                _state.value = coins.toDomain()
            }.onFailure { error ->
                Log.d("TAG", "____________$error")
            }
        }
    }
}
