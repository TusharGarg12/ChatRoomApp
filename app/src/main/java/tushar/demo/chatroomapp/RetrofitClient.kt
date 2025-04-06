package tushar.demo.chatroomapp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tushar.demo.chatroomapp.viewmodel.ChatApi
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://chatroom-ai-backend.onrender.com/"

    val instance: ChatApi by lazy {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ChatApi::class.java)
    }
}

