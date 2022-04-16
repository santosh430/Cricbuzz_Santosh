package com.example.cricbuzz_santosh.data.dbhelper

import androidx.room.*

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlayer(player:Player)

    @Delete
    suspend fun deleteUser(player: Player)

    @Query("select * from player_table order by name asc")
    fun getPlayers():List<Player?>

    @Query("update player_table set fav= :choice where name= :name")
    fun updateFavPlayer(name: String, choice:Int)
}