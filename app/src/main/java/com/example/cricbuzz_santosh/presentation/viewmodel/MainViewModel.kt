package com.example.cricbuzz_santosh.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cricbuzz_santosh.data.dbhelper.Player
import com.example.cricbuzz_santosh.data.dbhelper.PlayerDataBase
import com.example.cricbuzz_santosh.data.repo.PlayerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private var repository: PlayerRepository,
    application: Application,
    private var database: PlayerDataBase
)
    : AndroidViewModel(
    application
) {


    init {

        viewModelScope.launch(Dispatchers.IO) {
            repository.getPlayer()
        }
    }

    val list = ArrayList<Player>()

    val player : LiveData<List<Player>>
        get() = repository.players

    fun addPlayer(player: Player){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPlayer(player)
        }
    }

    fun updateFavPlayer(name:String,choice:Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateFavPlayer(name,choice)
        }
    }
}