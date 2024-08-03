package com.tanya.finhelp.screens.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tanya.finhelp.domain.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RegistrationVM @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _isSignUp = MutableLiveData<Boolean>()
    val isSignUp: LiveData<Boolean> = _isSignUp

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun signUp(email: String, password: String, username: String) {
        viewModelScope.launch {
            _error.value = ""

            if (email.isBlank() || username.isBlank() || password.isBlank()) {
                _error.value = "Пустые поля"
                return@launch
            }

            runCatching {
                repository.singUp(email, password, username)
            }.onSuccess {
                _isSignUp.value = true
            }.onFailure { error ->
                _error.value = error.message
            }
        }
    }
}
