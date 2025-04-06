package tushar.demo.chatroomapp.data

import com.google.firebase.firestore.Exclude

data class Message(
    val senderFirstName: String = "",
    val senderId:String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),

    @Transient
    val isSentByCurrentUser: Boolean = false

)
