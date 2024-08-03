package com.tanya.finhelp.domain

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

private const val EMAIL_KEY = "email"
private const val USERNAME_KEY = "username"
private const val DATABASE_PATH = "Users"

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDb: FirebaseDatabase
) : AuthRepository {

    override suspend fun logIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun singUp(email: String, password: String, username: String) {
        val isUserExist = firebaseDb.getReference().child(DATABASE_PATH).child(username).get().await().exists()
        if (isUserExist) throw Exception("Такой пользователь существует")
        val isUserCreated = firebaseAuth.createUserWithEmailAndPassword(email, password)
        isUserCreated.await()
        if (!isUserCreated.isSuccessful) throw Exception("Что-то пошло не так")
        val userInfo = HashMap<String, String>()
        userInfo.put(EMAIL_KEY, email)
        userInfo.put(USERNAME_KEY, username)
        firebaseDb.getReference()
            .child(DATABASE_PATH)
            .child(username)
            .setValue(userInfo).await()
    }
}

interface AuthRepository {
    suspend fun logIn(email: String, password: String)
    suspend fun singUp(email: String, password: String, username: String)
}
