package com.tanya.finhelp.screens.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val EMAIL_KEY = "email"
private const val USERNAME_KEY = "username"
private const val DATABASE_PATH = "Users"

@HiltViewModel
class RegistrationVM @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDb: FirebaseDatabase
) : ViewModel() {

    private val _isSignUp = MutableLiveData<Boolean>()
    val isSignUp: LiveData<Boolean> = _isSignUp

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun signUp(email: String, password: String, username: String) {
        _error.value = ""

        if (email.isBlank() || username.isBlank() || password.isBlank()) {
            _error.value = "Пустые поля"
            return
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            val userInfo = HashMap<String, String>()
            userInfo.put(EMAIL_KEY, email)
            userInfo.put(USERNAME_KEY, username)
            firebaseDb.getReference()
                .child(DATABASE_PATH)
                .child(firebaseAuth.currentUser?.uid ?: "")
                .setValue(userInfo).addOnSuccessListener {
                    _isSignUp.value = true
                }.addOnFailureListener { error ->
                    _error.value = error.message
                }
        }.addOnFailureListener { error ->
            _error.value = error.message
        }
    }
}
