package com.example.cricbuzz_santosh

import android.app.Application
import com.example.cricbuzz_santosh.data.dbhelper.PlayerDataBase
import com.example.cricbuzz_santosh.data.repo.PlayerRepository

class PlayerApplication:Application()
{
    lateinit var userRepository: PlayerRepository
    lateinit var database: PlayerDataBase
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize(){
        database = PlayerDataBase.getDataBase(applicationContext)
        userRepository = PlayerRepository(database)

//        userRepository = UserRepository(userService, database, applicationContext)
    }
}