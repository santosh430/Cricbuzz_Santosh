package com.example.cricbuzz_santosh.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cricbuzz_santosh.data.dbhelper.Player
import com.example.cricbuzz_santosh.data.dbhelper.PlayerDataBase

class PlayerRepository(private val playerDataBase: PlayerDataBase) {

    private val readAllData = MutableLiveData<List<Player>>()
    val  favPlayerList= MutableLiveData<ArrayList<Player>>()

    val players : LiveData<List<Player>>
        get() = readAllData

    val favPlayer: LiveData<ArrayList<Player>>
        get()= favPlayerList

    val list = ArrayList<Player>()


    suspend fun addPlayer(player: Player){
        playerDataBase.userDao().addPlayer(player)
    }

    fun getPlayer() {
        val players = playerDataBase.userDao().getPlayers()
        Log.d("tagtagtag", players.toString())
        readAllData.postValue(players as List<Player>?)
    }

//    suspend fun deletePlayer(player: Player){
//        playerDataBase.userDao().deleteUser(player)
//    }

    fun updateFavPlayer(name: String, choice:Int){
        playerDataBase.userDao().updateFavPlayer(name,choice)
    }

}