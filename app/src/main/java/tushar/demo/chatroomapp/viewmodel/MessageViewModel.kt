package tushar.demo.chatroomapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import tushar.demo.chatroomapp.Injection
import tushar.demo.chatroomapp.data.Message
import tushar.demo.chatroomapp.data.MessageRepository
import tushar.demo.chatroomapp.data.Result.*
import tushar.demo.chatroomapp.data.User
import tushar.demo.chatroomapp.data.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tushar.demo.chatroomapp.RetrofitClient
import tushar.demo.chatroomapp.data.MessageRequest
import tushar.demo.chatroomapp.data.MessageResponse

class MessageViewModel : ViewModel() {

    private val messageRepository: MessageRepository
    private val userRepository: UserRepository

    init {
        messageRepository = MessageRepository(Injection.instance())
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
        loadCurrentUser()
    }

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    private val _roomId = MutableLiveData<String>()
    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    fun setRoomId(roomId: String) {
        _roomId.value = roomId
        loadMessages()
    }

    fun sendMessage(text: String) {
        val currentUser = _currentUser.value ?: return
        val roomId = _roomId.value ?: return

        val message = Message(
            senderId = currentUser.email ?: "unknown",
            senderFirstName = currentUser.firstName ?: "You",
            text = text,
            timestamp = System.currentTimeMillis(),
            isSentByCurrentUser = true
        )

        viewModelScope.launch {
            messageRepository.sendMessage(roomId, message)
        }
    }

     fun loadMessages() {
        viewModelScope.launch {
            _roomId.value?.let { roomId ->
                messageRepository.getChatMessages(roomId)
                    .collect { messageList ->
                        _messages.value = messageList
                    }
            }
        }
    }

     fun loadCurrentUser() {
        viewModelScope.launch {
            when (val result = userRepository.getCurrentUser()) {
                is Success -> _currentUser.value = result.data
                is Error -> {
                    // Optional: handle error case
                    Log.e("User", "Failed to load user: ${result.exception.message}")
                }
            }
        }
    }
}
