package com.tanya.finhelp.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthVM @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _isLogIn = MutableLiveData<Boolean>()
    val isLogIn: LiveData<Boolean> = _isLogIn

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun logIn(email: String, password: String) {
        _error.value = ""

        if (email.isBlank() || password.isBlank()) {
            _error.value = "Пустые поля"
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            _isLogIn.value = true
        }.addOnFailureListener { error ->
            _error.value = error.message
        }
    }
}
