package com.hkjj.heartbreakprice.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hkjj.heartbreakprice.core.Result
import com.hkjj.heartbreakprice.domain.model.User
import com.hkjj.heartbreakprice.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl : AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance("heart-break-price")

    override val isSignIn: Flow<Boolean> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        auth.addAuthStateListener(listener)
        // Send initial state immediately
        trySend(auth.currentUser != null) 
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun login(email: String, password: String): Result<Unit, Exception> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun signUp(email: String, password: String, name: String): Result<Unit, Exception> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid ?: throw Exception("User creation failed")

            val user = hashMapOf(
                "name" to name,
                "email" to email,
                "fcmToken" to "" 
            )

            firestore.collection("Users").document(uid).set(user).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }


    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun getUser(): User {
        val uid = auth.currentUser?.uid ?: throw Exception("Not logged in")
        val snapshot = firestore.collection("Users").document(uid).get().await()
        return User(
            name = snapshot.getString("name") ?: "",
            email = snapshot.getString("email") ?: "",
            fcmToken = snapshot.getString("fcmToken") ?: ""
        )
    }

    override suspend fun updateFcmToken(token: String) {
        val uid = auth.currentUser?.uid ?: return
        firestore.collection("Users").document(uid).update("fcmToken", token).await()
    }
}


