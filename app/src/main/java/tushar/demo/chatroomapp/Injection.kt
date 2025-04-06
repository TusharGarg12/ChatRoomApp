package tushar.demo.chatroomapp

import com.google.firebase.firestore.FirebaseFirestore

object Injection {
    private val instance: Lazy<FirebaseFirestore> = lazy {
        FirebaseFirestore.getInstance()
    }

    fun instance(): FirebaseFirestore {
        return instance.value
    }
}