package com.tanya.finhelp.domain

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.tanya.finhelp.data.local.SharedPreference
import com.tanya.finhelp.domain.model.User
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

private const val EMAIL_KEY = "email"
private const val USERNAME_KEY = "username"
private const val DATABASE_PATH = "Users"

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDb: FirebaseDatabase,
    private val sharedPref: SharedPreference
) : AuthRepository {

    override suspend fun logIn(email: String, password: String) {
        val resultLogIn = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        val resultFromDb = firebaseDb.getReference()
            .child(DATABASE_PATH)
            .child(resultLogIn.user?.uid.orEmpty()).get().await()
        val user = Gson().fromJson(resultFromDb.value.toString(), User::class.java)
        sharedPref.saveUsername(user.username)
    }

    override suspend fun singUp(email: String, password: String, username: String) {
        val isUserCreated = firebaseAuth.createUserWithEmailAndPassword(email, password)
        val resultUser = isUserCreated.await()
        val userInfo = HashMap<String, String>()
        userInfo.put(EMAIL_KEY, email)
        userInfo.put(USERNAME_KEY, username)
        firebaseDb.getReference()
            .child(DATABASE_PATH)
            .child(resultUser.user?.uid.orEmpty())
            .setValue(userInfo).await()
    }
}

interface AuthRepository {
    suspend fun logIn(email: String, password: String)
    suspend fun singUp(email: String, password: String, username: String)
}
