package tushar.demo.chatroomapp.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(private val auth: FirebaseAuth,
                     private val firestore: FirebaseFirestore
) {

    suspend fun signUp(email: String, password: String, firstName: String, lastName: String): Result<Boolean> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            if (user != null) {
                val userData = hashMapOf(
                    "email" to email,
                    "firstName" to firstName,
                    "lastName" to lastName
                )

                firestore.collection("users")
                    .document(user.uid)
                    .set(userData)
                    .await()
            }

            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun login(email: String, password: String): Result<Boolean> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user

            // Optional: Auto-create if missing (only if you want to ensure presence)
            if (user != null) {
                val doc = firestore.collection("users").document(user.uid).get().await()
                if (!doc.exists()) {
                    val fallbackUser = hashMapOf(
                        "email" to email,
                        "firstName" to user.displayName,
                        "lastName" to user.uid
                    )
                    firestore.collection("users").document(user.uid).set(fallbackUser).await()
                }
            }

            Result.Success(true)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private suspend fun saveUserToFirestore(user: User) {
        firestore.collection("users").document(user.email).set(user).await()
    }

    suspend fun getCurrentUser(): Result<User> {
        val uid = auth.currentUser?.uid ?: return Result.Error(Exception("Not authenticated"))
        val doc = firestore.collection("users").document(uid).get().await()
        return if (doc.exists()) {
            Result.Success(doc.toObject(User::class.java)!!)
        } else {
            Result.Error(Exception("User data not found"))
        }
    }

}
