package tushar.demo.chatroomapp.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MessageRepository(private val firestore: FirebaseFirestore) {

    suspend fun sendMessage(roomId: String, message: Message): Result<Unit> = try {
        firestore.collection("chatrooms")
            .document(roomId)
            .collection("messages")
            .add(message)
            .await()
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Error(e)
    }

    fun getChatMessages(roomId: String): Flow<List<Message>> = callbackFlow {
        val subscription = firestore.collection("chatrooms")
            .document(roomId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                querySnapshot?.let {
                    val messages = it.documents.mapNotNull { doc ->
                        try {
                            doc.toObject(Message::class.java)
                        } catch (e: Exception) {
                            Log.e("Parse", "Error parsing message: ${e.message}")
                            null
                        }
                    }
                    trySend(messages)
                }
            }

        awaitClose { subscription.remove() }
    }
}

