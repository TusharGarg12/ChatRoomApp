package tushar.demo.chatroomapp.viewmodel

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import tushar.demo.chatroomapp.data.MessageRequest
import tushar.demo.chatroomapp.data.MessageResponse

interface ChatApi {
    @POST("chat")
    fun sendMessage(@Body message: MessageRequest): Call<MessageResponse>
}