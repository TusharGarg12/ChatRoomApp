package tushar.demo.chatroomapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import tushar.demo.chatroomapp.Injection
import tushar.demo.chatroomapp.data.Result.*
import tushar.demo.chatroomapp.data.Room
import tushar.demo.chatroomapp.data.RoomRepository
import kotlinx.coroutines.launch

class RoomViewModel : ViewModel() {

    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> get() = _rooms
    private val roomRepository: RoomRepository
    init {
        roomRepository = RoomRepository(Injection.instance())
        loadRooms()
    }

    fun createRoom(name: String) {
        viewModelScope.launch {
            when (val result = roomRepository.createRoom(name)) {
                is Success -> {
                    loadRooms()
                }
                is Error -> {
                }
            }
        }
    }




    fun loadRooms() {
        viewModelScope.launch {
            when (val result = roomRepository.getRooms()) {
                is Success -> _rooms.value = result.data
                is Error -> {

                }
            }
        }
    }

}
