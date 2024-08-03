package com.tanya.finhelp.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.finhelp.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AuthVM @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _isLogIn = MutableLiveData<Boolean>()
    val isLogIn: LiveData<Boolean> = _isLogIn

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun logIn(email: String, password: String) {
        viewModelScope.launch {
            _error.value = ""

            if (email.isBlank() || password.isBlank()) {
                _error.value = "Пустые поля"
                return@launch
            }

            runCatching {
                repository.logIn(email, password)
            }.onSuccess {
                _isLogIn.value = true
            }.onFailure { error ->
                _error.value = error.message
            }
        }
    }
}
