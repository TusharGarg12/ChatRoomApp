package tushar.demo.chatroomapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tushar.demo.chatroomapp.RetrofitClient
import tushar.demo.chatroomapp.data.AIMessage
import tushar.demo.chatroomapp.data.MessageRequest
import tushar.demo.chatroomapp.data.MessageResponse

class AIChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<AIMessage>>(emptyList())
    val messages: StateFlow<List<AIMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun sendMessage(userText: String) {
        if (userText.isBlank()) return

        // Add user's message to list
        val userMessage = AIMessage(text = userText, isFromUser = true)
        _messages.value = _messages.value + userMessage

        _isLoading.value = true

        val request = MessageRequest(userText)
        RetrofitClient.instance.sendMessage(request).enqueue(object : Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val aiText = response.body()?.reply ?: "..."
                    val aiMessage = AIMessage(text = aiText, isFromUser = false)
                    _messages.value = _messages.value + aiMessage
                } else {
                    val errorMsg = AIMessage(text = "AI response error: ${response.code()}", isFromUser = false)
                    _messages.value = _messages.value + errorMsg
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                _isLoading.value = false
                val errorMsg = AIMessage(text = "Failed to contact AI: ${t.localizedMessage}", isFromUser = false)
                _messages.value = _messages.value + errorMsg
            }
        })
    }

    fun clearChat() {
        _messages.value = emptyList()
    }
}
