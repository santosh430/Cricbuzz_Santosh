package com.example.cricbuzz_santosh.data.dbhelper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cricbuzz_santosh.presentation.ui.addplayer.Converters

@Database(entities = [Player::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class PlayerDataBase: RoomDatabase() {

    abstract fun userDao(): PlayerDao

    companion object{
        @Volatile
        private var INSTANCE: PlayerDataBase? =null

        fun getDataBase (context: Context): PlayerDataBase {
            val tempInstance = INSTANCE

            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlayerDataBase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }

}